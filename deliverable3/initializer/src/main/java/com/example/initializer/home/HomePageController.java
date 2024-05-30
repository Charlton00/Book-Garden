package com.example.initializer.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.initializer.registration.Customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController {
    
    // to get cart items at a later time
    // @RequestMapping(value = "", method = RequestMethod.GET)
    // public String

    @Autowired
    private BookRepository bookRepository;

    // @Autowired
    // private CustomerRepository customerRepository;

    @RequestMapping(value = "/homeOBS", method = RequestMethod.GET)
    public String retrieveHomePage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Customer currentUser = (Customer) session.getAttribute("loggedInUser");

        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("logout", "Logout");
            if (!currentUser.isSessionActive(session, currentUser)) {
                return "redirect:userLogin";
            }
        } else {
            model.addAttribute("currentUser", new Customer());
            model.addAttribute("login", "Login");
        }
        model.addAttribute("book1", bookRepository.findByTitle("Harry Potter and the Philosophers Stone"));
        model.addAttribute("book2", bookRepository.findByTitle("The Strange Case of Origami Yoda"));
        model.addAttribute("book3", bookRepository.findByTitle("The Phantom Tollbooth"));
        model.addAttribute("book4", bookRepository.findByTitle("The Giver"));
        model.addAttribute("book5", bookRepository.findByTitle("Chainsaw Man Vol. 1"));
        model.addAttribute("book6", bookRepository.findByTitle("Minecraft: Redstone Handbook: An offical Mojang Book"));
        // List<Book> featuredBooks = new ArrayList<Book>();
        // List<Book> topSellers = new ArrayList<Book>();
        // featuredBooks.add(bookRepository.findByTitle("Harry Potter and the Philosopher's Stone"));
        // featuredBooks.add(bookRepository.findByTitle("The Strange Case of Origami Yoda"));
        // topSellers.add(bookRepository.findByTitle("The Phantom Tollbooth"));
        // topSellers.add(bookRepository.findByTitle("The Giver"));
        // topSellers.add(bookRepository.findByTitle("Chainsaw Man"));
        // topSellers.add(bookRepository.findByTitle("Redstone Handbook"));
        // model.addAttribute("id", id);
        // model.addAttribute("featuredBooks", featuredBooks);
        // model.addAttribute("topSellers", topSellers);
        return "homeOBS";
    }

}
