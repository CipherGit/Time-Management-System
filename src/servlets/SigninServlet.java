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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/SigninServlet")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SigninServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		String username= request.getParameter("inputUsername");        
		String password = request.getParameter("inputPassword");
		try {
			AccountDBAO account = new AccountDBAO();
			AccountDetails ad = account.loginCheck(username, password);
			if(ad != null) {
				session.setAttribute("status", "Login_Success");
				session.setAttribute("ad", ad);
				int user1_id = account.getUserId(ad.getUsername());
				FriendDBAO friend = new FriendDBAO();
				List<Integer> friends = friend.checkFriends(user1_id);
				List<Integer> friends2 = friend.checkFriends2(user1_id);
		
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
				return;
			}
			else {
				session.setAttribute("status", "Login_Failed");
				response.sendRedirect("index.jsp");
				account.remove();
				return;
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
