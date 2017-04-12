package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDBAO;
import database.AccountDetails;
import database.ScheduleDBAO;

/**
 * Servlet implementation class SingupServlet
 */
@WebServlet("/SignupServlet")
public class SingupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);		
		AccountDetails ad = new AccountDetails();
		ad.setUsername(request.getParameter("inputUsername"));
		ad.setPassword(request.getParameter("inputPassword"));
		ad.setName(request.getParameter("inputName"));
		ad.setEmail(request.getParameter("inputEmail"));

		try {
			AccountDBAO account = new AccountDBAO();
			boolean check = account.checkUser(ad.getUsername(), ad.getEmail());
			if(check == false) {
				session.setAttribute("ad", ad);	
				session.setAttribute("status", "Account_Registration");
				response.sendRedirect("setschedule.jsp");
				account.remove();
			}
			else {
				session.setAttribute("status", "Account_Exist");
				response.sendRedirect("index.jsp");
				account.remove();
			}
			
		} 
		catch (Exception e) {
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
