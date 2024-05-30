package com.example.initializer.forgotPassword;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.initializer.registration.CreateUser;
import com.example.initializer.registration.Customer;
import com.example.initializer.registration.CustomerRepository;
import com.example.initializer.registration.SendEmails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String getForgotPasswordScreen(Model model) {
        model.addAttribute("user", new Customer());
        return "forgotPassword";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public String enterPasswordsScreen(Model model, @ModelAttribute("user") Customer user, HttpServletRequest request) {
        if (user.getEmail().isBlank()) {
            model.addAttribute("emptyEmail", "Email cannot be empty!");
            return "forgotPassword";
        }

        Optional<Customer> checkExist = customerRepository.findByEmail(user.getEmail());
        if (checkExist.isPresent()) {
            HttpSession session = request.getSession(true);
            Customer customer = checkExist.get();

            Random random = new Random();
		    String code = "";
		    for (int i = 0; i < 8; i++) {
			    code = code + random.nextInt(10);
		    }
            customer.setPasswordToken(code);
            SendEmails send = new SendEmails();
			String message = "You have requested a password change. Please use the following token in order to verify your account and reset your password: " + code;
			String subject = "BookGarden Password Reset Token";
    	    send.sendCustomMessage(customer.getEmail(), customer.getFirstName(), message, subject);
            customerRepository.save(customer);
            session.setAttribute("resettingUser", customer);
            return "redirect:inputNewPasswords";
        } else {
            model.addAttribute("doesNotExistEmail", "An account registered with this email does not exist!");
            return "forgotPassword";
        }
    }

    @RequestMapping(value = "/inputNewPasswords", method = RequestMethod.GET)
    public String newPasswordsScreen(Model model) {
        model.addAttribute("toReset", new CreateUser());
        return "inputNewPasswords";
    }

    @RequestMapping(value = "/inputNewPasswords", method = RequestMethod.POST)
    public String validateNewPasswords(Model model, @ModelAttribute("toReset") CreateUser toReset, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Customer customer = null;
        boolean canReset = true;
        BCryptPasswordEncoder encodePassword = new BCryptPasswordEncoder();

        if (session != null) {
            customer = (Customer) session.getAttribute("resettingUser");
            // String rawPassword = new String(Base64.getDecoder().decode(customer.getHashedPassword().getBytes()));
            if (toReset.getPassword().isBlank()) { // if new password is empty
                model.addAttribute("emptyPassword", "Password cannot be empty!");
                canReset = false;
            } else if (toReset.getPassword() != null && toReset.getPassword().length() < 8) { // if new password's length is less than 8
                model.addAttribute("passwordLength", "Password length cannot be less than 8!");
                canReset = false;
            }

            if (toReset.getConfirmPassword().isBlank()) {
                model.addAttribute("emptyConfirmPassword", "Confirm password cannot be empty!");
                canReset = false;
            } else if (!toReset.getPassword().equals(toReset.getConfirmPassword())) {
                model.addAttribute("notMatchingPasswords", "Passwords do not match!");
                canReset = false;
            }

            if (toReset.getPasswordToken().isBlank()) {
                model.addAttribute("emptyToken", "Password token cannot be empty! Please refer to the token sent to your email!");
                canReset = false;
            } else if (!toReset.getPasswordToken().equals(customer.getPasswordToken())) {
                model.addAttribute("tokenDoesNotMatch", "Invalid password token! Please refer to the token sent to your email!");
                canReset = false;
            }

            if (canReset) {
                customer.setPasswordToken(null);
			    customer.setHashedPassword(encodePassword.encode(toReset.getPassword()));
			    customerRepository.save(customer);

			    SendEmails send = new SendEmails();
			    String message = "You have successfully changed your account's password.";
			    String subject = "BookGarden Account Password Changed";

			    send.sendCustomMessage(customer.getEmail(), customer.getFirstName(), message, subject);
                session.invalidate();
			    return "redirect:/userLogin";
		    } else {
			    return "inputNewPasswords";
            }
        } else {
            return "redirect:forgotPassword";
        }
    }

    @RequestMapping(value = "/resetToken", method = RequestMethod.POST)
    public String generateNewToken(Model model, @ModelAttribute("toReset") CreateUser toReset, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Customer customer = (Customer) session.getAttribute("resettingUser");
        Random random = new Random();
		String code = "";
		for (int i = 0; i < 8; i++) {
		    code = code + random.nextInt(10);
		}
        customer.setPasswordToken(code);
        SendEmails send = new SendEmails();
		String message = "You have requested a new password change token. Please use the following token in order to verify your account and reset your password: " + code;
		String subject = "BookGarden Password Reset Token";
    	send.sendCustomMessage(customer.getEmail(), customer.getFirstName(), message, subject);
        customerRepository.save(customer);
        session.setAttribute("resettingUser", customer);
        return "redirect:inputNewPasswords";
    }
}