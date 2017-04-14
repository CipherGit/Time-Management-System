package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import database.AccountDBAO;
import database.AccountDetails;
import database.ActivityDBAO;
import database.ActivityDetails;
import database.GroupDBAO;
import database.ScheduleDBAO;

/**
 * Servlet implementation class GroupSchedulerServlet
 */
@WebServlet("/GroupSchedulerServlet")
public class GroupSchedulerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupSchedulerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		try {
		    AccountDBAO accountDBAO = new AccountDBAO();
		    GroupDBAO groupDBAO = new GroupDBAO();
		    ArrayList<AccountDetails> users = new ArrayList<AccountDetails>();
		    String group = request.getParameter("group");
		    if(group != null) {
		        int group_id = Integer.parseInt(group);
		        List<Integer> userList = groupDBAO.getUsersInGroup(group_id);
		        groupDBAO.remove();
		        for(int i=0; i<userList.size(); i++) {
		            users.add(accountDBAO.getUserDetails(userList.get(i)));
		        }
		        accountDBAO.remove();
		    }
		    
            response.setHeader("Content-type", "application/json");
            response.getWriter().println(createDisplayFromUsers(users));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	/**
	 * createDisplayFromUsers - compiles a group schedule based on a list of users
	 * @param users
	 * @return
	 * @throws Exception 
	 */
	private String createDisplayFromUsers(List<AccountDetails> users) throws Exception{
	    
	    ScheduleDBAO schedDBAO = new ScheduleDBAO();
	    ActivityDBAO activityDBAO = new ActivityDBAO();
	    
	    JSONArray groupMembers = new JSONArray();
	    for(AccountDetails user : users) {
	        JSONObject groupMember = new JSONObject();
	        groupMember.put("userName", user.getUsername());
	        groupMember.put("name", user.getName());
	        groupMember.put("schedule", schedDBAO.getSchedule(user.getSchedule_id()).getIndiv_sched());
	        
	        List<ActivityDetails> activities = activityDBAO.fetchAllForSched(user.getSchedule_id());
	        JSONArray events = new JSONArray();
	        for(ActivityDetails activity : activities) {
	            JSONObject event = new JSONObject();
	            event.put("title", activity.getName());
	            event.put("start", activity.getStart_date());
	            event.put("end", activity.getEnd_date());
	            events.add(event);
	        }
	        groupMember.put("events", events);
	        groupMembers.add(groupMember);
	    }
	    
	    schedDBAO.remove();
	    activityDBAO.remove();
	    return groupMembers.toJSONString();
	}
}
