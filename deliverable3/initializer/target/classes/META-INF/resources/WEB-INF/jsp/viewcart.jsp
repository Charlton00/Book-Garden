<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <title>BookstoreHub - View Cart</title>
  <link rel="stylesheet" type="text/css" href="styles.css">
  <style>
    /* CSS styles for the headers */
    body {
      font-family: "Oswald", sans-serif;
      background: linear-gradient(to bottom, #f2f2eb, #f2f2eb);
    }

    header {
      background-color: #b98f68;
      padding: 20px;
      text-align: center;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: space-between;
      color: #f2f2eb;
    }

    header h1 {
      font-size: 70px;
      margin: 0;
    }

    header nav ul {
      list-style: none;
      padding: 0;
      margin: 0;
      display: flex;
      justify-content: flex-end;
      align-items: center;
    }

    header nav ul li {
      margin-right: 10px;
    }

    header nav ul li a {
      text-decoration: none;
      font-size: 16px;
      color: white;
      padding: 5px 15px;
      border: 1px solid #2b0d0a;
      border-radius: 5px;
      background-color: #78504b;
    }


    header nav ul li.search-bar {
      display: flex;
      align-items: center;
      margin-right: 10px;
    }

    header nav ul li.search-bar select {
      padding: 7px;
      margin-right: 0;
    }

    header nav ul li.search-bar input[type="text"] {
      padding: 7px;
      margin-right: 5px;
      width: 230px; /*300*/
      /* new */
      flex-grow: 1;
    }

    header nav ul li.search-bar button {
      padding: 7px 20px;
      background-color: #78504b;
      border: 1px solid #2b0d0a;
      color: white;
      border-radius: 5px;
      cursor: pointer;
      margin-left: -5px;
    }
    header nav ul li .checkout-button-header {
        /* This is here just to differ from the actual checkout button */
    }
    .logo {
      display: flex;
      align-items: center;
      margin-right: 20px;
    }

    .logo img {
      height: 150px;
      margin-right: 10px;
    }

    section {
      padding: 20px;
      text-align: center;
    }

    .cart-summary {
      background-color: #ffdab7;
      max-width: 600px;
      margin: 0 auto;
      padding: 30px;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      text-align: center;
      font-size: 16px;
      color: #4d4c4c;
      height: fit-content;
    }

    .cart-item {
      border-bottom: 1px solid #ddd;
      padding: 10px;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .cart-item img {
      width: 80px;
      height: 120px;
      object-fit: cover;
      margin-right: 10px;
    }

    .cart-item-info {
      flex-grow: 1;
      text-align: left;
    }

    .cart-item-info h3 {
      font-size: 18px;
      margin: 0;
    }

    .cart-item-info p {
      margin: 5px 0;
    }

    .cart-item-total {
      font-size: 18px;
    }

    .cart-item-total strong {
      color: #78504b;
    }

    .cart-item-actions {
      display: flex;
      align-items: center;
      margin-left: auto;
    }

    .cart-item-actions button {
      padding: 5px 10px;
      background-color: #570e06;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin-left: 10px;
    }

    .cart-total {
      margin-top: 20px;
      text-align: right;
    }

    .cart-total p {
      margin-bottom: 5px;
    }

    .cart-total strong {
      color: #78504b;
    }

    .checkout-button {
      padding: 10px 20px;
      background-color: #570e06;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-family: "Oswald", sans-serif;
    }

    footer {
      background-color: #996a44;
      padding: 5px;
      text-align: center;
      position: fixed;
      bottom: 0;
      left: 0;
      width: 100%;
    }

    .footer-content {
      color: white;
    }
  </style>
  <link href="https://fonts.googleapis.com/css?family=Oswald&display=swap" rel="stylesheet">
</head>

<body>
  <header>
    <div class="logo">
      <a href="/homeOBS"><img src="../../images/Logo_For_Bookstore_hub.jpg" alt="Logo"></a>
      <h1>BookGarden</h1>
    </div>
    <nav>
      <ul>
        <li class="search-bar">
          <form action="/searchResults" method="GET">
            <select name="category">
                <option value="all">All</option>
                <option value="authors">Authors</option>
                <option value="title">Title</option>
                <option value="isbn">ISBN</option>
            </select>
            <input type="text" name="query" placeholder="Search books..."/>
            <button type="submit" class="search-button"><i class="fa fa-search"></i></button>
          </form>
        </li>
        <li><a href="/editProfile" class="edit-button"><i class="fa fa-user-cog"></i>Edit Profile</a></li>
        <li><a href="/logout" class="login-button"><i class="fa fa-user"></i>${logout}</a></li>
        <li><a href="/orderSummary" class="checkout-button-header"><i class="fa fa-shopping-cart"></i>Checkout</a></li>
      </ul>
    </nav>
  </header>

  <section>
    <div class="cart-summary">
      <h2>Cart Summary</h2>
      <div>
        <h6 style="color: red">${outOfStock}</h6>
        <h6>${emptyCart}</h6>
      </div>

      
      <c:forEach items="${allCartItems}" var="cartItem">
        <div class="cart-item">
          <div class="col-sm-2">
            <img src="${cartItem.cover}" alt="book logo">
          </div>
          <div class="cart-item-info">
            <h3>${cartItem.title}</h3>
            <p>${cartItem.authors}</p>
          </div>
          <div class="cart-item-total">
            <p>Quantity: ${cartItem.quantityInCart}</p>
            <p>$${cartItem.sellingPrice}</p>
          </div>
          <div>
            <form method="post" action="/viewcart">
              <input type="hidden" name="_csrf" value="${_csrf.token}"/>
              <span style="float: right; margin-right: 65px;"></span><br>
              <span style="float: right; margin-right: 40px;">
                <input type="hidden" name="isbn" value="${cartItem.isbn}">
                <button class="increment-button" type="submit" name="addToCart"><i class="fa fa-plus"></i></button>
                <button class="decrement-button" type="submit" name="removeFromCart"><i class="fa fa-minus"></i></button>
                <button class="removefromcart" type="submit" name="removeAllBooks"><i class="fa fa-trash"></i></button>
              </span>
            </form>
          </div>
        </div>
      </c:forEach>
      <!-- <div class="cart-item">
        <img src="book1.jpg" alt="Book 1">
        <div class="cart-item-info">
          <h3>Harry Potter and the Philosopher's Stone</h3>
          <p>By: J.K. Rowling</p>
        </div>
        <div class="cart-item-total">
          <p>Qty: 2</p>
          <p>Total: <strong>$39.98</strong></p>
        </div>
        <div class="cart-item-actions">
          <button class="increment-button"><i class="fa fa-plus"></i></button>
          <button class="decrement-button"><i class="fa fa-minus"></i></button>
          <button class="delete-button"><i class="fa fa-trash"></i></button>
        </div>
      </div>
      <div class="cart-item">
        <img src="book2.jpg" alt="Book 2">
        <div class="cart-item-info">
          <h3>The Strange Case of Origami Yoda</h3>
          <p>By: Tom Angleberger</p>
        </div>
        <div class="cart-item-total">
          <p>Qty: 1</p>
          <p>Total: <strong>$19.99</strong></p>
        </div>
        <div class="cart-item-actions">
          <button class="increment-button"><i class="fa fa-plus"></i></button>
          <button class="decrement-button"><i class="fa fa-minus"></i></button>
          <button class="delete-button"><i class="fa fa-trash"></i></button>
        </div>
      </div> -->
      <div class="cart-total">
        <p>Subtotal: <strong>$${subtotal}</strong></p>
        <p>Tax: <strong>$${taxes}</strong></p>
        <p>Total: <strong>$${totalPrice}</strong></p>
      </div>
      <!-- change this to an href leading to the payment page -->
      <a href="/orderSummary" class="checkout-button">Proceed to Checkout</a>
    </div>
  </section>

  <footer>
    <div class="footer-content">
      <p>Created by Group 3</p>
    </div>
  </footer>
</body>

</html>