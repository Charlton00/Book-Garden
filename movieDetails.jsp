<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheets" href="css/stylesheet.css">
    <title>movieDetails
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
     body{
        background:linear-gradient(grey,orange);
        height:auto;
      }
      
      .login{
        border-radius:25px;
        background-color:lightgrey;
        width:50%;
        padding: 14px 20px;
        margin-left: auto;
        margin-right: auto;
        margin-top: 50px;
        text-align:center;
      }
      .title{
        font-size:50px;
        text-align: center;
        font-family:'Roboto', sans-serif;
      }
      .topnav {
  overflow: hidden;
  background-color: #333;
}

.topnav a {
  float: left;
  display: block;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}

.topnav a.active {
  background-color: orange;
  color: white;
}

.topnav .icon {
  display: none;
}

@media screen and (max-width: 600px) {
  .topnav a:not(:first-child) {display: none;}
  .topnav a.icon {
    float: right;
    display: block;
  }
}

@media screen and (max-width: 600px) {
  .topnav.responsive {position: relative;}
  .topnav.responsive .icon {
    position: absolute;
    right: 0;
    top: 0;
  }
  .topnav.responsive a {
    float: none;
    display: block;
    text-align: left;
  }
}

.flex-container {
  display:flex;
  background-color:orange;
  justify-content: center;
  margin-left: 200px;
  margin-right: 200px;
  border-radius: 25px; 
}

.movieDetTitle{
        border-radius:25px;
        background-color:orange;
        width:100%;
        padding: 14px 20px;
        margin-left: 25%;
        margin-right: 25%;
        margin-top: 15px;
        padding-bottom: 5px;
        font-size: 25px;

        text-align:center;
        float:left;
}
        .movieDetTitle .inputs span{font-size:24px;float: left;}
        .movieDetTitle .inputs input{height:40px;padding:0px 10px;font-size:17px;box-shadow:none;outline:none}
        .movieDetTitle .inputs input{float:center;}[type="text"][readonly]{border: 2px solid rgba(0,0,0,0)}
        
         
        
        
      


.flex-container > div {
  background: orange;
  margin:25px;
  padding:1px;
  font-size: 10px;
}
.flex-container .inputs2 span{font-size: 24px;float: left;font-weight: 700;}
.flex-container .inputs2 input{height:40px;padding:0px 10px;font-size:17px;box-shadow:none;outline:none}
.flex-container .inputs2 input{float:center;}[type="text"][readonly]{border: 2px solid rgba(0,0,0,0)}

a{
  color: black;
}

h5{
  text-align:center;
}


.footer{
    position:fixed;
    left:0;
    bottom:0;
    width:100%;
    text-align:center;
    background-color:#333;
    color: white;  
}
          
.seatingChart {
  align-items:center;

}

.help-tip{
    position: static;
    text-align: center;
    background-color: #BCDBEA;
    border-radius: 50%;
    width: 24px;
    height: 24px;
    font-size: 14px;
    line-height: 26px;
    cursor: default;
}

.help-tip:before{
    content:'?';
    font-weight: bold;
    color:#fff;
}

.help-tip:hover p{
    display:block;
    transform-origin: 100% 0%;

    -webkit-animation: fadeIn 0.3s ease-in-out;
    animation: fadeIn 0.3s ease-in-out;

}

.help-tip p{    /* The tooltip */
    display: none;
    text-align: center;
    background-color: #1E2021;
    padding: 20px;
    width: 300px;
    position: absolute;
    border-radius: 3px;
    box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
    right: -4px;
    color: #FFF;
    font-size: 13px;
    line-height: 1.4;
}

.help-tip p:before{ /* The pointer of the tooltip */
    float: right;
    content: '';
    width:0;
    height: 0;
    border:6px solid transparent;
    border-bottom-color:#1E2021;
    right:10px;
    top:-12px;
}

.help-tip p:after{ /* Prevents the tooltip from being hidden */
    width:100%;
    height:40px;
    content:'';
    position: absolute;
    top:-40px;
    left:0;
}

/* CSS animation */
@-webkit-keyframes fadeIn {
    0% { 
        opacity:0; 
        transform: scale(0.6);
    }

    100% {
        opacity:100%;
        transform: scale(1);
    }
}

@keyframes fadeIn {
    0% { opacity:0; }
    100% { opacity:100%; }
}


    </style>
  </head>

<body>

  
  <div class="topnav" id="myTopnav">
    <a href="homePage" class="active">Home</a>
    <a href="promotionHome">Promotions</a>
    <a href="userLogin">Login</a>
    <a href="viewcart">View Cart</a>
    <a href="javascript:void(0);" class="icon" onclick="myFunction()">
      <i class="fa fa-bars"></i>
    </a>
  </div>



  <div class="title">
    <br>
    ${title} 
    <br>
    
    <h3>
       More Information & Details Below:
    <br> <br>
    <a href="${trailer}" target="_blank">Movie Trailer</a><br> 
      <br>
    </h3>
</div>

<div class="flex-container">
  <div class="movieDetTitle">
    <div><img src="${image}" alt="image not found"
  width="200" height="300"></div>
  <br>
  <br> <br>
 </div>
</h5>
  
    <form:form method="POST">

<br>

<div class="inputs"><div class="inputs2"> 
  <span>Cast:</span></div> <br> <br>
  ${cast} </div> <br><br>
  
<div class="inputs"><div class="inputs2">
  <span>Director:</span></div><br> <br> 
  ${director} </div> <br><br>
  
<div class="inputs"><div class="inputs2"> 
  <span>Producer:</span></div> <br><br>
  ${producer} </div> <br><br>

<div class="inputs"> <div class="inputs2">
      <span>Rotten Tomato Review(%):</span></div><br> <br>
      ${reviews}%</div> <br><br>
    
<div class="inputs"> <div class="inputs2">
        <span>MPAA-US code:</span></div><br> <br>
        ${mpaa} </div> <br><br>

<div class="inputs"> <div class="inputs2">
    <span>Synopsis:</span></div> <br><br>
    ${synopsis} </div> <br><br>

    <div class="inputs"><div class="inputs2">
      <span>Showtimes:</span></div> <br><br> 
    <a href="orderTickets?show=${show1}" target="_self">${showtime1}</a> <br> <br>
    <a href="orderTickets?show=${show2}" target="_self">${showtime2}</a> <br> <br>
    <a href="orderTickets?show=${show3}" target="_self">${showtime3}</a> <br> <br>
    <a href="orderTickets?show=${show4}" target="_self">${showtime4}</a> <br> <br>

   

  </div> <br><br>
    
        
          </a> 
        </div>



  <br><br>
  <a href="homePage"><button" class="btn btn-secondary btn-block mb-4">Back to Home</button></a>    
  
  


<br> <br> <br> <br>




<div class="footer">
  <br>
  <small>Copyright - MovieHub</small>
  <br><br>

</div>


</body>
<script>
  function myFunction() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
      x.className += " responsive";
    } else {
      x.className = "topnav";
    }
  }
  </script>
</html>