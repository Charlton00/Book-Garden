package com.example.initializer.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.initializer.home.Book;
import com.example.initializer.home.BookRepository;
import com.example.initializer.registration.Customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SearchController {
    
    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "/searchError", method = RequestMethod.GET)
    public String getSearchErrorView(Model model, HttpServletRequest request) {
        model.addAttribute("book1", bookRepository.findByTitle("Harry Potter and the Philosophers Stone"));
        model.addAttribute("book2", bookRepository.findByTitle("The Strange Case of Origami Yoda"));
        return "searchError";
    }

    @RequestMapping(value = "/searchResults", method = RequestMethod.GET)
    public String search(@RequestParam("category") String category, @RequestParam("query") String query, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
        }
        Customer currentUser = (Customer) session.getAttribute("loggedInUser");

        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("logout", "Logout");
        } else {
            model.addAttribute("currentUser", new Customer());
            model.addAttribute("login", "Login");
        }

        if (query == null) {
            return "redirect:searchError";
        } else {
            List<Book> books = queryBooks(category, query);
            if (books.isEmpty()) {
                return "redirect:searchError";
            } else {
                model.addAttribute("searchResults", books);
                return "searchResults";
            }
        }
    }

    private List<Book> queryBooks(String category, String query) {
        List<Book> books;
            switch (category) {
                case "authors":
                    books = bookRepository.findByAuthors(query);
                    break;
                case "title":
                    books = bookRepository.findAllByTitle(query);
                    break;
                case "isbn":
                    books = bookRepository.findAllByIsbn(Long.parseLong(query));
                    break;
                default:
                    books = bookRepository.findAllBooks(query);
                    break;
            }
        return books;
    }
}
