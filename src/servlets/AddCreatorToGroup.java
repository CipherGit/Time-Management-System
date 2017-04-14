package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDBAO;
import database.AccountDetails;
import database.GroupDBAO;

/**
 * Servlet implementation class AddCreatorToGroup
 */
@WebServlet("/AddCreatorToGroup")
public class AddCreatorToGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCreatorToGroup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		AccountDetails ad = (AccountDetails) session.getAttribute("ad");
		int group_id = (Integer) session.getAttribute("newGroupId");
		try {
			AccountDBAO account = new AccountDBAO();
			int user_id = account.getUserId(ad.getUsername());
			GroupDBAO group = new GroupDBAO();
			boolean result = group.addUserToGroup(user_id, group_id);
			account.remove();
			group.remove();
			if(result==true) {
				session.setAttribute("newGroupId", null);
				response.sendRedirect("ShowGroups");
			}
			else {
				session.setAttribute("newGroupId", null);
				System.out.println("Error!");
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
