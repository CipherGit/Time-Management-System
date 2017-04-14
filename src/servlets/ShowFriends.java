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

import database.AccountDBAO;
import database.AccountDetails;
import database.FriendDBAO;

/**
 * Servlet implementation class ShowFriends
 */
@WebServlet("/ShowFriends")
public class ShowFriends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowFriends() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String group_id = request.getParameter("groupId");
		String username = request.getParameter("username");
		try {
			AccountDBAO account = new AccountDBAO();
			int user_id = account.getUserId(username);
			FriendDBAO friend = new FriendDBAO();
			List<Integer> friends = friend.checkFriends(user_id);
			List<Integer> friends2 = friend.checkFriends2(user_id);
			for(int i=0; i<friends2.size(); i++) {
				friends.add(friends2.get(i));
			}
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<div class=\"checkbox\">");
			out.println("<div class=\"list-group\">");
			for(int i=0; i<friends.size(); i++) {
				AccountDetails friend_detail = new AccountDetails();
				friend_detail = account.getUserDetails(friends.get(i));
				out.println("<label class=\"list-group-item\" style=\"padding-left: 30px;\"><input type=\"checkbox\" name=\"members\" value=\""+friend_detail.getUsername()+"\">");
				out.println(friend_detail.getName());
				out.println("</label>");
			}
			out.println("</div>");
			out.println("</div>");
			out.println("<input name=\"group_id\" value=\""+group_id+"\" style=\"display:none;\">");
			
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
