<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<input%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <title>BookstoreHub - Book Details</title>
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

    .book-showcase {
      display: flex;
      overflow-x: auto;
      gap: 100px;
      flex-wrap: nowrap;
      justify-content: center;
    }

    .book-item {
      flex-grow: 1;
      margin-right: 10px;
      padding: 10px 20px;
      font-size: 18px;
      background-color: #ebcb9e;
      color: black;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      width: 50%;
      margin-bottom: 100px;
      font-family: "Oswald", sans-serif;
    }

    .button-container {
      display: flex;
      justify-content: center;
      margin-top: 10px;
    }

    .add-to-cart-button,
    .back-home-button {
      flex-grow: 1;
      margin-right: 10px;
      padding: 10px 20px;
      font-size: 14px;
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
        <li><a href="/logout" class="login-button"><i class="fa fa-user"></i>${logout}${login}</a></li>
        <li><a href="/viewcart" class="checkout-button"><i class="fa fa-shopping-cart"></i>Checkout</a></li>
      </ul>
    </nav>
  </header>

  <section>
    <div class="book-showcase">
      <div class="book-item">
        <img src="${bookCover}" alt="Book Image">
        <h2 class="book-title">${bookTitle}</h2>
        <p class="book-author">Author: ${bookAuthor}</p>
        <p class="book-category">Category: ${bookCategory}</p>
        <p class="book-edition">Edition: ${bookEdition}</p>
        <p class="book-publisher">Publisher: ${bookPublisher}</p>
        <p class="book-publication-year">Publication Year: ${bookPubYear}</p>
        <p class="book-isbn">ISBN: ${bookIsbn}</p>
        <p class="book-copies-in-stock">Copies in Stock: ${bookQuantity}</p>
        <p class="book-price">Price: $${bookSellingPrice}</p>
        <p class="book-price">Have a copy to sell?</p>
        <p>We will buy it from you for $${bookBuyingPrice}!</p>
        <div class="button-container">
          <form:form method="post" action="/addToCart">
            <input hidden="true" name="isbn" value="${bookIsbn}">
            <input class="add-to-cart-button" type="submit" value="Add to Cart">
          </form:form>
          <form:form method="get" action="/homeOBS">
            <input class="back-home-button" type="submit" value="Back to Home">
          </form:form>
        </div>
      </div>
    </div>
  </section>

  <footer>
    <div class="footer-content">
      <p>Created by Group 3</p>
    </div>
  </footer>
</body>

</html>
