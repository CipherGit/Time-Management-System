<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database.AccountDetails" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile</title>

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<%
	String status = (String) session.getAttribute("status");
	AccountDetails ad = (AccountDetails) session.getAttribute("ad"); 
	if(status != null) {
		if (status.equals("Account_Added")){
			System.out.println("Account_Added");
			out.print("<script type=\"text/javascript\">alert('You have successfully joined us!');</script>");
			session.setAttribute("status", "Login_Success");
			System.out.println("Login_Success");
		}
		else if(status.equals("Login_Success")) {
			System.out.println("Login_Success");
		}
		else {
			response.sendRedirect("index.jsp");
		}
	}
	else {
		response.sendRedirect("index.jsp");
	}
	
%>

<div id="nav-here2"></div>
<div class="container">
	<div class="jumbotron">
	  
	  <% if(ad != null) { %>
		  <h1>Welcome <%=ad.getName() %>!</h1>
		  <div class="row">
			  
			<div class="col-md-6">
				<p>Your details are as follows:</p>	
				<div class="list-group">
					<a href="#" class="list-group-item">Username: <%=ad.getUsername() %></a>
				  	<a href="#" class="list-group-item">Password: <%=ad.getPassword() %></a>
				  	<a href="#" class="list-group-item">Email: <%=ad.getEmail() %></a>
				 </div>
			</div>
			<div class="col-md-6">
				<p>Friends:</p>
				
			</div>
		  </div>
	  <%} %>
	  <p>This is your current schedule:</p>
	  <!-- insert schedule of the user here -->
	  
	  <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
	</div>


</div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>