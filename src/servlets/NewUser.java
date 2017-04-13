package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDBAO;
import database.AccountDetails;
import database.FriendDBAO;
import database.ScheduleDBAO;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
public class NewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String indiv_sched = ""; //insert schedule value here
		int schedule_id = -1;
		try {
			ScheduleDBAO schedule = new ScheduleDBAO();
			schedule.addSchedule(indiv_sched);
			schedule_id = schedule.getId();
			schedule.remove();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		AccountDetails ad = new AccountDetails();
		ad = (AccountDetails) session.getAttribute("ad");
		ad.setSchedule_id(schedule_id);
			
		boolean result = false;
		try {
			AccountDBAO account = new AccountDBAO();
			result = account.addUser(ad);
			if (result){
				session.setAttribute("status", "Account_Added");
				session.setAttribute("ad", ad);
				int user1_id = account.getUserId(ad.getUsername());
				FriendDBAO friend = new FriendDBAO();
				List<Integer> friends = friend.checkFriends(user1_id);
				List<AccountDetails> friends_details = new ArrayList<AccountDetails>();
				for(int i=0; i<friends.size(); i++) {
					AccountDetails friend_detail = new AccountDetails();
					friend_detail = account.getUserDetails(friends.get(i));
					friends_details.add(friend_detail);
				}
				session.setAttribute("friends_details", friends_details);
				response.sendRedirect("profile.jsp");
				account.remove();
				return;
			}
			else {
				session.setAttribute("status", "Account_Failed");
				response.sendRedirect("setschedule.jsp");
				account.remove();
				return;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
