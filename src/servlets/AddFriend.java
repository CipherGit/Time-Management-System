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
 * Servlet implementation class AddFriend
 */
@WebServlet("/AddFriend")
public class AddFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFriend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String add_user= request.getParameter("add_user");
		AccountDetails ad = (AccountDetails) session.getAttribute("ad");
		try {
			AccountDBAO account = new AccountDBAO();
			int friend_id = account.getUserId(add_user);
			int user_id = account.getUserId(ad.getUsername());
			if(friend_id >= 0 && user_id >= 0) {
				FriendDBAO friend = new FriendDBAO();
				List<Integer> friends = friend.checkFriends(user_id);
				List<Integer> friends2 = friend.checkFriends2(user_id);
				for(int i=0; i<friends2.size(); i++) {
					friends.add(friends2.get(i));
				}
				boolean isFriends = false;
				for(int i=0; i<friends.size(); i++) {
					if(friends.get(i) == friend_id) {
						isFriends = true;
					}
				}
				
				if(isFriends == false) {
					boolean result = friend.addFriend(user_id, friend_id);
					if(result == true) {
						session.setAttribute("friend_status", "Positive");
						List<AccountDetails> friends_details = new ArrayList<AccountDetails>();
						friends = friend.checkFriends(user_id);
						friends2 = friend.checkFriends2(user_id);
						for(int i=0; i<friends2.size(); i++) {
							friends.add(friends2.get(i));
						}
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
						session.setAttribute("friend_status", "Negative");
						response.sendRedirect("addfriends.jsp");
						account.remove();
						friend.remove();
					}
				}
				else {
					session.setAttribute("friend_status", "FriendExist");
					response.sendRedirect("addfriends.jsp");
					account.remove();
					friend.remove();
				}
			}	
			else {
				System.out.println("Error finding user ID");
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
