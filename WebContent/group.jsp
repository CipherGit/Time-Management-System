<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="database.AccountDetails" %>
<%@ page import="database.GroupDetails" %>
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
	String add_group = (String) session.getAttribute("add_group");
	String group_status = (String) session.getAttribute("group_status");
	String members_status = (String) session.getAttribute("members_status");
	AccountDetails ad = (AccountDetails) session.getAttribute("ad");
	List<GroupDetails> groups = (List<GroupDetails>) session.getAttribute("groups");
	if(status != null) {
		if(status.equals("Login_Success")) {
			System.out.println("Login_Success");
			if(add_group != null) {
				if(add_group.equals("Positive")) {
					out.print("<script type=\"text/javascript\">alert('Group created!');</script>");
					session.setAttribute("add_group", "normal");
				}
				else if(add_group.equals("Negative")) {
					out.print("<script type=\"text/javascript\">alert('Error creating group!');</script>");
					session.setAttribute("add_group", "normal");
				}
			}
			if(group_status != null) {
				if(group_status.equals("Deleted")) {
					out.print("<script type=\"text/javascript\">alert('Group deleted!');</script>");
					session.setAttribute("group_status", "normal");
				}
				else if(group_status.equals("Error")) {
					out.print("<script type=\"text/javascript\">alert('Error deleting group!');</script>");
					session.setAttribute("group_status", "normal");
				}
			}
			if(members_status != null) {
				if(members_status.equals("Added")) {
					out.print("<script type=\"text/javascript\">alert('Members added!');</script>");
					session.setAttribute("members_status", "normal");
				}
				else if(members_status.equals("Error")) {
					out.print("<script type=\"text/javascript\">alert('Error adding members!');</script>");
					session.setAttribute("members_status", "normal");
				}
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
	<div class="jumbotron">
		<form method="post" action="NewGroup">
			<input type="text" name="groupName" placeholder=" Group name" required></input>
			<br/>
			<textarea type="text" name="groupDesc" placeholder=" Group description"></textarea>
			<br/>
			<button type="submit" class="btn btn-medium btn-primary">Create Group</button>
		</form>
		<h2>Your Groups:</h2>
		<%if(ad != null) { %>
		<%if(groups.size() > 0) { %>
		<%for(int i=0; i<groups.size(); i++) { %>
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
			<div class="panel panel-primary">
				<div class="panel-heading" role="tab" id="heading<%=i%>">
					<h4 class="panel-title">
						<a class="collapsed" role="button" data-toggle="collapse" href="#collapse<%=i%>"  aria-expanded="false" aria-controls="collapse<%=i%>">
									<%=groups.get(i).getName() %>
							</a>
					</h4>
				</div>
				<div id="collapse<%=i%>" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading<%=i%>">
					<div class="panel-body">
						<h3>Description:</h3>
						<p><%=groups.get(i).getDescription() %></p>
						<h3>Group Schedule:</h3>
						<div>
							<!-- insert group schedule here -->
							<%=groups.get(i).getGroup_sched() %>
						
						</div>
						<div class="row">
							<div class="col-md-3">
								<button class="btn btn-medium btn-success" type="button" data-toggle="modal" data-target="#addMembers" data-group-id="<%=groups.get(i).getGroup_id() %>" data-username="<%=ad.getUsername() %>">Add Members</button>
							</div>
							<div class="col-md-3">
								<form action="" method="post">
										<button class="btn btn-medium btn-warning" type="submit" name="delete_member" value="<%=groups.get(i).getUser_id() %>" >Delete Members</button>
								</form>
							</div>
							<div class="col-md-3">
								<form action="DeleteGroup" method="post">
										<button class="btn btn-medium btn-danger" type="submit" name="delete_group" value="<%=groups.get(i).getGroup_id() %>" >Delete Group</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%} %>
		<%}
		else {%>
		<h3>You don't have any groups. You can create one then add your friends!</h3>
		<%} %>
		<%} %>
	</div>
</div>

<div class="modal fade" id="addMembers" tabindex="-1" role="dialog" aria-labelledby="addMembersModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">Add Members</h4>
			</div>
			<form action="AddMembers" method="post">
			<div class="modal-body">
				<h3>Friends:</h3>
				<div id="friendsList"></div>
			</div>
			<div class="modal-footer">
		    	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="submit" class="btn btn-primary" value="Submit">Add Friends</button>
		    </div>
		    </form>
		</div>
	</div>
</div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript">
$('#addMembers').on('show.bs.modal', function(e) {

    //get data-id attribute of the clicked element
    var groupId = $(e.relatedTarget).data('group-id');
    var username = $(e.relatedTarget).data('username');
 
    $.ajax({
    	type: "POST",
    	url: "ShowFriends",
    	data: {"groupId":groupId, "username":username},
    	success: function(response) {
    		$('#friendsList').html(response);
    	}
    });
    
});
</script>
</body>
</html>