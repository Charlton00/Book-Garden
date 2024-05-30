package com.example.initializer.editProfile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.initializer.registration.Address;
import com.example.initializer.registration.AddressRepository;
import com.example.initializer.registration.CreateUser;
import com.example.initializer.registration.Customer;
import com.example.initializer.registration.CustomerRepository;
import com.example.initializer.registration.PaymentInfo;
import com.example.initializer.registration.PaymentInfoRepository;
import com.example.initializer.registration.SendEmails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EditProfileController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private AddressRepository addressRepository;
    
    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public String getEditProfilePage(Model model, HttpServletRequest request) {
        model.addAttribute("statesList", retrieveStateList());
        model.addAttribute("cardTypesList", retrieveCardTypes());
        HttpSession session = request.getSession(false);
        Customer user = (Customer) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("newPaymentInfo", new PaymentInfo());
            model.addAttribute("newAddress", new Address());
            model.addAttribute("editUser", new CreateUser());
            model.addAttribute("logout", "Logout");

			if (user.getSubscribed() == 1) {
				model.addAttribute("subscribedStatus", "true");
			}

            Address address = addressRepository.findByUserId(user.getUserId());
			if (address != null) {
				model.addAttribute("address", address);
				model.addAttribute("state", address.getState());
			} else {
				model.addAttribute("state", "Select a state");
			}

			PaymentInfo paymentInfo = paymentInfoRepository.findByUserId(user.getUserId());
			if (paymentInfo != null) {
				model.addAttribute("paymentInfo", paymentInfo);
                String rawCardNumber = new String(Base64.getDecoder().decode(paymentInfo.getHashedCardNumber().getBytes()));
                model.addAttribute("rawCardNumber", rawCardNumber);
                model.addAttribute("editCardType", retrieveCardType(paymentInfo.getCardType()));
			}
        } else {
            return "redirect:userLogin";
        }
        return "editProfile";
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST, params = "cancel")
	public String cancelEditProfile(Model model) {
		return "redirect:homeOBS";
	} // CancelEditProfile

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String submitEditProfile(@ModelAttribute("editUser") CreateUser editUser, Model model, HttpServletRequest request) {
        model.addAttribute("statesList", retrieveStateList());
        model.addAttribute("cardTypesList", retrieveCardTypes());
		HttpSession session = request.getSession(false);
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
		// if session is not active
		if (!loggedInUser.isSessionActive(session, loggedInUser)) {
            session.invalidate();
			return "redirect:/login";
		} // if

		boolean canSubmit = true;
		boolean addAddress = false;
        boolean addPayment = false;

		Address address = addressRepository.findByUserId(loggedInUser.getUserId());
		PaymentInfo paymentInfo = paymentInfoRepository.findByUserId(loggedInUser.getUserId());

		if (editUser.getFirstName().isBlank()) {
			model.addAttribute("emptyFirstName", "First name cannot be empty!");
			canSubmit = false;
		}

		if (editUser.getLastName().isBlank()) {
			model.addAttribute("emptyLastName", "Last name cannot be empty!");
			canSubmit = false;
		}

        if (editUser.getPassword().isBlank()) {
            model.addAttribute("emptyPassword", "Please enter your old or new password before submitting!");
        } else if (!editUser.getPassword().isBlank() && editUser.getPassword().length() < 8) {
			model.addAttribute("passwordLength", "Password must be 8 characters long!");
			canSubmit = false;
		}

        if (editUser.getConfirmPassword().isBlank()) {
            model.addAttribute("emptyConfirmPassword", "Confirm password cannot be empty!");
            canSubmit = false;
        } else if (!(editUser.getPassword().equals(editUser.getConfirmPassword()))) {
			model.addAttribute("notMatchingPasswords", "Passwords do not match!");
			canSubmit = false;
		}

		if (!(editUser.getStreet().isBlank()) || !(editUser.getCity().isBlank()) || !(editUser.getState().isBlank()) || !(editUser.getZipcode().isBlank())) {

            if (editUser.getStreet().isBlank()) {
				model.addAttribute("emptyStreet", "Street address cannot be empty!");
				canSubmit = false;
			}

            if (editUser.getZipcode().isBlank()) {
				model.addAttribute("emptyZipcode", "ZIP Code cannot be empty!");
				canSubmit = false;
			}

            if (editUser.getCity().isBlank()) {
				model.addAttribute("emptyCity", "City cannot be empty!");
				canSubmit = false;
			}

            if (editUser.getState().isBlank()) {
				model.addAttribute("emptyState", "State cannot be empty!");
				canSubmit = false;
			}

            addAddress = true;
        }

        if (!(editUser.getCardType().isBlank()) || !(editUser.getExpiration().isBlank()) || !(editUser.getCvv().isBlank()) || !(editUser.getCardNumber().isBlank())) {

            if (editUser.getCardType().isBlank()) {
				model.addAttribute("emptyCardType", "Card type cannot be empty!");
				canSubmit = false;
			}

            if (editUser.getExpiration().isBlank()) {
				model.addAttribute("emptyExpiration", "Card expiration cannot be empty!");
				canSubmit = false;
			}

            if (editUser.getCvv().isBlank()) {
				model.addAttribute("emptyCvv", "CVV cannot be empty!");
				canSubmit = false;
			}

            if (editUser.getCardNumber().isBlank()) {
				model.addAttribute("emptyCardNumber", "Card number cannot be empty!");
				canSubmit = false;
			} else if (editUser.getCardNumber().length() != 16) {
                model.addAttribute("shortCardNumber", "Credit card numbers must be 16 digits long!");
                canSubmit = false;
            }

            addPayment = true;
        }

		if (canSubmit == true) {
			if (!editUser.getPassword().isBlank()) {
				BCryptPasswordEncoder encodePassword = new BCryptPasswordEncoder();
				loggedInUser.setHashedPassword(encodePassword.encode(editUser.getPassword()));
			}
			loggedInUser.setFirstName(editUser.getFirstName());
			loggedInUser.setLastName(editUser.getLastName());

			if (editUser.getSubscribed() != null) {
				if (editUser.getSubscribed().equals("Y")) {
				    loggedInUser.setSubscribed(1);
				}
			} else {
				loggedInUser.setSubscribed(0);
			}

			customerRepository.save(loggedInUser);

			if (addAddress) {
                if (address == null) {
                    address = new Address();
                }
                address.setUserId(loggedInUser.getUserId());
                address.setStreet(editUser.getStreet());
                address.setCity(editUser.getCity());
                address.setState(editUser.getState());
                address.setZipcode(editUser.getZipcode());
                address.setUseCase("both");
                addressRepository.save(address);
            }

            if (addPayment) {
                if (paymentInfo == null) {
                    paymentInfo = new PaymentInfo();
                }

                String encodedCardNumber = Base64.getEncoder().encodeToString(editUser.getCardNumber().getBytes());

                paymentInfo.setUserId(loggedInUser.getUserId());
                paymentInfo.setHashedCardNumber(encodedCardNumber);
                paymentInfo.setExpiration(editUser.getExpiration());
                paymentInfo.setCardType(editUser.getCardType().toLowerCase());
                paymentInfo.setBillingAddress(addressRepository.findByUserId(loggedInUser.getUserId()).getAddressId());

                paymentInfoRepository.save(paymentInfo);
            }

			SendEmails sendEmail = new SendEmails();
			String message = "Your profile on BookGarden has been successfully updated.";
			String subject = "Your BookGarden Profile Has Been Updated!";
			sendEmail.sendCustomMessage(loggedInUser.getEmail(), loggedInUser.getFirstName(), message, subject);

			return "redirect:editProfile";
		} else {
			return "editProfile";
		}
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

    private String retrieveCardType(String cardType) {
        switch (cardType) {
            case "mastercard":
                return "MasterCard";
            case "visa":
                return "Visa";
            case "american express":
                return "American Express";
        }
        return "Discover";
    }
    
}
