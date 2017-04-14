<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
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
<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.min.css'/>
<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.print.css' media='print'/>
</head>
<body>
<%
	String status = (String) session.getAttribute("status");
	String friend_status = (String) session.getAttribute("friend_status");
	AccountDetails ad = (AccountDetails) session.getAttribute("ad"); 
	List<AccountDetails> friends_details = (List<AccountDetails>) session.getAttribute("friends_details");
	if(status != null) {
		if (status.equals("Account_Added")){
			System.out.println("Account_Added");
			out.print("<script type=\"text/javascript\">alert('You have successfully joined us!');</script>");
			session.setAttribute("status", "Login_Success");
			System.out.println("Login_Success");
			
		}
		else if(status.equals("Login_Success")) {
			if(friend_status != null) {
				if(friend_status.equals("Positive")) {
					out.print("<script type=\"text/javascript\">alert('Friend added!');</script>");
					session.setAttribute("friend_status", "normal");
				}
				else if (friend_status.equals("Deleted")) {
					out.print("<script type=\"text/javascript\">alert('Friend deleted!');</script>");
					session.setAttribute("friend_status", "normal");
				}
			}
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
				<div class="list-group">
				<%if(friends_details.size() > 0) { %>
					<%for(int i=0; i<friends_details.size(); i++) { %>
						<form action="DeleteFriend" method="post">
							<a href="#" class="list-group-item"><%=friends_details.get(i).getName() %>
							<button class="btn btn-xs btn-danger" style="float:right;" type="submit" name="delete_user" value="<%=friends_details.get(i).getUsername() %>" >Delete</button>
							</a>
							
						</form>
					<%} %>
				<%} 
				else {%>
					<a href="#" class="list-group-item">No friends found. Click on the search bar to add a friend!</a>
				<%} %>
				</div>
			</div>
		  </div>
	  <%} %>
	  <p>This is your current schedule:</p>
	  <p>Feel free to make some changes and click the button below to save.</p>
	  <div id='calendar'></div>
	  
	  <p><a class="btn btn-primary btn-lg" onClick="sendJSON()" role="button">Save Changes</a></p>
	</div>


</div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src='//cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js'></script>
<script type="text/javascript" src='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.min.js'></script>
<script type="text/javascript" src='js/updateScheduler.js'></script>
</body>
</html>