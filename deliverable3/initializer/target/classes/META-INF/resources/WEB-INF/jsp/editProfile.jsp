<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <title>BookGarden - Edit Profile</title>
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

    .edit-profile {
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

    .edit-profile h2 {
      margin-bottom: 20px;
    }

    .edit-profile input[type="text"],
    .edit-profile input[type="email"],
    .edit-profile input[type="password"],
    .edit-profile select {
      width: 100%;
      padding: 10px;
      margin-bottom: 15px;
      box-sizing: border-box;
    }

    .edit-profile .button-container {
      display: flex;
      justify-content: center;
      flex-wrap: wrap;
      margin-top: 20px;
    }

    .edit-profile .button-container button {
      flex-grow: 1;
      margin-right: 10px;
      padding: 10px 20px;
      font-size: 14px;
      background-color: #570e06;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-family: "Montserrat", sans-serif;
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
        <li><a href="/logout" class="login-button"><i class="fa fa-user"></i>${login}${logout}</a></li>
        <li><a href="/viewcart" class="checkout-button"><i class="fa fa-shopping-cart"></i>Checkout</a></li>
      </ul>
    </nav>
  </header>

  <section>
    <div class="edit-profile">
      <form:form method="POST" modelAttribute="editUser">
        <h2>Edit Profile</h2>

        <h3>Change Password</h3>
        <span style="font-size: 12px; color: red">${emptyPassword}</span><span style="font-size: 12px; color: red">${passwordLength}</span>
        <form:input type="password" class="form-control" id="password" path="password" placeholder="Current/New Password*"/>
        <span style="font-size: 12px; color: red">${emptyConfirmPassword}</span><span style="font-size: 12px; color: red">${notMatchingPasswords}</span>
        <form:input type="password" class="form-control" id="confirmPassword" path="confirmPassword" placeholder="Confirm Password*"/>

        <h3>Personal Information</h3>
        <span style="font-size: 12px; color: red">${emptyFirstName}</span>
        <form:input type="text" class="form-control" id="firstName" path="firstName" placeholder="First Name" value="${user.getFirstName()}"/>
        <span style="font-size: 12px; color: red">${emptyLastName}</span>
        <form:input type="text" class="form-control" id="lastName" path="lastName" placeholder="Last Name" value="${user.getLastName()}"/>
        <h3 style="text-align: left;">${user.getEmail()}</h3>

        <h3>Payment Information</h3>
        <span style="font-size: 12px; color: red">${emptyCardType}</span>
        <form:select name="cardType" class="form-control" id="cardType" path="cardType">
          <form:option value="${editCardType}" item="${editCardType}" selected="selected"></form:option>
          <form:options items="${cardTypesList}"></form:options>
        </form:select>
        <span style="font-size: 12px; color: red">${emptyCardNumber}</span><span style="font-size: 12px; color: red">${shortCardNumber}</span>
        <form:input type="text" class="form-control" id="cardNumber" path="cardNumber" placeholder="Enter card number" maxlength="16" value="${rawCardNumber}"/>
        <span style="font-size: 12px; color: red">${emptyExpiration}</span>
        <form:input type="text" class="form-control" id="expiration" path="expiration" placeholder="MM/YY" value="${paymentInfo.getExpiration()}"/>
        <span style="font-size: 12px; color: red">${emptyCvv}</span>
        <form:input type="text" class="form-control" id="cvv" path="cvv" placeholder="CVV" maxlength="3"/>

        <h3>Address Information</h3>
        <span style="font-size: 12px; color: red">${emptyStreet}</span>
        <form:input type="text" class="form-control" id="street" path="street" placeholder="Address" value="${address.getStreet()}"/>
        <span style="font-size: 12px; color: red">${emptyCity}</span>
        <form:input type="text" class="form-control" id="city" path="city" placeholder="City" value="${address.getCity()}"/>
        <span style="font-size: 12px; color: red">${emptyState}</span>
        <form:select name="state" class="form-control" id="state" path="state">
							<form:option value="${address.getState()}" item="${address.getState()}" selected="selected"></form:option>
              <form:options items="${statesList}"></form:options>
				</form:select>
        <span style="font-size: 12px; color: red; text-align: center">${emptyZipcode}</span>
        <form:input type="text" class="form-control" id="zipcode" path="zipcode" placeholder="ZIP Code" maxlength="5" value="${address.getZipcode()}"/>

        <div class="promotions-checkbox">
          <form:checkbox id="subscribed" name="subscribed" class="custom-control-input" path="subscribed" checked="${subscribedStatus}" value="Y"/>
          <label class="custom-control-label" for="subscribed">Subscribe for promotions</label>
        </div>

        <div class="button-container">
          <form:button>Save Changes</form:button>
          <form:button>Cancel</form:button>
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