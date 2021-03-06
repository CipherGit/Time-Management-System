package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDBAO;
import database.GroupDBAO;

/**
 * Servlet implementation class AddMembers
 */
@WebServlet("/AddMembers")
public class AddMembers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMembers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String[] members = request.getParameterValues("members");
		String group_id = request.getParameter("group_id");
		boolean result = false;
		try {
			for(int i=0; i<members.length; i++) {
				AccountDBAO account = new AccountDBAO();
				GroupDBAO group = new GroupDBAO();
				int user_id = account.getUserId(members[i]);
				result = group.addUserToGroup(user_id, Integer.parseInt(group_id));
				account.remove();
				group.remove();
			}
			if(result==true) {
				session.setAttribute("members_status", "Added");
				response.sendRedirect("ShowGroups");	
			}
			else {
				session.setAttribute("members_status", "Error");
				response.sendRedirect("ShowGroups");
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
