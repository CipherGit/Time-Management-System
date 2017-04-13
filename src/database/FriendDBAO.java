package database;
import java.sql.*;

public class FriendDBAO {
	Connection con;
    private boolean conFree = true;
    private String friends_table = "friends";
    private String col_user1_id = "user1_id";
    private String col_user2_id = "user2_id";
    
    // Database configuration
    public static String url = "jdbc:mariadb://localhost:3306/tsm_db";
    public static String dbdriver = "org.mariadb.jdbc.Driver";
    public static String username = "root";
    public static String password = "";
    
    public FriendDBAO() throws Exception {
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
    
    public boolean addFriend(int user1_id, int user2_id) {
    	try {
			Statement stmt = con.createStatement();
			String query = "INSERT INTO "+friends_table+" VALUES ("+user1_id+", "+user2_id+")";
			stmt.executeUpdate(query);
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }

}
