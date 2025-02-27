<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <title>BookGarden - Order Summary</title>
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

    .logo {
      display: flex;
      align-items: center;
      margin-right: 0px; /*20*/
    }

    .logo img {
      height: 150px;
      margin-right: 10px;
    }

    header nav ul li.search-bar {
      display: flex;
      align-items: center;
      margin-right: 10px;
      flex-grow: 1;
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
      margin-left: 0px;
    }

    section {
      padding: 20px;
      text-align: center;
      margin-top: 120;
    }

    .order-summary {
      background-color: #ffdab7;
      max-width: 400px;
      margin: 50px auto;
      padding: 30px;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      text-align: center;
      font-size: 16px;
      color: #4d4c4c;
    }

    .payment-info {
      margin-bottom: 20px;
    }

    .payment-info p {
      margin: 5px 0;
    }

    .shipping-address {
      margin-bottom: 20px;
    }

    .shipping-address p {
      margin: 5px 0;
    }

    .button-container {
      display: flex;
      justify-content: center;
      flex-wrap: wrap;
    }

    .button-container button {
      padding: 10px 20px;
      background-color: #78504b;
      border: none;
      color: white;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
      margin: 10px;
      width: 200px;
      max-width: 100%;
      box-sizing: border-box;
      font-family: "Oswald", sans-serif;
    }

    .payment-info,
    .shipping-address,
    .billing-address {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
    }

    .payment-info h4,
    .shipping-address h4,
    .billing-address h4 {
      margin: 5px 0;
      width: 150px;
      text-align: left;
    }

    .payment-fields,
    .shipping-fields,
    .billing-fields {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      margin-top: 10px;
    }

    .payment-fields input,
    .shipping-fields input,
    .billing-fields input {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border-radius: 5px;
      border: 1px solid #2b0d0a;
    }


    .pay-button {
      text-decoration: none;
      font-size: 20px;
      color: white;
      padding: 5px 10px;
      border: 1px solid #2b0d0a;
      border-radius: 5px;
      background-color: #570e06;
      font-family: "Oswald", sans-serif;
    }

    .promotion-container {
      margin-bottom: 20px;
    }

    .promotion-container input[type="text"] {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border-radius: 5px;
      border: 1px solid #2b0d0a;
    }

    .address-select {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border-radius: 5px;
      border: 1px solid #2b0d0a;
    }

    .shipping-to-another-address-fields {
      display: none;
    }

    #shipToAnotherAddress:checked~.billing-fields .shipping-to-another-address-fields {
      display: block;
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
        <li><a href="/editProfile" class="edit-button"><i class="fa fa-user-cog"></i>View Profile</a></li>
        <li><a href="/logout" class="login-button"><i class="fa fa-user"></i>${logout}${login}</a></li>
        <li><a href="/viewCart" class="checkout-button"><i class="fa fa-shopping-cart"></i>View Cart</a></li>
      </ul>
    </nav>
  </header>
  <section>
    <div class="order-summary">
      <h2>Order Summary</h2>

      <!-- ... (previous code) ... -->


      <div class="billing-address">
        <h3>Billing Address</h3>
        <form:form method="post" modelAttribute="createOrder">
              <form:select class="address-select" path="billingAddress">
                <form:option value="" disabled="disabled" selected="selected">Select a saved billing address</form:option>
                <form:options items="${billingList}"></form:options>
                <!-- Add more saved billing addresses here -->
              </form:select>

              <div class="billing-fields">
                <!-- Billing Address Input Fields -->
                <h4>Name</h4>
                <form:input type="text" path="firstName" placeholder="First Name"/>
                <form:input type="text" path="lastName" placeholder="Last Name"/>
                <h4>Billing Street Address</h4>
                <form:input type="text" path="inputBillingAddress" placeholder="Enter Billing Street Address"/>

                <h4>Billing City</h4>
                <form:input type="text" path="billingCity" placeholder="Enter Billing City"/>

                <h4>Billing State</h4>
                <form:input type="text" path="billingState" placeholder="Enter Billing State"/>

                <h4>Billing ZIP Code</h4>
                <form:input type="text" path="billingZip" placeholder="Enter Billing ZIP Code"/>

                <!-- Checkbox for shipping option -->
                <div class="shipping-option">
                  <label>
                    <span>Ship to this address</span>
                    <form:checkbox id="shipToThisAddress" path="checked" checked="${checked ? 'checked' : ''}" value="true"/>
                  </label>

                </div>
              </div>
            </div>
            <div class="shipping-address">
              <h3>Shipping Address</h3>
              <form:select class="address-select" path="shippingAddress">
                <form:option value="" disabled="disabled" selected="selected">Select a saved address</form:option>
                <form:options items="${shippingList}"></form:options>
                <!-- Add more saved addresses here -->
              </form:select>
              <div class="shipping-fields">
                <!-- Shipping Address Input Fields -->
                <h4>Street Address</h4>
                <form:input type="text" path="inputShippingAddress" placeholder="Enter Street Address" />

                <h4>City</h4>
                <form:input type="text" path="shippingCity" placeholder="Enter City" />

                <h4>State</h4>
                <form:input type="text" path="shippingState" placeholder="Enter State" />

                <h4>ZIP Code</h4>
                <form:input type="text" path="shippingZip" placeholder="Enter ZIP Code" />
              </div>
            </div>
            <div class="payment-info">
              <h3>Payment Information</h3>
              <form:select class="address-select" path="card">
                <form:option value="" disabled="disabled" selected="selected">Select a saved payment method</form:option>
                <form:options items="${paymentList}"></form:options>
                <!-- Add more saved payment methods here -->
              </form:select>
              <div class="payment-fields">
                <!-- Payment Input Fields -->
                <h4>Card Number</h4>
                <form:input type="text" path="cardNumber" placeholder="Enter Card Number" />

                <h4>Cardholder Name</h4>
                <form:input type="text" path="cardName" placeholder="Enter Cardholder Name" />

                <h4>Expiration Date</h4>
                <form:input type="text" path="expirationDate" placeholder="Enter Expiration Date (MM/YY)" />

                <h4>CVV</h4>
                <input type="text" placeholder="Enter CVV" />
              </div>
            </div>

            <p>Total Price: $${subtotal}</p>
            <p>Tax: $${taxes}</p>


            <p>Grand Total: $${totalPrice}</p>

            <div class="button-container">
              <form:button type="submit" class="pay-button">Checkout</form:button>
            </div>
        </form:form>
    </div>
  </section>

  <footer>
    <div class="footer-content">
      <p>Created by Group 3</p>
    </div>
  </footer>
</body>

</html>