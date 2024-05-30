package com.example.initializer.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.initializer.cart.CartItem;
import com.example.initializer.cart.CartItemRepository;
import com.example.initializer.home.Book;
import com.example.initializer.home.BookRepository;
import com.example.initializer.registration.Customer;
import com.example.initializer.registration.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookActionController {
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    /* 
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchResultsPage(Model model, HttpServletRequest request) {
        return "seachResults";
    }
    */

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    public String addToCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
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
            return "redirect:userLogin";
        }

        long isbn = Long.valueOf(request.getParameter("isbn"));
        Book book = bookRepository.findByIsbn(isbn);
        int bookQuantity = 1;

        if (book.getQuantityInStock() == 0) {
            return "redirect:viewcart";
        }
        
        CartItem bookInCart = cartItemRepository.findByCartIdAndIsbn(currentUser.getUserId(), isbn);
        if (bookInCart != null) {
            bookQuantity = bookInCart.getQuantity() + 1;
        } else {
            bookInCart = new CartItem();
        }

        bookInCart.setCartId(currentUser.getUserId());
        bookInCart.setIsbn(isbn);
        bookInCart.setQuantity(bookQuantity);
        cartItemRepository.save(bookInCart);

        return "redirect:viewcart";
    }

    @RequestMapping(value = "/viewBookDetail", method = RequestMethod.POST)
    public String viewBookDetail(Model model, HttpServletRequest request) {
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

        long isbn = Long.valueOf(request.getParameter("isbn"));
        Book book = bookRepository.findByIsbn(isbn);
        String bookCategory = "" + book.getCategory().charAt(0);
        bookCategory = bookCategory.toUpperCase() + book.getCategory().substring(1);

        model.addAttribute("bookCover", book.getCover());
        model.addAttribute("bookTitle", book.getTitle());
        model.addAttribute("bookAuthor", book.getAuthors());
        model.addAttribute("bookCategory", bookCategory);
        model.addAttribute("bookEdition", book.getEdition());
        model.addAttribute("bookPublisher", book.getPublisher());
        model.addAttribute("bookPubYear", book.getPublicationYear());
        model.addAttribute("bookIsbn", book.getIsbn());
        model.addAttribute("bookQuantity", book.getQuantityInStock());
        model.addAttribute("bookSellingPrice", book.getSellingPrice());
        model.addAttribute("bookBuyingPrice", book.getBuyingPrice());

        return "bookDetails";
    }
}
