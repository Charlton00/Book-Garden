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
  <title>BookstoreHub - Search Results</title>
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


    /* CSS styles for the search results page */
    .search-results-container {
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

    .search-results-container h2 {
      margin-bottom: 20px;
      font-size: 24px;
      color: #2b0d0a;
    }

    .book-item {
      display: flex;
      align-items: center;
      padding: 10px;
      margin-bottom: 10px;
      background-color: #ebcb9e;
      color: black;
      border: none;
      border-radius: 5px;
      font-family: "Oswald", sans-serif;
      text-align: left;
    }

    .book-item-image {
      flex-shrink: 0;
      width: 120px;
      height: 160px;
      margin-right: 10px;
    }

    .book-item-details {
      flex-grow: 1;
    }

    .book-item-details h3 {
      margin-top: 0;
      margin-bottom: 5px;
    }

    .book-item-details p {
      margin: 5px 0;
    }

    .book-item-details .price {
      font-weight: bold;
    }

    .view-details-button {
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
      <img src="../../images/Logo_For_Bookstore_hub.jpg" alt="Logo">
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
        <li><a href="/viewCart" class="checkout-button"><i class="fa fa-shopping-cart"></i>Checkout</a></li>
      </ul>
    </nav>
  </header>

  <section>
    <div class="search-results-container">
      <h2>Search Results</h2>
      <c:forEach items="${searchResults}" var="book">
        <div class="book-item">
          <img src="${book.getCover()}" alt="Book Image" class="book-item-image">
          <div class="book-item-details">
            <h3>${book.getTitle()}</h3>
            <p>Author: ${book.getAuthors()}</p>
            <p>Category: ${book.getCategory()}</p>
            <p class="price">Price: ${book.getSellingPrice()}</p>
            <form:form method="post" action="/viewBookDetail">
              <input hidden="true" name="isbn" value="${book.getIsbn()}">
              <button type="submit" class="view-details-button">View Details</button>
            </form:form>
            <form:form method="post" action="/addToCart">
              <input hidden="true" name="isbn" value="${book.getIsbn()}">
              <button type="submit" class="view-details-button">Add to Cart</button>
            </form:form>
          </div>
        </div>
      </c:forEach>
      
      <!--
      <div class="book-item">
        <img src="book2.jpg" alt="Book Image" class="book-item-image">
        <div class="book-item-details">
          <h3>Book Title</h3>
          <p>Author: Author Name</p>
          <p>Category: Category Name</p>
          <p class="price">Price: $14.99</p>
          <button class="view-details-button">View Details</button>
          <button class="view-details-button">Add to Cart</button>
        </div>
      </div>
      -->
      <!-- More book items... -->
    </div>
  </section>

  <footer>
    <div class="footer-content">
      <p>Created by Group 3</p>
    </div>
  </footer>
</body>

</html>
