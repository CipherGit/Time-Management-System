<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>About</title>

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<% String status = (String) session.getAttribute("status");
	if(status != null) { 
		if(status.equals("Login_Success")) { %>
			<div id="nav-here2"></div>
		<%}
		else {%>
			<div id="nav-here"></div>
		<%}	
	} else {%>
		<div id="nav-here"></div>
	<%} %>

<div class="container">

	<div class="jumbotron">
	  <h1>MeetU</h1>
	  <h2>Time Management System</h2>
	  <p style="text-align:justify;">Nowadays, people are more and more busy with various kinds of activities, so the schedules and available time for each person are commonly different from others’. For some shared activities or plans, it is not easy to create an available time slot for all participants, especially for activities or plans that have many participants. Thus, digital schedule came out, which allows each participant to create his or her own digital schedules in the system, and share his or her schedule to other people. The system can consolidate all digital schedules to find out the overlapping available time slots, after which users can plan on what to do on these free time slots.</p>
	  <p style="text-align:justify;">Finding a common available time slot is the premise to create schedule for a  group of users. This app allows users to:</p>
	  <ul style="font-size:20px;">
	  	<li>Create his/her own digital schedule and add activities</li>
	  	<li>Share schedule to other users in a group</li>
	  	<li>Invite friend to join groups and plan for group activities</li>
	  </ul>
	  <span>*Take note that the assumption here is that users have a fixed schedule</span>
 
	  <p><a class="btn btn-primary btn-lg" style="margin-top: 20px;" href="index.jsp" role="button">Give it a try!</a></p>
	</div>
</div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/myjquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>