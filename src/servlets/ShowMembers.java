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
import database.GroupDBAO;

/**
 * Servlet implementation class ShowMembers
 */
@WebServlet("/ShowMembers")
public class ShowMembers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMembers() {
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
			GroupDBAO group = new GroupDBAO();
			List<Integer> membersId = group.getUsersInGroup(Integer.parseInt(group_id));
			AccountDBAO account = new AccountDBAO();
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<div class=\"checkbox\">");
			out.println("<div class=\"list-group\">");
			for(int i=0; i<membersId.size(); i++) {
				AccountDetails ad = new AccountDetails();
				ad = account.getUserDetails(membersId.get(i));
				out.println("<label class=\"list-group-item\" style=\"padding-left: 30px;\"><input type=\"checkbox\" name=\"members\" value=\""+ad.getUsername()+"\">");
				out.println(ad.getName());
				out.println("</label>");
			}
			out.println("</div>");
			out.println("</div>");
			out.println("<input name=\"group_id\" value=\""+group_id+"\" style=\"display:none;\">");
			account.remove();
			group.remove();
			
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
