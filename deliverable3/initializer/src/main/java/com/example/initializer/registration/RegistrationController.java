package com.example.initializer.registration;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {
    
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PaymentInfoRepository paymentInfoRepository;

    @RequestMapping(value = "/userRegistration", method = RequestMethod.GET)
	public String RegistrationForm(Model model) {
		model.addAttribute("newUser", new CreateUser());
        model.addAttribute("statesList", retrieveStateList());
        model.addAttribute("cardTypesList", retrieveCardTypes());
		return "userRegistration";
	}
    
    @RequestMapping(value = "/userRegistration", method = RequestMethod.POST)
    public String createUser (@ModelAttribute("newUser") CreateUser toCreate, Model model) {
        boolean canRegister = true;
        boolean addressInput = false;
        boolean paymentInput = false;

        model.addAttribute("statesList", retrieveStateList());
        model.addAttribute("cardTypesList", retrieveCardTypes());
        if (toCreate.getFirstName().isBlank()) {
            model.addAttribute("emptyFirstName", "First name cannot be empty!");
            canRegister = false;
        }

        if (toCreate.getLastName().isBlank()) {
            model.addAttribute("emptyLastName", "Last name cannot be empty!");
            canRegister = false;
        }

        if (toCreate.getEmail().isBlank()) {
            model.addAttribute("emptyEmail", "Email cannot be empty!");
            canRegister = false;
        }

        if (toCreate.getPassword().isBlank()) {
            model.addAttribute("emptyPassword", "Password cannot be empty!");
            canRegister = false;
        } else if (toCreate.getPassword() != null && toCreate.getPassword().length() < 8) {
            model.addAttribute("passwordLength", "Password length cannot be less than 8!");
            canRegister = false;
        }

        if (toCreate.getConfirmPassword().isBlank()) {
            model.addAttribute("emptyConfirmPassword", "Confirm password cannot be empty!");
            canRegister = false;
        } else if (!toCreate.getPassword().equals(toCreate.getConfirmPassword())) {
            model.addAttribute("notMatchingPasswords", "Passwords do not match!");
            canRegister = false;
        }

        if ((customerRepository.findByEmail(toCreate.getEmail()).isPresent())) {
            model.addAttribute("existingEmail", "Account with this email already exists!");
            canRegister = false;
        }

        // they are inputting address and payment info. if one field is  not blank, the others become required as well
        if (!(toCreate.getStreet().isBlank()) || !(toCreate.getCity().isBlank()) || !(toCreate.getState().isBlank()) || !(toCreate.getZipcode().isBlank())) {

            if (toCreate.getStreet().isBlank()) {
				model.addAttribute("emptyStreet", "Street address cannot be empty!");
				canRegister = false;
			}

            if (toCreate.getZipcode().isBlank()) {
				model.addAttribute("emptyZipcode", "ZIP Code cannot be empty!");
				canRegister = false;
			}

            if (toCreate.getCity().isBlank()) {
				model.addAttribute("emptyCity", "City cannot be empty!");
				canRegister = false;
			}

            if (toCreate.getState().isBlank()) {
				model.addAttribute("emptyState", "State cannot be empty!");
				canRegister = false;
			}

            addressInput = true;
        }

        if (!(toCreate.getCardType().isBlank()) || !(toCreate.getExpiration().isBlank()) || !(toCreate.getCvv().isBlank()) || !(toCreate.getCardNumber().isBlank())) {

            if (toCreate.getCardType().isBlank()) {
				model.addAttribute("emptyCardType", "Card type cannot be empty!");
				canRegister = false;
			}

            if (toCreate.getExpiration().isBlank()) {
				model.addAttribute("emptyExpiration", "Card expiration cannot be empty!");
				canRegister = false;
			}

            if (toCreate.getCvv().isBlank()) {
				model.addAttribute("emptyCvv", "CVV cannot be empty!");
				canRegister = false;
			}

            if (toCreate.getCardNumber().isBlank()) {
				model.addAttribute("emptyCardNumber", "Card type cannot be empty!");
				canRegister = false;
			} else if (toCreate.getCardNumber().length() != 16) {
                model.addAttribute("shortCardNumber", "Credit card numbers must be 16 digits long!");
                canRegister = false;
            }
            paymentInput = true;
        }

        if (canRegister) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            Customer customer = new Customer();
            customer.setFirstName(toCreate.getFirstName());
            customer.setLastName(toCreate.getLastName());
            customer.setEmail(toCreate.getEmail().toLowerCase());
            customer.setHashedPassword(encoder.encode(toCreate.getPassword()));
            customer.setIsAdmin(0);
            customer.setUserStatus("inactive");
            if (toCreate.getSubscribed() != null) {
                if (toCreate.getSubscribed().equals("Y")) {
                    customer.setSubscribed(1);
                } else {
                    customer.setSubscribed(0);
                }
            }

            // generates a random number 8 times as a "verification code"
            Random random = new Random();
		    String code = "";
		    for (int i = 0; i < 8; i++) {
			    code = code + random.nextInt(10);
		    }
            customer.setVerificationCode(code);
            SendEmails send = new SendEmails();
			send.sendRegistrationVerification(toCreate.getEmail(), toCreate.getFirstName(), code);

            customerRepository.save(customer);

            if (addressInput) {
                Address address = new Address();
                address.setUserId(customer.getUserId());
                address.setStreet(toCreate.getStreet());
                address.setCity(toCreate.getCity());
                address.setState(toCreate.getState());
                address.setZipcode(toCreate.getZipcode());
                address.setUseCase("both");
                addressRepository.save(address);
            }

            if (paymentInput) {
                PaymentInfo paymentInfo = new PaymentInfo();

                String encodedCardNumber = Base64.getEncoder().encodeToString(toCreate.getCardNumber().getBytes());

                paymentInfo.setUserId(customer.getUserId());
                paymentInfo.setHashedCardNumber(encodedCardNumber);
                paymentInfo.setExpiration(toCreate.getExpiration());
                paymentInfo.setCardType(toCreate.getCardType().toLowerCase());
                paymentInfo.setBillingAddress(addressRepository.findByUserId(customer.getUserId()).getAddressId());

                paymentInfoRepository.save(paymentInfo);
            }

            return "redirect:registrationConfirmation";
        } else {
            return "userRegistration";
        }
    }

    @RequestMapping(value = "/registrationConfirmation", method = RequestMethod.GET)
    public String getRegistrationConfirmationView(Model model) {
        return "registrationConfirmation";
    }

    private List<String> retrieveStateList() {
        List<String> states = new ArrayList<>();
        states.add("AK");
        states.add("AL");
        states.add("AR");
        states.add("AZ");
        states.add("CA");
        states.add("CO");
        states.add("CT");
        states.add("DC");
        states.add("DE");
        states.add("FL");
        states.add("GA");
        states.add("HI");
        states.add("IA");
        states.add("ID");
        states.add("IL");
        states.add("IN");
        states.add("KS");
        states.add("KY");
        states.add("LA");
        states.add("MA");
        states.add("MD");
        states.add("ME");
        states.add("MI");
        states.add("MN");
        states.add("MO");
        states.add("MS");
        states.add("MT");
        states.add("NC");
        states.add("ND");
        states.add("NE");
        states.add("NH");
        states.add("NJ");
        states.add("NM");
        states.add("NV");
        states.add("NY");
        states.add("OH");
        states.add("OK");
        states.add("OR");
        states.add("PA");
        states.add("RI");
        states.add("SC");
        states.add("SD");
        states.add("TN");
        states.add("TX");
        states.add("UT");
        states.add("VA");
        states.add("VT");
        states.add("WA");
        states.add("WI");
        states.add("WV");
        states.add("WY");
        return states;
    }

    private List<String> retrieveCardTypes() {
        List<String> cards = new ArrayList<>();
        cards.add("Visa");
        cards.add("MasterCard");
        cards.add("American Express");
        cards.add("Discover");
        return cards;
    }

}
