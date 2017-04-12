<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Set Schedule</title>

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<%
	String status = (String) session.getAttribute("status");
	if(status != null) {
		if (status.equals("Account_Failed")){     
			out.print("<script type=\"text/javascript\">alert('Sorry, there was an error in adding your account.');</script>");
			System.out.println("Account_Failed");
			session.setAttribute("status", "normal");
			System.out.println("normal");
			
		}
		else if(status.equals("Login_Success")) {
			response.sendRedirect("profile.jsp");
			System.out.println("Login_Success");
		}
		else if(status.equals("Account_Registration")) {
			System.out.println("Account_Registration");
		}
		else {
			response.sendRedirect("index.jsp");
		}
	}
	else
	{
		response.sendRedirect("index.jsp");
	}
%>

<div id="nav-here"></div>
<div class="container">
	<h2 style="text-align:center;">Set Your Schedule:</h2>
	<!-- insert code here -->

	<form class="form-schedule" action="NewUser" method="post">
		<button class="btn btn-lg btn-primary btn-block" type="submit">Finish</button>
	</form>

</div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>