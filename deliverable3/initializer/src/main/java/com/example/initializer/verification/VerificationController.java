package com.example.initializer.verification;

import org.springframework.beans.factory.annotation.Autowired;
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
public class VerificationController {

    @Autowired
	private CustomerRepository customerRepository;

	@RequestMapping(value = "/verification", method = RequestMethod.GET)
	public String VerificationForm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Customer loggedUser = (Customer) session.getAttribute("loggedInUser");

		if (!loggedUser.isSessionActive(session, loggedUser)) {
			session.invalidate();
			return "redirect:userLogin";
		}

		model.addAttribute("currentUser", new Customer());
		return "verification";
	}

    @RequestMapping(value = "/verification", method = RequestMethod.POST, params = "verify")
	public String Verify(@ModelAttribute("currentUser") Customer customer, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Customer toVerify = (Customer) session.getAttribute("loggedInUser");

		if (customer.getVerificationCode().isBlank()) {
			model.addAttribute("emptyVerificationCode", "Verification code cannot be empty!");
			return "verification";
		}

		if (!toVerify.getVerificationCode().equals(customer.getVerificationCode())) {
			model.addAttribute("wrongVerificationCode", "Verification code is invalid!");
			return "verification";
		} else {
			toVerify.setUserStatus("active");
			toVerify.setVerificationCode(null);
			customerRepository.save(toVerify);
			return "redirect:homeOBS";
		}
	}
}
