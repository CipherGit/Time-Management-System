package database;

public class ScheduleDetails {
    int schedule_id;
    String indiv_sched;

    public ScheduleDetails() {
        
    }
    
    public ScheduleDetails(int schedule_id, String indiv_sched) {
        super();
        this.schedule_id = schedule_id;
        this.indiv_sched = indiv_sched;
    }

    public int getSchedule_id() {
        return schedule_id;
    }
    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }
    public String getIndiv_sched() {
        return indiv_sched;
    }
    public void setIndiv_sched(String indiv_sched) {
        this.indiv_sched = indiv_sched;
    }
}
