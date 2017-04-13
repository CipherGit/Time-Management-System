package database;

public class ActivityDetails {
	private int activity_id;
	private String name;
	private String start_date;
	private String end_date;
	private int schedule_id;
	
	public ActivityDetails() {
	}
	
	public ActivityDetails(String name, String start_date, String end_date) {
		super();
		this.name = name;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	public int getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}
}
