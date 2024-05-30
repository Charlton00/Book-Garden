<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <title>BookstoreHub - Admin Home</title>
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
      font-size: 13px;
      color: white;
      padding: 5px 15px;
      border: 1px solid #2b0d0a;
      border-radius: 5px;
      background-color: #78504b;
    }

    /* CSS styles for the admin home page */
    .admin-home-container {
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

    .admin-home-container h2 {
      margin-bottom: 20px;
      font-size: 24px;
      color: #2b0d0a;
    }

    .admin-home-container a {
      display: block;
      padding: 10px;
      margin-bottom: 10px;
      background-color: #570e06;
      color: white;
      text-decoration: none;
      border-radius: 5px;
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
      <a href="/adminHome"><img src="../../images/Logo_For_Bookstore_hub.jpg" alt="Logo"></a>
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
        <li><a href="/logout" class="login-button"><i class="fa fa-user"></i>${logout}</a></li>
      </ul>
    </nav>
  </header>

  <section>
    <div class="admin-home-container">
      <h2>Admin Home</h2>
      <a href="/addBook">Add Books</a>
      <a href="#">Manage Users</a>
      <a href="/addPromotion">Add New Promotions</a>
    </div>
  </section>

  <footer>
    <div class="footer-content">
      <p>Created by Group 3</p>
    </div>
  </footer>
</body>

</html>
