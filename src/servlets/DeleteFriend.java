package servlets;

import java.io.IOException;
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

/**
 * Servlet implementation class DeleteFriend
 */
@WebServlet("/DeleteFriend")
public class DeleteFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFriend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String delete_user= request.getParameter("delete_user");
		AccountDetails ad = (AccountDetails) session.getAttribute("ad");
		try {
			AccountDBAO account = new AccountDBAO();
			int friend_id = account.getUserId(delete_user);
			int user_id = account.getUserId(ad.getUsername());
			FriendDBAO friend = new FriendDBAO();
			boolean result = friend.deleteFriend(user_id, friend_id);
			if(result == true) {
				session.setAttribute("friend_status", "Deleted");
				List<Integer> friends = friend.checkFriends(user_id);
				List<Integer> friends2 = friend.checkFriends2(user_id);
				for(int i=0; i<friends2.size(); i++) {
					friends.add(friends2.get(i));
				}
				List<AccountDetails> friends_details = new ArrayList<AccountDetails>();
				for(int i=0; i<friends.size(); i++) {
					AccountDetails friend_detail = new AccountDetails();
					friend_detail = account.getUserDetails(friends.get(i));
					friends_details.add(friend_detail);
				}
				session.setAttribute("friends_details", friends_details);
				response.sendRedirect("profile.jsp");
				account.remove();
				friend.remove();
			}
			else {
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
