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
<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.min.css'/>
<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.print.css' media='print'/>
</head>
<body>
<%
	String status = (String) session.getAttribute("status");
	String fromShowGroups = (String) session.getAttribute("fromShowGroups");
	String add_group = (String) session.getAttribute("add_group");
	String group_status = (String) session.getAttribute("group_status");
	String members_status = (String) session.getAttribute("members_status");
	AccountDetails ad = (AccountDetails) session.getAttribute("ad");
	List<GroupDetails> groups = (List<GroupDetails>) session.getAttribute("groups");
	if(status != null) {
		if(status.equals("Login_Success")) {
			System.out.println("Login_Success");
			if(fromShowGroups != null) {
				if(fromShowGroups.equals("true")) {
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
						else if(members_status.equals("Deleted")) {
							out.print("<script type=\"text/javascript\">alert('Members deleted!');</script>");
							session.setAttribute("members_status", "normal");
						}
					}
					session.setAttribute("fromShowGroups", "false");
				}
				else {
					response.sendRedirect("profile.jsp");
				}
			}
			else {
				response.sendRedirect("profile.jsp");
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
		<%if(fromShowGroups != null) { %>
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
						<div id="calendar<%=i%>"></div>
						<div class="row" style="margin-top:20px;">
							<div class="col-md-3">
								<button class="btn btn-medium btn-success" type="button" data-toggle="modal" data-target="#addMembers" data-group-id="<%=groups.get(i).getGroup_id() %>" data-username="<%=ad.getUsername() %>">Add Members</button>
							</div>
							<div class="col-md-3">
								<button class="btn btn-medium btn-warning" type="button" data-toggle="modal" data-target="#showMembers" data-group-id="<%=groups.get(i).getGroup_id() %>" data-username="<%=ad.getUsername() %>">Show Members</button>
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

<div class="modal fade" id="showMembers" tabindex="-1" role="dialog" aria-labelledby="showMembersModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">Show Members</h4>
			</div>
			<form action="DeleteMembers" method="post">
			<div class="modal-body">
				<h3>Members:</h3>
				<div id="membersList"></div>
			</div>
			<div class="modal-footer">
		    	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="submit" class="btn btn-danger" value="Submit">Delete Members</button>
		    </div>
		    </form>
		</div>
	</div>
</div>

<div id="TimeSlotDetail" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 id="TimeSlotHeader">Modal Header</h3>
				<p id="availabilityCount">Availability Count: </p>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-6">
							<p>Available:</p>
							<ul id="availableList"></ul>
						</div>
						<div class="col-md-6">
							<p>Unavailable:</p>
							<ul id="unavailableList"></ul>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src='//cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js'></script>
<script type="text/javascript" src='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.min.js'></script>
<script type="text/javascript" src="js/myjquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src='js/groupDisplay.js'></script>
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
$('#showMembers').on('show.bs.modal', function(e) {
    //get data-id attribute of the clicked element
    var groupId = $(e.relatedTarget).data('group-id');
    var username = $(e.relatedTarget).data('username');
    $.ajax({
    	type: "POST",
    	url: "ShowMembers",
    	data: {"groupId":groupId, "username":username},
    	success: function(response) {
    		$('#membersList').html(response);
    	}
    });
});

//Calendar Code
$(document).ready(function() {
<%if(fromShowGroups != null) { %>
<%for(int i=0; i<groups.size(); i++) { %>
	$('#calendar<%=i%>').fullCalendar({
	    minTime: minTime,
	    maxTime: maxTime,
	    defaultDate: "2017-04-05",
	    visibleRange: {
	      start: '2017-04-02',
	      end: '2017-04-08'
	    },
	    defaultView: 'agendaWeek',
	    columnFormat: 'dddd',
	    header: false,
	    allDaySlot: false,
	    editable: false,
	    events: [],
	    dayClick: function(date, jsEvent, view) {
	      $('#TimeSlotHeader').html(date.format('dddd, h:mma'))
	      $('#TimeSlotDetail').modal({show:true})
	      getIndex(<%=i%>, date);
	    }
	});
	$('#collapse<%=i%>').on('shown.bs.collapse', function () {
	  $('#calendar<%=i%>').fullCalendar('render');
	});
	retrieveData(<%=i%>, <%=groups.get(i).getGroup_id() %>)
<%}
}%>
});
</script>
</body>
</html>
