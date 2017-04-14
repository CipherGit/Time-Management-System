package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDBAO {
	Connection con;
    private boolean conFree = true;
    private String group_table = "group_t";
    private String col_group_id = "group_id";
    private String col_name = "name";
    private String col_desc = "description";
    private String col_user_id = "user_id";
    private String col_group_sched = "group_sched";
    
    private String user_group_table = "user_group";
    
    // Database configuration
    public static String url = "jdbc:mariadb://localhost:3306/tsm_db";
    public static String dbdriver = "org.mariadb.jdbc.Driver";
    public static String username = "root";
    public static String password = "";
    
    public GroupDBAO() throws Exception {
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
    
    public boolean addGroup(GroupDetails gd) {
    	try {
    		getConnection();
			String query = "INSERT INTO "+group_table+"("+col_name+", "+col_desc+", "+col_user_id+", "+col_group_sched+") VALUES (?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, gd.getName());
			stmt.setString(2, gd.getDescription());
			stmt.setInt(3, gd.getUser_id());
			stmt.setString(4,gd.getGroup_sched());
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    public List<GroupDetails> showGroups(int user_id) {
    	List<GroupDetails> groups = new ArrayList<GroupDetails>();
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM "+group_table+" WHERE "+col_user_id+" = "+user_id+"";
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
			while(rs.next()) {
				GroupDetails group = new GroupDetails();
				group.setGroup_id(Integer.parseInt(rs.getString(1)));
				group.setName(rs.getString(2));
				group.setDescription(rs.getString(3));
				group.setUser_id(Integer.parseInt(rs.getString(4)));
				group.setGroup_sched(rs.getString(5));
				groups.add(group);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return groups;
    }
    
    public boolean deleteGroup(int group_id) {
    	try {
			Statement stmt = con.createStatement();
			String query = "DELETE FROM "+group_table+" WHERE "+col_group_id+" = "+group_id;
			stmt.executeUpdate(query);
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean addUserToGroup(int user_id, int group_id) {
		try {
			getConnection();
			String query = "INSERT INTO "+user_group_table+"("+col_user_id+", "+col_group_id+") VALUES (?,?)";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, user_id);
			stmt.setInt(2, group_id);
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    public List<Integer> getUsersInGroup(int group_id) {
    	List<Integer> usersInGroup = new ArrayList<Integer>();
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT "+col_user_id+" FROM "+user_group_table+" WHERE "+col_group_id+" = "+group_id+"";
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
			while(rs.next()) {
				int user_id = Integer.parseInt(rs.getString(1));
				usersInGroup.add(user_id);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usersInGroup;
		
	}
    
    
}
