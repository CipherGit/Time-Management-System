package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import database.AccountDetails;
import database.ActivityDBAO;
import database.ActivityDetails;
import database.ScheduleDBAO;
import database.ScheduleDetails;

/**
 * Servlet implementation class SchedulerServlet
 */
@WebServlet("/SchedulerServlet")
public class SchedulerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchedulerServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Parse request contents
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        
        // Check request header to determine correct action
        String purpose = request.getHeader("Purpose");
        if(purpose != null) {
            if(purpose.toLowerCase().contains("modify")){
                JSONParser parser = new JSONParser();
                try {
                    //Parse JSON
                    JSONObject jobj = (JSONObject) parser.parse(sb.toString());
                    String startDate = (String) jobj.get("startDate");
                    String endDate = (String) jobj.get("endDate");
                    String minTime = (String) jobj.get("minTime");
                    String maxTime = (String) jobj.get("maxTime");
                    JSONArray events = (JSONArray) jobj.get("events");
                    
                    //Convert JSON to Database Detail Objects
                    ArrayList<ActivityDetails> activities = getActivityDetails(events);
                    ScheduleDetails schedule = getSchedFromData(startDate, endDate, minTime, maxTime, activities);
                    
                    //Store to DB
                    storeDB(request, response, schedule, activities);
                    if(request.getHeader("Schedule-type").equals("update")){
                        response.getWriter().println("Schedule Saved!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (purpose.toLowerCase().contains("index")) {
                JSONParser parser = new JSONParser();
                try {
                    //Parse JSON
                    JSONObject jobj = (JSONObject) parser.parse(sb.toString());
                    String startDate = (String) jobj.get("startDate");
                    String minTime = (String) jobj.get("minTime");
                    String maxTime = (String) jobj.get("maxTime");
                    String slot = (String) jobj.get("slot");
                    
                    String[] activityDT = slot.split("T");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    int timeSteps = calculateTimeSteps(format, startDate, minTime, maxTime);
                    int index = getIndex(format, startDate + "T" + minTime, activityDT[0] + "T" + minTime, slot, timeSteps);
                    
                    response.getWriter().println(index);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (purpose.toLowerCase().contains("fetch")) {
                try {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(userSchedtoJson(request));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * getEventDetails - Returns an ArrayList of activities from JSON data
     */
    private ArrayList<ActivityDetails> getActivityDetails(JSONArray events) {
        ArrayList<ActivityDetails> activities = new ArrayList<ActivityDetails>();
        Iterator<?> itEvent = events.iterator();

        while (itEvent.hasNext()) {
            JSONObject event = (JSONObject) itEvent.next();
            ActivityDetails activity = new ActivityDetails();
            activity.setName((String) event.get("title"));
            activity.setStart_date((String) event.get("start"));
            activity.setEnd_date((String) event.get("end"));
            activities.add(activity);
        }
        return activities;
    }

    /**
     * getScheduleDetails - Returns ScheduleDetails based on activities
     */
    private ScheduleDetails getSchedFromData(String startDate, String endDate, String minTime, String maxTime,
            ArrayList<ActivityDetails> activities) {
        // Define Date Format based on MomentJS
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        // Get total number of days in the calendar view
        Date startCal = null, endCal = null;
        int numDays = 0;
        try {
            startCal = format.parse(startDate + "T" + minTime);
            endCal = format.parse(endDate + "T" + maxTime);
            long diff = endCal.getTime() - startCal.getTime();
            numDays = (int) TimeUnit.MILLISECONDS.toDays(diff);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // Get time steps per day
        int timeSteps = calculateTimeSteps(format, startDate, minTime, maxTime);

        // Create Schedule array
        int[] schedule = new int[numDays * timeSteps];

        // Fill Schedule array based on activities
        for (int i = 0; i < activities.size(); i++) {
            String[] activityDT = activities.get(i).getStart_date().split("T");
            int startIndex = getIndex(format, startDate + "T" + minTime, activityDT[0] + "T" + minTime,
                    activities.get(i).getStart_date(), timeSteps);
            int endIndex = getIndex(format, startDate + "T" + minTime, activityDT[0] + "T" + minTime,
                    activities.get(i).getEnd_date(), timeSteps);
            for (int j = startIndex; j < endIndex; j++) {
                if (schedule[j] < 1) {
                    schedule[j]++;
                }
            }
        }
        ScheduleDetails scheduleDetail = new ScheduleDetails();
        scheduleDetail.setIndiv_sched(generateSchedString(schedule));
        return scheduleDetail;
    }

    /**
     * getIndex - Returns the index of a certain time step based on the schedule
     */
    private int getIndex(SimpleDateFormat format, String baseStart, String activityStart, String activityEnd,
            int totalTimeSteps) {
        int steps = 0, days = 0;
        try {
            Date baseDate = format.parse(baseStart);
            Date startDate = format.parse(activityStart);
            Date endDate = format.parse(activityEnd);
            long diffSteps = endDate.getTime() - startDate.getTime();
            long diffBase = endDate.getTime() - baseDate.getTime();
            int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diffSteps);
            steps = minutes / 30;
            days = (int) TimeUnit.MILLISECONDS.toDays(diffBase);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return (days * totalTimeSteps) + steps;
    }

    /**
     * generateSchedString - Converts an int array into a string
     */
    private String generateSchedString(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i : a) {
            sb.append(i);
        }
        return sb.toString();
    }
    
    /**
     * storeDB - stores schedule and activities in the DB
     * @throws Exception 
     */
    private void storeDB(HttpServletRequest request, HttpServletResponse response, ScheduleDetails schedule, ArrayList<ActivityDetails> activities) throws Exception{
        //Retrieve User Details
        HttpSession session = request.getSession(true);
        AccountDetails ad = (AccountDetails) session.getAttribute("ad");
        
        //If schedule does not exist add it, else update it
        ScheduleDBAO schedDBAO = new ScheduleDBAO();
        ActivityDBAO activityDBAO = new ActivityDBAO();
        if(ad.getSchedule_id() == -1){
            schedDBAO.addSchedule(schedule.getIndiv_sched());
            activityDBAO.addActivities(schedDBAO.getId(), activities);
        } else {
            schedDBAO.updateSchedule(ad.getSchedule_id(), schedule.getIndiv_sched());
            activityDBAO.deleteAllForSched(ad.getSchedule_id());
            activityDBAO.addActivities(ad.getSchedule_id(), activities);
        }
        
        if(request.getHeader("Schedule-type").equals("new")){
            RequestDispatcher rd = request.getRequestDispatcher("NewUser");
            response.setIntHeader("sched_id", schedDBAO.getId());
            rd.forward(request, response);
        }
        
        schedDBAO.remove();
        activityDBAO.remove();
    }
    
    /**
     * userSchedtoJson - returns a stringified JSON of Schedule and Activity Data for the user
     * @param request
     * @return
     * @throws Exception 
     */
    private String userSchedtoJson(HttpServletRequest request) throws Exception{
        //Retrieve User Details
        HttpSession session = request.getSession(true);
        AccountDetails ad = (AccountDetails) session.getAttribute("ad");
       
        ActivityDBAO activityDBAO = new ActivityDBAO();
        ArrayList<ActivityDetails> activities = (ArrayList<ActivityDetails>) activityDBAO.fetchAllForSched(ad.getSchedule_id());
        
        JSONArray jarr = new JSONArray();
        for(int i = 0; i<activities.size(); i++){
            JSONObject jobj = new JSONObject();
            jobj.put("title",activities.get(i).getName());
            jobj.put("start",activities.get(i).getStart_date());
            jobj.put("end",activities.get(i).getEnd_date());
            jarr.add(jobj);
        }
        
        activityDBAO.remove();
        return jarr.toJSONString();
    }
    
    /**
     * calculateTimeSteps - returns the timesteps per day
     * @param format
     * @param date
     * @param minTime
     * @param maxTime
     * @return
     */
    private int calculateTimeSteps(SimpleDateFormat format, String date, String minTime, String maxTime){
        int timeSteps = 0;
        try {
            Date startTime = format.parse(date + "T" + minTime);
            Date endTime = format.parse(date + "T" + maxTime);
            long diff = endTime.getTime() - startTime.getTime();
            int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diff);
            timeSteps = minutes / 30;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return timeSteps;
    }

}
