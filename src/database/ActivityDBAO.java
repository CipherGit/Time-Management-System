package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDBAO {
    Connection con;
    private boolean conFree = true;
    private String activity_table = "tsm_db.activity";
    private String col_name = "name";
    private String col_start_date = "start_date";
    private String col_end_date = "end_date";
    private String col_schedule_id = "schedule_id";

    // Database configuration
    public static String url = "jdbc:mariadb://localhost:3306/tsm_db";
    public static String dbdriver = "org.mariadb.jdbc.Driver";
    public static String username = "root";
    public static String password = "";

    public ActivityDBAO() throws Exception {
        Class.forName(dbdriver);
        con = DriverManager.getConnection(url, username, password);
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

    public boolean addActivities(int schedule_id, List<ActivityDetails> activities){
    	try {
    		getConnection();
    		String query = "INSERT INTO "+activity_table+" ("+col_name+","+col_start_date+","+col_end_date+","+col_schedule_id+") VALUES(?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(query);
			for(ActivityDetails activity : activities){
				stmt.setString(1, activity.getName());
				stmt.setString(2, activity.getStart_date());
				stmt.setString(3, activity.getEnd_date());
				stmt.setInt(4, schedule_id);
				stmt.executeQuery();
			}
			releaseConnection();
			stmt.close();
			return true;
		} catch (SQLException e) {
		    releaseConnection();
			e.printStackTrace();
		}
    	return false;
    }

    public List<ActivityDetails> fetchAllForSched(int schedule_id) {
        List<ActivityDetails> activities = new ArrayList<ActivityDetails>();
        try {
            getConnection();
            String query = "SELECT * "+ "FROM " + activity_table + " WHERE " + col_schedule_id + " = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, schedule_id);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                ActivityDetails activity = new ActivityDetails();
                activity.setActivity_id(rs.getInt(1));
                activity.setName(rs.getString(2));
                activity.setStart_date(rs.getString(3));
                activity.setEnd_date(rs.getString(4));
                activity.setSchedule_id(rs.getInt(5));
                activities.add(activity);
            }
            
            releaseConnection();
            stmt.close();
        } catch (SQLException e) {
            releaseConnection();
            e.printStackTrace();
        }
        return activities;
    }
    
    public boolean deleteAllForSched(int schedule_id) {
        try {
            getConnection();
            String query = "DELETE * "+ "FROM " + activity_table + " WHERE " + col_schedule_id + " = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, schedule_id);
            stmt.executeQuery();
            
            releaseConnection();
            stmt.close();
            return true;
        } catch (SQLException e) {
            releaseConnection();
            e.printStackTrace();
        }
        return false;
    }
}
