package com.example.initializer.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.initializer.registration.Customer;
import com.example.initializer.registration.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
	public String loginPage(@ModelAttribute("user") Customer user, Model model) {
		return "userLogin";
	}

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") Customer user, Model model, HttpServletRequest request) {

        if (user.getEmail().isBlank()) {
			model.addAttribute("emptyEmail", "Email and/or password cannot be empty!");
			return "userLogin";
		}

		if (user.getHashedPassword().isBlank()) {
			model.addAttribute("emptyPassword", "Password and/or password cannot be empty!");
			return "userLogin";
		}

        BCryptPasswordEncoder checkPassword = new BCryptPasswordEncoder();
		Optional<Customer> checkUser = customerRepository.findByEmail(user.getEmail());
        Customer loggedInUser = null;

        if (checkUser.isPresent()) {
            loggedInUser = checkUser.get();
        } else {
            model.addAttribute("invalidEmail", "Email or password does not match!");
			return "userLogin";
        }

        if (loggedInUser == null || !checkPassword.matches(user.getHashedPassword(), loggedInUser.getHashedPassword())) {
			model.addAttribute("invalidPassword", "Email or password does not match!");
			return "userLogin";
		}

        HttpSession session = request.getSession();
		session.setAttribute("loggedInUser", loggedInUser);
        // user can be inactive for 30m before they are forcefully signed out
		session.setMaxInactiveInterval(60 * 30);

        if (loggedInUser.getIsAdmin() == 0) {
            if (loggedInUser.getUserStatus().equals("inactive")) {
				return "redirect:verification";
			} else {
				return "redirect:homeOBS";
			}
		} else {
			return "redirect:adminHome";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser (Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
		if (session != null) {
			try {
				session.invalidate();
			} catch (IllegalStateException ise) {
				System.out.println("Session is already invalidated!");
			} // try catch
		} // if
		return "redirect:userLogin";
    }
}
