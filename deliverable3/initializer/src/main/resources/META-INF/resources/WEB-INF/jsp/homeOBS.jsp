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
  <title>BookstoreHub</title>
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
      /* new */
      flex-grow: 1;
    }

    header nav ul li.search-bar select {
      padding: 7px;
      margin-right: 0;
      /*left: 100px;*/
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
      /*margin-left: -5px;*/
      margin-left: 0;
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

    section {
      padding: 20px;
      text-align: center;
    }

    .book-showcase {
      display: flex;
      overflow-x: auto;
      gap: 10px;
      flex-wrap: nowrap;
      justify-content: center;
    }

    .book-item {
      flex-grow: 1;
      margin-right: 10px;
      padding: 10px 20px;
      font-size: 12px;
      background-color: #ebcb9e;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      width: 50%;
      margin-bottom: 10px;
      font-family: "Oswald", sans-serif;
    }

    .book-actions {
      display: flex;
      justify-content: center;
      margin-top: 10px;
    }

    .add-to-cart-button,
    .book-details-button {
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

    .book-item img {
      width: 60%;
      height: 500px;
      object-fit: contain;
      margin-bottom: 10px;
    }

    .book-item h3 {
      font-weight: bolder;
      font-size: 30px;
      margin-bottom: 5px;
      color: black;
      font-family: "Montserrat", sans-serif;
    }

    .book-item p {
      font-size: 14px;
      color: #4d4c4c;
      margin-bottom: 10px;
      font-weight: 300;
    }

    footer {
      background-color: #996a44;
      padding: 5px;
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
        <li><a href="/logout" class="login-button"><i class="fa fa-user"></i>${logout}${login}</a></li>
        <li><a href="/viewcart" class="checkout-button"><i class="fa fa-shopping-cart"></i>Checkout</a></li>
      </ul>
    </nav>
  </header>

  <section>
    <div class="book-showcase">
      <!-- Book items go here -->
    </div>
  </section>
  <section id="featured-books">
    <h2>Featured Books</h2>
    <!-- Add Featured-books content here -->
    <div class="book-showcase">
      <div class="book-item">
        <img src="${book1.cover}" alt="Book 1">
        <h3>${book1.title}</h3>
        <p>${book1.authors}</p>
        <p class="price">$${book1.sellingPrice}</p>
        <div class="book-actions">
          <div>
            <form:form method="post" action="/addToCart">
              <input hidden="true" name="isbn" value="${book1.isbn}">
              <input class="add-to-cart-button" type="submit" value="Add to Cart">
            </form:form>
          </div>
          <div>
            <form:form method="post" action="/viewBookDetail">
              <input hidden="true" name="isbn" value="${book1.isbn}">
              <input class="book-details-button" type="submit" value="Book Details">
            </form:form>
          </div>
        </div>
      </div>
      <div class="book-item">
        <img src="${book2.cover}" alt="Book 2">
        <h3>${book2.title}</h3>
        <p>${book2.authors}</p>
        <p class="price">$${book2.sellingPrice}</p>
        <div class="book-actions">
          <div>
            <form:form method="post" action="/addToCart">
              <input hidden="true" name="isbn" value="${book2.isbn}">
              <input class="add-to-cart-button" type="submit" value="Add to Cart">
            </form:form>
          </div>
          <div>
            <form:form method="post" action="/viewBookDetail">
              <input hidden="true" name="isbn" value="${book2.isbn}">
              <input class="book-details-button" type="submit" value="Book Details">
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </section>
  <section id="top-sellers">
    <h2>Top Sellers</h2>
    <!-- Add top sellers section content here -->
    <div class="book-showcase">
      <div class="book-item">
        <img src="${book3.cover}" alt="Book 3">
        <h3>${book3.title}</h3>
        <p>${book3.authors}</p>
        <p class="price">$${book3.sellingPrice}</p>
        <div class="book-actions">
          <div>
            <form:form method="post" action="/addToCart">
              <input hidden="true" name="isbn" value="${book3.isbn}">
              <input class="add-to-cart-button" type="submit" value="Add to Cart">
            </form:form>
          </div>
          <div>
            <form:form method="post" action="/viewBookDetail">
              <input hidden="true" name="isbn" value="${book3.isbn}">
              <input class="book-details-button" type="submit" value="Book Details">
            </form:form>
          </div>
        </div>
      </div>
      <div class="book-item">
        <img src="${book4.cover}" alt="Book 4">
        <h3>${book4.title}</h3>
        <p>${book4.authors}</p>
        <p class="price">$${book4.sellingPrice}</p>
        <div class="book-actions">
          <div>
            <form:form method="post" action="/addToCart">
              <input hidden="true" name="isbn" value="${book4.isbn}">
              <input class="add-to-cart-button" type="submit" value="Add to Cart">
            </form:form>
          </div>
          <div>
            <form:form method="post" action="/viewBookDetail">
              <input hidden="true" name="isbn" value="${book4.isbn}">
              <input class="book-details-button" type="submit" value="Book Details">
            </form:form>
          </div>
        </div>
      </div>
      <div class="book-item">
        <img src="${book5.cover}" alt="Book 5">
        <h3>${book5.title}</h3>
        <p>${book5.authors}</p>
        <p class="price">$${book5.sellingPrice}</p>
        <div class="book-actions">
          <div>
            <form:form method="post" action="/addToCart">
              <input hidden="true" name="isbn" value="${book5.isbn}">
              <input class="add-to-cart-button" type="submit" value="Add to Cart">
            </form:form>
          </div>
          <div>
            <form:form method="post" action="/viewBookDetail">
              <input hidden="true" name="isbn" value="${book5.isbn}">
              <input class="book-details-button" type="submit" value="Book Details">
            </form:form>
          </div>
        </div>
      </div>
      <div class="book-item">
        <img src="${book6.cover}" alt="Book 6">
        <h3>${book6.title}</h3>
        <p>${book6.authors}</p>
        <p class="price">$${book6.sellingPrice}</p>
        <div class="book-actions">
          <div>
            <form:form method="post" action="/addToCart">
              <input hidden="true" name="isbn" value="${book6.isbn}">
              <input class="add-to-cart-button" type="submit" value="Add to Cart">
            </form:form>
          </div>
          <div>
            <form:form method="post" action="/viewBookDetail">
              <input hidden="true" name="isbn" value="${book6.isbn}">
              <input class="book-details-button" type="submit" value="Book Details">
            </form:form>
          </div>
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