package database;
import java.sql.*;

public class AccountDBAO {
	Connection con;
    private boolean conFree = true;
    private String user_table = "user";
    private String col_username = "username";
    private String col_password = "password";
    private String col_name = "name";
    private String col_email = "email";
    private String col_schedule_id = "schedule_id";
    
    // Database configuration
    public static String url = "jdbc:mariadb://localhost:3306/tsm_db";
    public static String dbdriver = "org.mariadb.jdbc.Driver";
    public static String username = "root";
    public static String password = "";
    
    public AccountDBAO() throws Exception {
        try {
            Class.forName(dbdriver);
            con = DriverManager.getConnection(url, username, password);
            
        } catch (Exception ex) {
            System.out.println("Exception in AccountDBAO: " + ex);
            throw new Exception("Couldn't open connection to database: " +
                    ex.getMessage());
        }
    }
    
    public void remove() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    protected synchronized Connection getConnection() {
        while (conFree == false) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        
        conFree = false;
        notify();
        
        return con;
    }
    
    protected synchronized void releaseConnection() {
        while (conFree == true) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        
        conFree = true;
        notify();
    }
    
    public boolean addUser(AccountDetails ad) {
    	try {
			Statement stmt = con.createStatement();
			String query = "INSERT INTO "+user_table+"("+col_username+", "+col_password+", "+col_name+", "+col_email+", "+col_schedule_id+") VALUES ('"+ad.getUsername()+"', '"+ad.getPassword()+"', '"+ad.getName()+"', '"+ad.getEmail()+"', "+ad.getSchedule_id()+")";
			stmt.executeUpdate(query);
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    public boolean checkUser(String username, String email) {
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM "+user_table+" WHERE "+col_username+" = '"+username+"' OR "+col_email+" = '"+email+"'";
			ResultSet rs = stmt.executeQuery(query);
			int count = 0;
			while(rs.next()) {
				count++;
			}
			if(count > 0) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    public AccountDetails loginCheck(String username, String password) {
    	AccountDetails ad = new AccountDetails();
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM "+user_table+" WHERE "+col_username+" = '"+username+"' AND "+col_password+" = '"+password+"'";
			ResultSet rs = stmt.executeQuery(query);
			int count = 0;
			while(rs.next()) {
				count++;
				String userid = rs.getString(1);
				ad.setUsername(rs.getString(2));
				ad.setPassword(rs.getString(3));
				ad.setName(rs.getString(4));
				ad.setEmail(rs.getString(5));
				ad.setSchedule_id(Integer.parseInt(rs.getString(6)));
			}
			if(count > 0) {
				return ad;
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
    }
}
