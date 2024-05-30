<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <title>User Login</title>
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


    /* CSS styles for the login page */
    .login-container {
      background-color: #ffdab7;
      max-width: 600px;
      margin: 50px auto;
      padding: 30px;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      text-align: center;
      font-size: 16px;
      color: #4d4c4c;
    }

    .registration-form h2 {
      margin-bottom: 20px;
      font-size: 24px;
      color: #2b0d0a;
      ;
    }

    .registration-form input[type="text"],
    .registration-form input[type="password"] {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border-radius: 5px;
      border: 1px solid #2b0d0a;
    }

    .registration-form button {
      padding: 10px 20px;
      background-color: #570e06;
      border: none;
      color: white;
      border-radius: 5px;
      cursor: pointer;
      font-family: "Montserrat", sans-serif;
    }

    .login-container a {
      text-decoration: none;
      color: #78504b;
      font-weight: bold;
    }

    .login-container a:hover {
      text-decoration: underline;
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
        <li><a href="/viewcart" class="checkout-button"><i class="fa fa-shopping-cart"></i>Checkout</a></li>
      </ul>
    </nav>
  </header>

  <section>
    <div class="login-container">
      <h2>User Login</h2>
      <form:form class="registration-form" method="POST" modelAttribute="user">
        <span style="font-size: 12px; color: red">${emptyEmail}</span>
        <span style="font-size: 12px; color: red">${invalidEmail}</span>
        <span style="font-size: 12px; color: red">${emptyPassword}</span>
        <span style="font-size: 12px; color: red">${invalidPassword}</span>
        <form:input type="text" class="custom-control-input" name="email" id="email" path="email" placeholder="Email"/>
        <br>
        <form:input type="password" class="custom-control-input" name="hashedPassword" id="hashedPassword" path="hashedPassword" placeholder="Password"/>
        <br>
        <label>
          <input class="custom-control-input" type="checkbox"> Remember Me
        </label>
        <br>
        <form:button type="submit">Login</form:button>
      </form:form>
      <p>Forgot your password? <a href="/forgotPassword">Reset here</a></p>
      <p>Not a member? <a href="/userRegistration">Register</a></p>
      <p>Continue as guest? <a href="/homeOBS">Continue</a></p>
    </div>
  </section>
  <footer>
    <div class="footer-content">
        <p>Created by Group 3</p>
    </div>
</footer>
</body>


</html>