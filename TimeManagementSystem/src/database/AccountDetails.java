package database;

public class AccountDetails {
	private String username = null;
	private String password = null;
	private String name = null;
	private String email = null;
	private int schedule_id = -1;
	
	public AccountDetails() {
		
	}
	
	public AccountDetails(String uName, String pWord, String name, String email, int sId) {
		this.username = uName;
		this.password = pWord;
		this.name = name;
		this.email = email;
		this.schedule_id = sId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}

}

