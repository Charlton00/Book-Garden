package com.example.initializer.admin;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.initializer.home.Book;
import com.example.initializer.home.BookRepository;
import com.example.initializer.registration.Customer;
import com.example.initializer.registration.CustomerRepository;
import com.example.initializer.registration.SendEmails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/adminHome", method = RequestMethod.GET)
    public String getAdminPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:userLogin";
        } else {
            Customer customer = (Customer) session.getAttribute("loggedInUser");
            if (customer != null && customer.getIsAdmin() == 1) {
                model.addAttribute("admin", customer);
		        model.addAttribute("logout", "Logout");
                return "adminHome";
            } else {
                return "redirect:userLogin";
            }
        }
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.GET)
    private String getAddBookView(@ModelAttribute("newBook") Book newBook, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:userLogin";
        } else {
            Customer customer = (Customer) session.getAttribute("loggedInUser");
            if (customer != null && customer.getIsAdmin() == 1) {
                model.addAttribute("admin", customer);
                model.addAttribute("logout", "Logout");
                return "addBook";
            } else {
                return "redirect:userLogin";
            }
        }
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    private String addNewBook(@ModelAttribute("newBook") Book newBook, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:userLogin";
        } else {
            Customer customer = (Customer) session.getAttribute("loggedInUser");
            if (customer != null && customer.getIsAdmin() == 1) {
                model.addAttribute("admin", customer);
                model.addAttribute("logout", "Logout");
            } else {
                return "redirect:userLogin";
            }
        }

		Book book = new Book();
        boolean canAdd = true;

		if (newBook.getTitle().isBlank()) {
			model.addAttribute("emptyTitle", "Title cannot be empty!");
            canAdd = false;
		}

        if (newBook.getIsbn() == 0) {
            model.addAttribute("emptyIsbn", "ISBN cannot be empty!");
            canAdd = false;
        }

        if (newBook.getCategory().isBlank()) {
            model.addAttribute("emptyCategory", "Category cannot be empty!");
            canAdd = false;
        }

        if (newBook.getAuthors().isBlank()) {
            model.addAttribute("emptyAuthor", "Author cannot be empty!");
            canAdd = false;
        }

        if (newBook.getSellingPrice() == 0) {
            model.addAttribute("emptySellingPrice", "Selling price cannot be zero!");
            canAdd = false;
        }

        if (newBook.getBuyingPrice() == 0) {
            model.addAttribute("emptyBuyingPrice", "Buying price cannot be zero!");
            canAdd = false;
        }

        if (newBook.getEdition().isBlank()) {
            model.addAttribute("emptyEdition", "Edition cannot be empty!");
            canAdd = false;
        }

        if (newBook.getPublisher().isBlank()) {
            model.addAttribute("emptyPublisher",  "Publisher cannot be empty!");
            canAdd = false;
        }

        if (canAdd) {
            book.setIsbn(newBook.getIsbn());
            book.setTitle(newBook.getTitle());
            book.setAuthors(newBook.getAuthors());
            book.setCategory(newBook.getCategory());
            book.setCover("../../images/" + newBook.getTitle().replace(' ', '-') + ".png");
            book.setEdition(newBook.getEdition());
            book.setPublisher(newBook.getPublisher());
            book.setPublicationYear(newBook.getPublicationYear());
            book.setQuantityInStock(newBook.getQuantityInStock());
            book.setMinThreshold(newBook.getMinThreshold());
            book.setBuyingPrice(newBook.getBuyingPrice());
            book.setSellingPrice(newBook.getSellingPrice());
            bookRepository.save(book);
        }

        return "redirect:addBook";
    }

    @RequestMapping(value = "/addPromotion", method = RequestMethod.GET)
    private String getAddPromotionView(@ModelAttribute("newPromotion") Promotion newPromotion, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:userLogin";
        } else {
            Customer customer = (Customer) session.getAttribute("loggedInUser");
            if (customer != null && customer.getIsAdmin() == 1) {
                model.addAttribute("admin", customer);
                model.addAttribute("logout", "Logout");
                return "addPromotion";
            } else {
                return "redirect:userLogin";
            }
        }
    }

    @RequestMapping(value = "/addPromotion", method = RequestMethod.POST)
    private String addNewPromotion(@ModelAttribute("newPromotion") Promotion newPromotion, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:userLogin";
        } else {
            Customer customer = (Customer) session.getAttribute("loggedInUser");
            if (customer != null && customer.getIsAdmin() == 1) {
                model.addAttribute("admin", customer);
                model.addAttribute("logout", "Logout");
            } else {
                return "redirect:userLogin";
            }
        }

		Promotion promotion = new Promotion();
        boolean canAdd = true;

        Promotion promotionCode = promotionRepository.findByPromotionId(newPromotion.getPromotionId());
        System.out.println("promotionCode: " + promotionCode);
        if (promotionCode != null) {
            model.addAttribute("existingCode", "Promotion code already exists!");
            canAdd = false;
        }

        if (newPromotion.getPromotionId().isBlank()) {
            model.addAttribute("emptyCode", "Promotion code cannot be empty!");
            canAdd = false;
        }

        if (newPromotion.getDiscount() < 1) {
            model.addAttribute("lessThan1", "Discount amount cannot be lower than 1%!");
            canAdd = false;
        }

        if (newPromotion.getDiscount() > 100) {
            model.addAttribute("moreThan100", "Discount amount cannot be higher than 100%!");
            canAdd = false;
        }

        if (newPromotion.getEndTime() == null) {
            model.addAttribute("emptyDate", "End time cannot be empty!");
            canAdd = false;
        }

        Timestamp todayTS = new Timestamp(System.currentTimeMillis());
        LocalDateTime date = todayTS.toLocalDateTime();
        String today = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth() + " 00:00:00";
        Timestamp compareToday = Timestamp.valueOf(today);
        System.out.println("today's date is: " + compareToday);
        String selectedDate = newPromotion.getEndTime() + " 00:00:00";
        Timestamp compareSelected = Timestamp.valueOf(selectedDate);
        System.out.println("the date selected is: " + compareSelected);
        
        System.out.println(compareSelected.compareTo(compareToday));
        if (compareSelected.compareTo(compareToday) < 0) {
            model.addAttribute("badDate", "New promotion expiration cannot be in the past!");
            canAdd = false;
        }

        if (canAdd) {
            promotion.setPromotionId(newPromotion.getPromotionId());
            promotion.setEndTime(newPromotion.getEndTime());
            promotion.setDiscount(newPromotion.getDiscount());
            promotion.setSent(1);
            promotionRepository.save(promotion);
            List<Customer> userList = customerRepository.findAllSubscribedCustomers();
            System.out.println("amount of users to email: " + userList.size());
            for(Customer c : userList) {
                String name = c.getFirstName();
                SendEmails send = new SendEmails();
			    String message = "Hello, " + name + "!\n" + "Use code " + promotion.getPromotionId() + " for " + promotion.getDiscount() + "% off on your order.\nIt will expire on " + promotion.getEndTime() + ".\nEnjoy!";
			    String subject = "New BookGarden Promotion!";
			    send.sendCustomMessage(c.getEmail(), c.getFirstName(), message, subject);
            }
            return "redirect:addPromotion";
        }

        return "addPromotion";
    }
}
