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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

/**
 * Servlet implementation class SingupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);		
		AccountDetails ad = new AccountDetails();
		String password = request.getParameter("inputPassword");
		String salt = "InternetProgrammingJustinKarlXuanBoss0987654321";
		MessageDigest messageDigest=null;
		try {
			messageDigest = MessageDigest.getInstance("SHA");
			messageDigest.update((password+salt).getBytes());
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String encryptedPass = (new BigInteger(messageDigest.digest())).toString(16);
		
		ad.setUsername(request.getParameter("inputUsername"));
		ad.setPassword(encryptedPass);
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
