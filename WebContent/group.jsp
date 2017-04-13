<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="database.AccountDetails" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Groups</title>

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<%
	String status = (String) session.getAttribute("status");
	AccountDetails ad = (AccountDetails) session.getAttribute("ad");
	if(status != null) {
		if(status.equals("Login_Success")) {
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
		<form method="post">
			<input type="text" name="groupName" required></input>
			<button type="submit" class="btn btn-medium btn-primary">Create Group</button>
		</form>
		<h2>Your Groups:</h2>
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
			<div class="panel panel-primary">
				<div class="panel-heading" role="tab" id="heading">
					<h4 class="panel-title">
						<a class="collapsed" role="button" data-toggle="collapse" href="#collapse"  aria-expanded="false" aria-controls="collapse">
									Title Here
							</a>
					</h4>
				</div>
				<div id="collapse" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading">
					<div class="panel-body">
						Text Here
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>