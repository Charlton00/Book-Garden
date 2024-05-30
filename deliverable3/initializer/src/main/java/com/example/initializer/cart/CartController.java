package com.example.initializer.cart;

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
import com.example.initializer.registration.Customer;
import com.example.initializer.registration.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private CustomerRepository CustomerRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @RequestMapping(value = "/viewcart", method = RequestMethod.GET)
    public String getCartView(Model model, HttpServletRequest request) {
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

        List<Book> cartItems = bookRepository.findAllCartItems(currentUser.getUserId());
		List<CartItem> bookQuantity = cartItemRepository.findAllByCartId(currentUser.getUserId());
        // automatically removes extra books from the database if it sees that there are books in the user's carts that have added more quantity than the bookstore has stock
        if (cartItems != null) {
			for (Book book : cartItems) {
				for (CartItem cartItem : bookQuantity) {
					if (cartItem.getIsbn() == book.getIsbn()) {
						if ((book.getQuantityInStock() - cartItem.getQuantity()) < 0) {
							model.addAttribute("outOfStock", "You have added more stock than how much the bookstore has, and the extra books have been removed from your cart.");
							cartItem.setQuantity(book.getQuantityInStock());
							if (cartItem.getQuantity() == 0) {
								cartItemRepository.delete(cartItem);
							} else {
								cartItemRepository.save(cartItem);
							}
						}
						break;
					}
				}
			}
		}

        cartItems = bookRepository.findAllCartItems(currentUser.getUserId());
		bookQuantity = cartItemRepository.findAllByCartId(currentUser.getUserId());
        List<UserCart> userCarts = createUserCart(cartItems, bookQuantity);
		currentUser.setUserCarts(userCarts);

		if (cartItems.isEmpty() || bookQuantity.isEmpty()) {
			model.addAttribute("emptyCart", "Your cart is currently empty!");
			model.addAttribute("disabled", "disabled");
		} else {
			model.addAttribute("cartItems", cartItems);
			model.addAttribute("bookQuantity", bookQuantity);
            model.addAttribute("allCartItems", userCarts);
		}

		double cartSubtotal = 0;
		int cartCount = 0;

		if (cartItems != null) {
			for (Book book : cartItems) {
				int count = 0;

				for (CartItem cartItem : bookQuantity) {
					if (cartItem.getIsbn() == book.getIsbn()) {
						count = cartItem.getQuantity();
						break;
					}
				}
				cartSubtotal += book.getSellingPrice() * count;
				cartCount += count;
			}
		}

        String subtotal = df.format(cartSubtotal);
        double taxNumber = cartSubtotal * 0.08;
        String taxes = df.format(taxNumber);
        double total = cartSubtotal + taxNumber;
        String totalPrice = df.format(total);

		model.addAttribute("subtotal", subtotal);
        model.addAttribute("taxes", taxes);
        model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartCount", cartCount);
        model.addAttribute("userId", currentUser.getUserId());

        return "viewcart";
    }

    @RequestMapping(value = "/viewcart", method = RequestMethod.POST, params = "addToCart")
    public String addToCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Customer currentUser = (Customer) session.getAttribute("loggedInUser");

		if (!currentUser.isSessionActive(session, currentUser)) {
			return "redirect:userLogin";
		}

		long bookIsbn = Long.valueOf(request.getParameter("isbn"));
		CartItem cartItem = cartItemRepository.findByCartIdAndIsbn(currentUser.getUserId(), bookIsbn);
		int quantity = cartItem.getQuantity() + 1;

		cartItem.setQuantity(quantity);
		cartItemRepository.save(cartItem);

        return "redirect:viewcart";
    }

    @RequestMapping(value = "/viewcart", method = RequestMethod.POST, params = "removeFromCart")
	public String removeFromCart(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
        Customer currentUser = (Customer) session.getAttribute("loggedInUser");

		if (!currentUser.isSessionActive(session, currentUser)) {
			return "redirect:userLogin";
		}

		long bookIsbn = Long.valueOf(request.getParameter("isbn"));
		CartItem cartItem = cartItemRepository.findByCartIdAndIsbn(currentUser.getUserId(), bookIsbn);
		int quantity = cartItem.getQuantity() - 1;

		if (quantity == 0) {
			cartItemRepository.delete(cartItem);
			return "redirect:viewcart";
		}

		cartItem.setQuantity(quantity);
		cartItemRepository.save(cartItem);

		return "redirect:viewcart";
	}

    @RequestMapping(value = "/viewcart", method = RequestMethod.POST, params = "removeAllBooks")
    public String removeAllBooks(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Customer currentUser = (Customer) session.getAttribute("loggedInUser");

		if (!currentUser.isSessionActive(session, currentUser)) {
			return "redirect:/login";
		}

		long bookIsbn = Long.valueOf(request.getParameter("isbn"));
		CartItem cartItem = cartItemRepository.findByCartIdAndIsbn(currentUser.getUserId(), bookIsbn);

		cartItemRepository.delete(cartItem);

        return "redirect:viewcart";
    }

    private List<UserCart> createUserCart(List<Book> cartItems, List<CartItem> bookQuantity) {
        List<UserCart> list = new ArrayList<>();
        if (cartItems != null) {
			for (Book book : cartItems) {
				for (CartItem cartItem : bookQuantity) {
                    if (cartItem.getIsbn() == book.getIsbn()) {
                        UserCart item = new UserCart();
                        item.setAuthors(book.getAuthors());
                        item.setCover(book.getCover());
                        item.setIsbn(book.getIsbn());
                        item.setQuantityInCart(cartItem.getQuantity());
                        item.setSellingPrice(book.getSellingPrice() * cartItem.getQuantity());
                        item.setTitle(book.getTitle());
                        list.add(item);
                        break;
                    }
                }
            }
        }
        return list;
    }
}
