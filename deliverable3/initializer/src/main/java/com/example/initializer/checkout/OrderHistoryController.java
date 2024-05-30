package com.example.initializer.checkout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.initializer.home.Book;
import com.example.initializer.home.BookRepository;
import com.example.initializer.orders.AllUserOrders;
import com.example.initializer.orders.OrderItem;
import com.example.initializer.orders.OrderItemHistory;
import com.example.initializer.orders.OrderItemRepository;
import com.example.initializer.orders.Orders;
import com.example.initializer.orders.OrdersRepository;
import com.example.initializer.registration.Customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderHistoryController {
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
    public String getOrderHistoryView(Model model, HttpServletRequest request) {
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

        List<Orders> orders = orderRepository.findByUserId(currentUser.getUserId());
        List<AllUserOrders> userOrderHistory = getAllUserOrders(orders);
        model.addAttribute("orderHistory", userOrderHistory);
        return "orderHistory";
    }

    public List<AllUserOrders> getAllUserOrders(List<Orders> orders) {
        List<AllUserOrders> result = new ArrayList<>();
        List<OrderItem> orderItems;
        Book book;
        for (Orders order : orders) {
            AllUserOrders userOrder = new AllUserOrders();
            userOrder.setId(order.getOrderId());
            userOrder.setTotalPrice(order.getTotalPrice());
            List<OrderItemHistory> orderItemHistories = new ArrayList<>();
            orderItems = orderItemRepository.findAllByOrderId(order.getOrderId());
            for (OrderItem items : orderItems) {
                OrderItemHistory userHistory = new OrderItemHistory();
                userHistory.setCopies(items.getQuantity());
                Book orderBook = bookRepository.findByIsbn(items.getIsbn());
                userHistory.setTotalPrice(orderBook.getSellingPrice() * items.getQuantity());
                userHistory.setIsbn(items.getIsbn()); 
                book = bookRepository.findByIsbn(items.getIsbn());
                userHistory.setAuthor(book.getAuthors());
                userHistory.setCover(book.getCover());
                userHistory.setTitle(book.getTitle());
                orderItemHistories.add(userHistory);
            }
            userOrder.setOrderItems(orderItemHistories);
            result.add(userOrder);
        }
        return result;
    }
}
