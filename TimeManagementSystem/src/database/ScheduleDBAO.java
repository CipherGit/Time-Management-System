package database;
import java.sql.*;

public class ScheduleDBAO {
	Connection con;
    private boolean conFree = true;
    private String schedule_table = "tsm_db.schedule";
    private String col_indiv_sched = "indiv_sched";
    
    // Database configuration
    public static String url = "jdbc:mariadb://localhost:3306/tsm_db";
    public static String dbdriver = "org.mariadb.jdbc.Driver";
    public static String username = "root";
    public static String password = "";
    
    public ScheduleDBAO() throws Exception {
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
    
    public boolean addSchedule(String schedule) {
    	
    	try {
			Statement stmt = con.createStatement();		
			String query = "INSERT INTO "+schedule_table+" ("+col_indiv_sched+") VALUES('"+schedule +"')";
			stmt.executeUpdate(query);
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    public int getId() {
    	try {
    		int schedule_id = -1;
			Statement stmt = con.createStatement();
			String query = "SELECT LAST_INSERT_ID()";
			ResultSet rs = stmt.executeQuery(query);
			int count = 0;
			while(rs.next()) {
				count++;
				schedule_id = Integer.parseInt(rs.getString(1));
			}
			if(count > 0) {
				return schedule_id;
			}
			else {
				return -1;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    	
    }
    

}
