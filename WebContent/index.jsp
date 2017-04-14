<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MeetU | Time Management System</title>

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<%
	String status = (String) session.getAttribute("status");
	if(status != null) {
		if (status.equals("Account_Exist")){
			System.out.println("Account_Exist");
			out.print("<script type=\"text/javascript\">alert('Sorry, the username or email has already been taken.');</script>");
			session.setAttribute("status", "normal");
			System.out.println("normal");
		}
		else if(status.equals("Login_Failed")) {
			System.out.println("Login_Failed");
			out.print("<script type=\"text/javascript\">alert('Sorry, the username or password is incorrect.');</script>");
			session.setAttribute("status", "normal");
			System.out.println("normal");
		}
		else if(status.equals("Login_Success")) {
			response.sendRedirect("profile.jsp");
			System.out.println("Login_Success");
		}
		else if(status.equals("normal")) {
			System.out.println("normal");
		}
	}
	else {
		System.out.println("null");
	}
%>

<div id="nav-here"></div>

<div class="container" id="signin-form">
	<form class="form-signin" action="SigninServlet" method="post">
		<h3 class="form-signin-heading">sign in</h3>
		<input type="text" name="inputUsername" class="form-control" placeholder="Username" required autofocus id="inputError">
		<input type="password" name="inputPassword" class="form-control" placeholder="Password" required>
		<a href="#" class="pull-right" style="margin-bottom:10px;" onclick="changeForm()">New user? Join us!</a>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	</form>
</div>

<div class="container set-invisible" id="signup-form">
	<form class="form-signup" action="SignupServlet" method="post" onsubmit="return checkPassword(this);">
		<h3 class="form-signup-heading">sign up</h3>
		<input type="text" name="inputUsername" class="form-control" placeholder="Username" required autofocus>
		<div id="passwordClass">
		<input type="password" name="inputPassword" class="form-control" placeholder="Password" required>
		<input type="password" name="inputPassword2" class="form-control" placeholder="Re-type Password" required>
		</div>
		<input type="text" name="inputName" class="form-control" placeholder="Full name" required>
		<input type="email" name="inputEmail" class="form-control" placeholder="Email" required>
		<button class="btn btn-lg btn-primary" type="submit">Sign up</button>
		<a href="#" onclick="returnForm()">Cancel</a>
	</form>
</div>


<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/myjquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>