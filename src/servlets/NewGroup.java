package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDBAO;
import database.AccountDetails;
import database.GroupDBAO;
import database.GroupDetails;

/**
 * Servlet implementation class NewGroup
 */
@WebServlet("/NewGroup")
public class NewGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewGroup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String groupName = request.getParameter("groupName");
		String groupDesc = request.getParameter("groupDesc");
		String groupSched = ""; //put schedule string of the group here
		AccountDetails ad = (AccountDetails) session.getAttribute("ad");
		try {
			AccountDBAO account = new AccountDBAO();
			int user_id = account.getUserId(ad.getUsername());
			GroupDBAO group = new GroupDBAO();
			GroupDetails gd = new GroupDetails(groupName, groupDesc, user_id, groupSched);
			boolean result = group.addGroup(gd);
			if(result == true) {
				session.setAttribute("add_group", "Positive");
				List<GroupDetails> groups = group.showGroups(user_id);
				session.setAttribute("groups", groups);
				response.sendRedirect("group.jsp");
				account.remove();
				group.remove();
			}
			else {
				session.setAttribute("add_group", "Negative");
				response.sendRedirect("group.jsp");
				account.remove();
				group.remove();
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