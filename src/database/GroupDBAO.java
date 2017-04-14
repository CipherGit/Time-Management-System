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
    
    public List<GroupDetails> getGroupsOfUser(int user_id) {
    	List<GroupDetails> groupOfUser = new ArrayList<GroupDetails>();
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM "+user_group_table+", "+group_table+" WHERE "+user_group_table+"."+col_group_id+" = "+group_table+"."+col_group_id+" AND "+user_group_table+"."+col_user_id+" = "+user_id+"";
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
			while(rs.next()) {
				GroupDetails gd = new GroupDetails();
				gd.setGroup_id(Integer.parseInt(rs.getString(3)));
				gd.setName(rs.getString(4));
				gd.setDescription(rs.getString(5));
				gd.setUser_id(Integer.parseInt(rs.getString(6)));
				gd.setGroup_sched(rs.getString(7));
				groupOfUser.add(gd);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return groupOfUser;
    }
    
    public boolean deleteMembers(int user_id) {
    	try {
			Statement stmt = con.createStatement();
			String query = "DELETE FROM "+user_group_table+" WHERE "+col_user_id+" = "+user_id+"";
			stmt.executeUpdate(query);
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    public int getGroupId(String name, String desc, int user_id, String schedule) {
    	int group_id = -1;
    	try {
			Statement stmt = con.createStatement();
			String query = "SELECT "+col_group_id+" FROM "+group_table+" WHERE "+col_name+" = '"+name+"' AND "+col_desc+" = '"+desc+"' AND "+col_user_id+" = "+user_id+" AND "+col_group_sched+" = '"+schedule+"'";
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
			while(rs.next()) {
				group_id = Integer.parseInt(rs.getString(1));
			}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return group_id;
    }
    
}
