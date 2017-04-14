package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDBAO {
	Connection con;
    private boolean conFree = true;
    private String user_table = "user";
    private String col_user_id = "user_id";
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
    		getConnection();
			String query = "INSERT INTO "+user_table+"("+col_username+", "+col_password+", "+col_name+", "+col_email+", "+col_schedule_id+") VALUES (?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, ad.getUsername());
			stmt.setString(2, ad.getPassword());
			stmt.setString(3, ad.getName());
			stmt.setString(4, ad.getEmail());
			stmt.setInt(5, ad.getSchedule_id());
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
    }
    
    public boolean checkUser(String username, String email) {
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM "+user_table+" WHERE "+col_username+" = '"+username+"' OR "+col_email+" = '"+email+"'";
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
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
			stmt.close();
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
    
    public List<AccountDetails> findUsers(String username, String name, String email) {
    	List<AccountDetails> users = new ArrayList<AccountDetails>();
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM "+user_table+" WHERE "+col_username+" LIKE '%"+username+"%' OR "+col_email+" LIKE '%"+email+"%' OR "+col_name+" LIKE '%"+name+"%'";
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
			while(rs.next()) {
				AccountDetails ad = new AccountDetails();
				String userid = rs.getString(1);
				ad.setUsername(rs.getString(2));
				ad.setPassword(rs.getString(3));
				ad.setName(rs.getString(4));
				ad.setEmail(rs.getString(5));
				ad.setSchedule_id(Integer.parseInt(rs.getString(6)));
				users.add(ad);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return users;
    }
    
    public int getUserId(String username) {
    	int userId = -1;
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM "+user_table+" WHERE "+col_username+" = '"+username+"'";
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
			while(rs.next()) {
				userId = Integer.parseInt(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return userId;
    }
    
    public AccountDetails getUserDetails(int user_id) {
    	AccountDetails ad = new AccountDetails();
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM "+user_table+" WHERE "+col_user_id+" = "+user_id+"";
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
			while(rs.next()) {
				String userid = rs.getString(1);
				ad.setUsername(rs.getString(2));
				ad.setPassword(rs.getString(3));
				ad.setName(rs.getString(4));
				ad.setEmail(rs.getString(5));
				ad.setSchedule_id(Integer.parseInt(rs.getString(6)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ad;
    }
}
