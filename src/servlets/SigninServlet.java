package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDBAO;
import database.AccountDetails;

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
