package database;

public class GroupDetails {
	private int group_id;
	private String name;
	private String description;
	private int user_id;
	private String group_sched;
	
	public GroupDetails() {
		
	}
	
	public GroupDetails (String n, String d, int uid, String gsched) {
		this.name = n;
		this.description = d;
		this.user_id = uid;
		this.group_sched = gsched;
	}
	
	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getGroup_sched() {
		return group_sched;
	}

	public void setGroup_sched(String group_sched) {
		this.group_sched = group_sched;
	}

}
