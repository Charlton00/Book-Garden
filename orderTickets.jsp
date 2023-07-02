<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheets" href="css/stylesheet.css">
    <title>orderTickets</title>
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
        width:auto;
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
  justify-content: center;
  margin-left: auto;
  margin-right: auto;
  border-radius: 25px; 
}
.flex-container > div {
  background: orange;
  margin:25px;
  padding:1px;
  font-size: 10px;
}
a{
  color: black;
}
h5{
  text-align:center;
}
.buttonCentering {
            margin-top:45px;
            align-content:center;
            margin-left: 30%;
            margin-right: 30%;
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

.seatChartImg{
  background-image: url("https://i.imgur.com/UyM2oFk.png");
  background-position: center;
  margin-left: 200px;
  margin-right:200px;
  overflow:hidden;
  background-repeat: no-repeat;
}
.seatChart{
  padding-left: 40px;
  padding-right: 40px;
 
}

.seatChart p {
  padding-left: 47px;
  font-size:15px;
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
    <h3><a href="https://www.youtube.com/watch?v=qtRKdVHc-cE" target="_blank">>Movie Trailer</a>
    </h3>
    <br>
</div>

<div class="flex-container">
<div><img src="${img}" alt="image not found"
  width="200" height="300"></div>
    
</h5>

</div>

<div class="buttonCentering">
  <a href="movieDetails"><button" class="btn btn-secondary btn-block mb-4">Additional Movie Details Here</button></a> 
  </div>
<div class="login">
      <form>
          <div class="help-tip">
            <p>Select your seat number for your movie selction</p>
            </div>          
            <form:form method="POST" modelAttribute="seatSelect">
            <label class="mr-sm-2" for="ticketTime">Number of Child Tickets</label>
            <form:select  path="child" id = "child">
              <form:option value="0"  label="0" />
              <form:option value="1"  label="1" />
              <form:option value="2"  label="2" />
              <form:option value="3"  label="3" />
              <form:option value="4"  label="4" />
              <form:option value="5"  label="5" />
              <form:option value="6"  label="6" />
              <form:option value="7"  label="7" />
              <form:option value="8"  label="8" />
              <form:option value="9"  label="9" />
              <form:option value="10" label="10"/>  
            </form:select>
           <br>
              <label class="mr-sm-2" for="ticketTime">Number of Adult Tickets</label>
              <form:select  path="adult">
                <form:option value="0"  label="0" />
                <form:option value="1"  label="1" />
                <form:option value="2"  label="2" />
                <form:option value="3"  label="3" />
                <form:option value="4"  label="4" />
                <form:option value="5"  label="5" />
                <form:option value="6"  label="6" />
                <form:option value="7"  label="7" />
                <form:option value="8"  label="8" />
                <form:option value="9"  label="9" />
                <form:option value="10" label="10"/>  
              </form:select>
             <br>
              <label class="mr-sm-2" for="ticketTime">Number of Senior Tickets</label>
              <form:select  path="senior">
                <form:option value="0"  label="0" />
                <form:option value="1"  label="1" />
                <form:option value="2"  label="2" />
                <form:option value="3"  label="3" />
                <form:option value="4"  label="4" />
                <form:option value="5"  label="5" />
                <form:option value="6"  label="6" />
                <form:option value="7"  label="7" />
                <form:option value="8"  label="8" />
                <form:option value="9"  label="9" />
                <form:option value="10" label="10"/>  
              </form:select>
              <div class="seatChartImg">
              <div class="seatChart">
                A B C D E
              <br><td><form:checkboxes items = "${row1}" path = "row1" /></td>
              <br><td><form:checkboxes items = "${row2}" path = "row2" /></td>
              <br><td><form:checkboxes items = "${row3}" path = "row3" /></td>
              <br><td><form:checkboxes items = "${row4}" path = "row4" /></td>
              </div>
            </div>  
            <br>    
            </div>
            <div class="buttonCentering">
            <a href="viewcart">
              <button" class="btn btn-secondary btn-block mb-4">Submit</button>
          </a>
          </div>
           
      </form:form> 


      </div>
      

      </form>
            </div>

    </div>
    
    <div class="buttonCentering">
  <a href="homePage">
    <button" class="btn btn-secondary btn-block mb-4">Back to Home</button>
</a>
  </div>
</div>
<br>
<br>
<br>
    <div class="footer">
      <br>
      <small>Copyright - Company Name</small>
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