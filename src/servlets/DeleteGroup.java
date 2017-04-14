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
 * Servlet implementation class DeleteGroup
 */
@WebServlet("/DeleteGroup")
public class DeleteGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGroup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		AccountDetails ad = (AccountDetails) session.getAttribute("ad");
		String delete_group= request.getParameter("delete_group");
		try {
			GroupDBAO group = new GroupDBAO();
			boolean result = group.deleteGroup(Integer.parseInt(delete_group));
			if(result == true) {
				session.setAttribute("group_status", "Deleted");
				AccountDBAO account = new AccountDBAO();
				int user_id = account.getUserId(ad.getUsername());
				List<GroupDetails> groups = group.showGroups(user_id);
				session.setAttribute("groups", groups);
				response.sendRedirect("ShowGroups");
				account.remove();
				group.remove();
			}
			else {
				session.setAttribute("group_status", "Error");
				response.sendRedirect("ShowGroups");
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
