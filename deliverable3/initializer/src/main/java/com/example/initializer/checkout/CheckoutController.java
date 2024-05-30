package com.example.initializer.checkout;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.initializer.cart.CartItem;
import com.example.initializer.cart.CartItemRepository;
import com.example.initializer.home.Book;
import com.example.initializer.home.BookRepository;
import com.example.initializer.orders.CreateOrder;
import com.example.initializer.orders.OrderItem;
import com.example.initializer.orders.OrderItemRepository;
import com.example.initializer.orders.OrderStatus;
import com.example.initializer.orders.Orders;
import com.example.initializer.orders.OrdersRepository;
import com.example.initializer.registration.Address;
import com.example.initializer.registration.AddressRepository;
import com.example.initializer.registration.Customer;
import com.example.initializer.registration.PaymentInfo;
import com.example.initializer.registration.PaymentInfoRepository;
import com.example.initializer.registration.SendEmails;

import java.sql.Timestamp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private AddressRepository addressRepository;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @RequestMapping(value = "/orderSummary", method = RequestMethod.GET)
    public String getCheckoutView(Model model, HttpServletRequest request) {
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

        PaymentInfo paymentInfo = paymentInfoRepository.findByUserId(currentUser.getUserId());
        Address address = addressRepository.findByUserId(currentUser.getUserId());

        model.addAttribute("billingList", getBillingAddressList(currentUser.getUserId()));
        model.addAttribute("shippingList", getShippingAddressList(currentUser.getUserId()));
        model.addAttribute("paymentList", getPaymentList(currentUser.getUserId()));
        model.addAttribute("firstName", currentUser.getFirstName());
        model.addAttribute("lastName", currentUser.getLastName());
        model.addAttribute("inputBillingAddress", addressRepository.findByUserId(currentUser.getUserId()).getStreet());
        model.addAttribute("billingCity", addressRepository.findByUserId(currentUser.getUserId()).getCity());
        model.addAttribute("billingState", addressRepository.findByUserId(currentUser.getUserId()).getState());
        model.addAttribute("billingZip", addressRepository.findByUserId(currentUser.getUserId()).getZipcode());

        byte[] decodedBytes = Base64.getDecoder().decode(paymentInfo.getHashedCardNumber());
        String cardNumber = new String(decodedBytes);

        model.addAttribute("cardNumber", cardNumber);
        model.addAttribute("cardName", currentUser.getFirstName() + " " + currentUser.getLastName());
        model.addAttribute("expirationDate", paymentInfo.getExpiration());

        List<Book> cartItems = bookRepository.findAllCartItems(currentUser.getUserId());
		List<CartItem> bookQuantity = cartItemRepository.findAllByCartId(currentUser.getUserId());
        
        double cartSubtotal = 0;
        for (Book book : cartItems) {
            int count = 0;
            for (CartItem cartItem : bookQuantity) {
                if (cartItem.getIsbn() == book.getIsbn()) {
					count = cartItem.getQuantity();
					break;
				}
                //cartSubtotal += book.getSellingPrice() * count;
            }
            cartSubtotal += book.getSellingPrice() * count;
        }
        double tax = cartSubtotal * 0.08;
        double finalTotal = cartSubtotal + tax;
        String stringCartSubtotal = df.format(cartSubtotal);
        String stringTax = df.format(tax);
        String stringFinalTotal = df.format(finalTotal);

        model.addAttribute("subtotal", stringCartSubtotal);
        model.addAttribute("taxes", stringTax);
        model.addAttribute("totalPrice", stringFinalTotal);
        model.addAttribute("createOrder", new CreateOrder());
        /* 
        PaymentInfo currentPaymentInfo = paymentInfoRepository.findByUserId(currentUser.getUserId());
        long addressId = addressRepository.findByUserId(currentUser.getUserId()).getAddressId();
        Orders order = new Orders();
        order.setOrderTime(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setUserId(currentUser.getUserId());
        order.setPaymentId(paymentInfo.getPaymentId());
        order.setShippingAddress(address.getAddressId());
        ordersRepository.save(order);
        ordersRepository.flush();
        */
        return "orderSummary";
    }

    @Transactional
    @RequestMapping(value = "/orderSummary", method = RequestMethod.POST)
    public String makeOrder(@ModelAttribute("createOrder") CreateOrder createOrder, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Customer currentUser = (Customer) session.getAttribute("loggedInUser");

		if (!currentUser.isSessionActive(session, currentUser)) {
			return "redirect:login";
		}
        
        Address newShippingAddress = new Address();
        Address newBillingaddress = new Address();
        PaymentInfo newPaymentInfo = new PaymentInfo(); 
        PaymentInfo currentPaymentInfo = paymentInfoRepository.findByUserId(currentUser.getUserId());
        long addressId = addressRepository.findByUserId(currentUser.getUserId()).getAddressId();
        
        Orders order = new Orders();
        order.setOrderTime(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setUserId(currentUser.getUserId());
        order.setPaymentId(currentPaymentInfo.getPaymentId());
        order.setShippingAddress(addressId);

        List<Book> cartItems = bookRepository.findAllCartItems(currentUser.getUserId());
		List<CartItem> bookQuantity = cartItemRepository.findAllByCartId(currentUser.getUserId());
        boolean useSelectBillingAddress = false;
        
        double cartSubtotal = 0;
        for (Book book : cartItems) {
            int count = 0;
            for (CartItem cartItem : bookQuantity) {
                if (cartItem.getIsbn() == book.getIsbn()) {
					count = cartItem.getQuantity();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order); //orderItem.setOrderId(order.getOrderId())
                    orderItem.setIsbn(book.getIsbn());
                    orderItem.setQuantity(count);
                    order.getOrderItems().add(orderItem);
                    //orderItemRepository.save(orderItem);
					break;
				}
                //cartSubtotal += book.getSellingPrice() * count;
            }
            cartSubtotal += book.getSellingPrice() * count;
        }
        double tax = cartSubtotal * 0.08;
        double finalTotal = cartSubtotal + tax;
        order.setSubtotal(cartSubtotal);
        order.setTotalPrice(finalTotal);
        ordersRepository.saveAndFlush(order);

        for (CartItem cartItem : bookQuantity) {
            cartItemRepository.delete(cartItem);
        }

        if (createOrder.getInputBillingAddress().isBlank() && 
            createOrder.getBillingCity().isBlank() && 
            createOrder.getBillingState().isBlank() && 
            createOrder.getBillingZip().isBlank()) {
                Address address = addressRepository.findByStreet(createOrder.getBillingAddress());
                addressId = address.getAddressId();
                useSelectBillingAddress = true;
        } else {
            newBillingaddress.setUserId(currentUser.getUserId());
            newBillingaddress.setStreet(createOrder.getInputBillingAddress());
            newBillingaddress.setCity(createOrder.getBillingCity());
            newBillingaddress.setState(createOrder.getBillingState());
            newBillingaddress.setZipcode(createOrder.getBillingZip());
            newBillingaddress.setUseCase("billing");
            newBillingaddress = addressRepository.saveAndFlush(newBillingaddress);
        }

        if (createOrder.getChecked()) {
            if (useSelectBillingAddress) {
                order.setShippingAddress(addressId);
            } else {
                order.setShippingAddress(newBillingaddress.getAddressId());
            }
        } else {
            if (createOrder.getInputShippingAddress().isBlank() && 
                createOrder.getShippingCity().isBlank() && 
                createOrder.getShippingState().isBlank() && 
                createOrder.getShippingZip().isBlank()) {
                    Address shippingAddress = addressRepository.findByStreet(createOrder.getShippingAddress());
                    order.setShippingAddress(shippingAddress.getAddressId());
            } else {
                newShippingAddress.setStreet(createOrder.getInputShippingAddress());
                newShippingAddress.setCity(createOrder.getShippingCity());
                newShippingAddress.setState(createOrder.getShippingState());
                newShippingAddress.setZipcode(createOrder.getShippingZip());
                newShippingAddress.setUseCase("shipping");
                newShippingAddress.setUserId(currentUser.getUserId());
                newShippingAddress = addressRepository.saveAndFlush(newShippingAddress);
                order.setShippingAddress(newShippingAddress.getAddressId());
            }
        }

        if (createOrder.getCardNumber().isBlank() && 
            createOrder.getCardName().isBlank() && 
            createOrder.getExpirationDate().isBlank()) {
                //long paymentId = getPaymentIdFromString(createOrder.getCard());
                //PaymentInfo paymentInfo = paymentInfoRepository.findByUserId(currentUser.getUserId());
                order.setPaymentId(currentPaymentInfo.getPaymentId());
        } else {
            newPaymentInfo.setUserId(currentUser.getUserId());
            String hashedCardNumber = Base64.getEncoder().encodeToString(createOrder.getCardNumber().getBytes());
            newPaymentInfo.setHashedCardNumber(hashedCardNumber);
            if (useSelectBillingAddress) {
                newPaymentInfo.setBillingAddress(addressId);
                newPaymentInfo.setExpiration(createOrder.getExpirationDate());
                newPaymentInfo = paymentInfoRepository.saveAndFlush(newPaymentInfo);
                order.setPaymentId(newPaymentInfo.getPaymentId());
            } else {
                newPaymentInfo.setBillingAddress(newBillingaddress.getAddressId());
                newPaymentInfo.setExpiration(createOrder.getExpirationDate());
                newPaymentInfo = paymentInfoRepository.saveAndFlush(newPaymentInfo);
                order.setPaymentId(newPaymentInfo.getPaymentId());
            }
        }
        
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            long isbn = orderItem.getIsbn();
            int quantity = orderItem.getQuantity();
            Book book = bookRepository.findByIsbn(isbn);
            book.setQuantityInStock(book.getQuantityInStock() - quantity);
            bookRepository.save(book);
        }
        ordersRepository.save(order);
        return "redirect:orderConfirmation";
    }

    @RequestMapping(value = "/orderConfirmation", method=RequestMethod.GET)
    public String confirmOrder(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Customer currentUser = (Customer) session.getAttribute("loggedInUser");
        
        String email = currentUser.getEmail();
        String firstName = currentUser.getFirstName();
        List<Orders> userOrders = ordersRepository.findByUserId(currentUser.getUserId());
        Orders currentOrder = userOrders.get(userOrders.size() - 1);
        String orderStatus = currentOrder.getOrderStatus().toString();
       
        String orderHistoryLink = request.getScheme() 
                                + "://" 
                                + request.getServerName() 
                                + ":" 
                                + request.getServerPort() 
                                + request.getContextPath() 
                                + "/orderHistory"; // check if this is correct path

        SendEmails confirmationEmail = new SendEmails();
        confirmationEmail.sendOrderConfirmation(email, firstName, orderStatus, orderHistoryLink);
        return "orderConfirmation";
    }

    private List<String> getBillingAddressList(long userId) {
        String street;
        List<String> billingAddresses = new ArrayList<>();
        List<Long> paymentInfoAddresses = paymentInfoRepository.findAllBillingAddresses(userId);
        for (long address : paymentInfoAddresses) {
            Address userAddress = addressRepository.findById(address);
            street = userAddress.getStreet();
            billingAddresses.add(street);
        }
        return billingAddresses;
    }
    
    private List<String> getShippingAddressList(long userId) {
        String street;
        List<String> shippingAddresses = new ArrayList<>();
        Address userAddress = addressRepository.findByUserId(userId);
        street = userAddress.getStreet();
        shippingAddresses.add(street);
        return shippingAddresses;
    }


    private List<String> getPaymentList(long userId) {
        String cardType;
        String first12 = "**** **** **** ";
        String last4;
        String finalPayment;
        List<String> payments = new ArrayList<>();
        PaymentInfo payment = paymentInfoRepository.findByUserId(userId);
        cardType = payment.getCardType();
        byte[] decodedBytes = Base64.getDecoder().decode(payment.getHashedCardNumber());
        String cardNumber = new String(decodedBytes);
        last4 = cardNumber.substring(12, 16);
        finalPayment = cardType + first12 + last4;
        payments.add(finalPayment);
        return payments;
    }
}