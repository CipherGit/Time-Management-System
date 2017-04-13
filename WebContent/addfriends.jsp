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
<title>Add Friends</title>

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<%
	String status = (String) session.getAttribute("status");
	String search_status = (String) session.getAttribute("search_status");
	AccountDetails ad = (AccountDetails) session.getAttribute("ad"); 
	List<AccountDetails> search_results = (List<AccountDetails>) session.getAttribute("search_results");
	if(status != null) {
		if(status.equals("Login_Success")) {
			System.out.println("Login_Success");
			if(search_status.equals("Positive")) {
				System.out.println(""+search_results.size()+" match found");
			}
			else {
				System.out.println("No such user");
			}
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
	<div class="friends-container">
	<div class="jumbotron">
	<h3>Result:</h3>
	<%if(ad != null) { %>
	<%if(search_status.equals("Positive")) { %>
		<%for (int i=0; i<search_results.size(); i++) { %>
			<%if(!search_results.get(i).getUsername().equals(ad.getUsername())) { %>
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
				<div class="panel panel-primary">
					<div class="panel-heading" role="tab" id="heading<%=i%>">
						<h4 class="panel-title">
							
							<a class="collapsed" role="button" data-toggle="collapse" href="#collapse<%=i%>"  aria-expanded="false" aria-controls="collapse<%=i%>">
									<%=search_results.get(i).getName()%>
							</a>
							
						</h4>
					</div>
					<div id="collapse<%=i%>" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading<%=i%>">
						<div class="panel-body">
							<ul>
								<li>Username: <%=search_results.get(i).getUsername() %></li>
								<li>Email: <%=search_results.get(i).getEmail() %></li>
							</ul>
							<form action="AddFriend" method="post">
								<button class="btn btn-medium btn-success" type="submit" name="add_user" value="<%=search_results.get(i).getUsername() %>" >Add</button>
							</form>
						</div>
					</div>
				</div>
			</div>
			<%} %>
		<%} %>
	<%} 
	else {
	%>
		<h3>No such user!</h3>
	
	<%} 
	}
	%>
	</div>
	</div>
</div>


<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>