package scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import objects.Activity;

/**
 * Servlet implementation class IndividualScheduler
 */
@WebServlet("/IndividualScheduler")
public class IndividualScheduler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndividualScheduler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Activity> activities = new ArrayList<Activity>();
		String calendarStart = "";
		String calendarEnd = "";
		String minTime = "";
		String maxTime = "";
		
		//Read Response String
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
	    
	    //Parse the JSON
	    JSONParser parser = new JSONParser();
	    try {
			JSONObject jobj = (JSONObject) parser.parse(sb.toString());
            calendarStart = (String) jobj.get("startDate");
            calendarEnd = (String) jobj.get("endDate");
            minTime = (String) jobj.get("minTime");
            maxTime = (String) jobj.get("maxTime");
            JSONArray events = (JSONArray) jobj.get("events");
            Iterator<?> itEvent = events.iterator();

            while (itEvent.hasNext()) {
                JSONObject event = (JSONObject) itEvent.next();
                Activity activity = new Activity();
                activity.setName((String) event.get("title"));
                activity.setStart_date((String) event.get("start"));
                activity.setEnd_date((String) event.get("end"));
                activities.add(activity);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    //Process Information Before Sending to the Database
	    processScheduleData(calendarStart, calendarEnd, minTime, maxTime, activities);
	}
	
	protected void processScheduleData(String startDate, String endDate, String minTime, String maxTime, ArrayList<Activity> activities) {
		
		//Define Date Format based on MomentJS
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		//Get total number of days in the calendar view
		Date startCal = null, endCal = null;
		int numDays = 0;
		try {
			startCal = format.parse(startDate+"T"+minTime);
			endCal = format.parse(endDate+"T"+maxTime);
	        long diff = endCal.getTime() - startCal.getTime();
	        numDays = (int) TimeUnit.MILLISECONDS.toDays(diff);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		//Get time steps per day
		int timeSteps = 0;
		try {
			startCal = format.parse(startDate+"T"+minTime);
			endCal = format.parse(startDate+"T"+maxTime);
	        long diff = endCal.getTime() - startCal.getTime();
	        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diff);
	        timeSteps = minutes/30;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
		//Create Schedule array
		int[] schedule = new int[numDays*timeSteps];
		
		//Fill Schedule array based on activities
		for(int i=0; i<activities.size(); i++){
			String[] activityDT = activities.get(i).getStart_date().split("T");
			int startIndex = getIndex(format, startDate+"T"+minTime, activityDT[0]+"T"+minTime, activities.get(i).getStart_date(), timeSteps);
			int endIndex = getIndex(format, startDate+"T"+minTime,activityDT[0]+"T"+minTime, activities.get(i).getEnd_date(), timeSteps);
			for(int j = startIndex; j<endIndex; j++){
				if(schedule[j]<1){
					schedule[j]++;
				}
			}
		}
		
		System.out.println(generateSchedString(schedule));
	}
	
	protected String generateSchedString(int[] a){
		StringBuilder sb = new StringBuilder();
		for(int i : a){
			sb.append(i);
		}
		return sb.toString();
	}
	
	protected int getIndex(SimpleDateFormat format, String baseStart, String activityStart, String activityEnd, int totalTimeSteps) {
		int steps = 0, days = 0;
		try {
			Date baseDate = format.parse(baseStart);
			Date startDate = format.parse(activityStart);
			Date endDate = format.parse(activityEnd);
			long diffSteps = endDate.getTime() - startDate.getTime();
			long diffBase = endDate.getTime() - baseDate.getTime();
			int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diffSteps);
			steps = minutes/30;
			days = (int) TimeUnit.MILLISECONDS.toDays(diffBase);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return (days*totalTimeSteps) + steps;
	}
}
