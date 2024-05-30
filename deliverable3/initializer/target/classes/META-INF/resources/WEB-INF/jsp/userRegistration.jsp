<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <title>BookstoreHub - Registration</title>
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

    .login-container {
      background-color: #ffdab7;
      max-width: 400px;
      margin: 50px auto;
      padding: 30px;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      font-size: 16px;
      color: #4d4c4c;
    }

    .registration-form input[type="text"],
    .registration-form input[type="email"],
    .registration-form input[type="tel"],
    .registration-form input[type="password"],
    .registration-form textarea,
    .registration-form select {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border-radius: 5px;
      border: 1px solid #ccc;
      box-sizing: border-box;
    }

    .registration-form input[type="checkbox"] {
      margin-right: 5px;
    }

    .registration-form button {
      padding: 10px 20px;
      background-color: #570e06;
      border: none;
      color: white;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
    }

    .registration-form button:hover {
      background-color: #441106;
    }

    .section-title {
      text-align: left;
      margin-bottom: 10px;
      margin-top: 20px;
    }

    .address-info {
      margin-top: 20px;
      text-align: center;
    }

    .address-info label {
      display: block;
      margin-bottom: 5px;
    }

    .address-info input[type="text"],
    .address-info input[type="number"],
    .address-info select {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border-radius: 5px;
      border: 1px solid #ccc;
      box-sizing: border-box;
    }

    footer {
      background-color: #996a44;
      padding: 20px;
      text-align: center;
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
        <li><a href="/userLogin" class="login-button"><i class="fa fa-user"></i>Login</a></li>
        <li><a href="/viewcart" class="checkout-button"><i class="fa fa-shopping-cart"></i>Checkout</a></li>
      </ul>
    </nav>
  </header>

  <section>
    <div class="login-container">
      <h2>Create an Account</h2>
      <form:form class="registration-form" method="POST" modelAttribute="newUser">
        <div class="personal-info">
          <h3 class="section-title">Personal Information</h3>
          <span style="font-size: 12px; color: red">${emptyFirstName}</span><form:input type="text" class="form-control" id="firstName" path="firstName" placeholder="First Name*"/>
          <span style="font-size: 12px; color: red">${emptyLastName}</span><form:input type="text" class="form-control" id="lastName" path="lastName" placeholder="Last Name*"/>
          <span style="font-size: 12px; color: red">${emptyEmail}</span><span style="font-size: 12px; color: red">${existingEmail}</span><form:input type="email" class="form-control" id="email" path="email" placeholder="Email*"/>
          <span style="font-size: 12px; color: red">${emptyPassword}</span><span style="font-size: 12px; color: red">${passwordLength}</span><form:input type="password" class="form-control" id="password" path="password" placeholder="Password (at least 8 characters long)*"/>
          <span style="font-size: 12px; color: red">${emptyConfirmPassword}</span><span style="font-size: 12px; color: red">${notMatchingPasswords}</span><form:input type="password" class="form-control" id="confirmPassword" path="confirmPassword" placeholder="Confirm Password*"/>
        </div>
        <div class="address-info">
          <h3 class="section-title">Address Information (optional)</h3>
          <span style="font-size: 12px; color: red">${emptyStreet}</span><form:input type="text" class="form-control" id="street" path="street" placeholder="Address"/>
          <span style="font-size: 12px; color: red">${emptyCity}</span><form:input type="text" class="form-control" id="city" path="city" placeholder="City"/>
          <span style="font-size: 12px; color: red">${emptyState}</span>
          <form:select name="state" class="form-control" id="state" path="state">
							<option value="" selected="selected">Select a State</option>
              <form:options items="${statesList}"></form:options>
						</form:select>
            <span style="font-size: 12px; color: red; text-align: center">${emptyZipcode}</span><form:input type="text" class="form-control" id="zipcode" path="zipcode" placeholder="ZIP Code" maxlength="5"/>
        </div>
        <div class="payment-info">
          <h3 class="section-title">Payment Information (optional)</h3>
          <label>
            Card Number:
            <br><span style="font-size: 12px; color: red">${emptyCardNumber}</span><span style="font-size: 12px; color: red">${shortCardNumber}</span><form:input type="text" class="form-control" id="cardNumber" path="cardNumber" placeholder="Enter card number" maxlength="16"/>
          </label>
          <label>
            Expiry Date:
            <br><span style="font-size: 12px; color: red">${emptyExpiration}</span><form:input type="text" class="form-control" id="expiration" path="expiration" placeholder="MM/YY"/>
          </label>
          <label>
            CVV:
            <br><span style="font-size: 12px; color: red">${emptyCvv}</span><form:input type="text" class="form-control" id="cvv" path="cvv" placeholder="CVV" maxlength="3"/>
          </label>
          <label>
            Card Type:
            <br><span style="font-size: 12px; color: red">${emptyCardType}</span>
            <form:select name="cardType" class="form-control" id="cardType" path="cardType">
              <option value="" selected="selected">Select Card Type</option>
              <form:options items="${cardTypesList}"></form:options>
            </form:select>
          </label>
        </div>
        <div class="registration-agreement">
          <form:checkbox id="subscribed" name="subscribed" class="custom-control-input" path="subscribed" checked="" value="Y"/>
          <label class="custom-control-label" for="subscribed">Register for promotions</label>
        </div>
        <form:button type="submit" class="btn btn-primary">Create Account</form:button>
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