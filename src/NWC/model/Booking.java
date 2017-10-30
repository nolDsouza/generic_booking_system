package NWC.model;

public class Booking {
	private int month;
	private int day;
	private Day times;
	private String date;
	private String clientID;
	private String workerID;
	private String startTime;
	private String endTime;
	private String activity;
	private Database database = Origin.getInstance().db;
	
	public Booking(String cID, Day day, String activity, String date) {
		this.clientID = database.getID(cID);
		this.times = day;
		this.date = date;
		this.activity = activity;
	}
	
	
	public Booking(String cID, int month, int day, String startTime, String endTime) {
		this.clientID = database.getID(cID);
		this.month = month;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public void setEmployee(String wID) {
		this.workerID = wID;
	}
	
	public void insert() {
		database.insertBooking(clientID, times.getStart(),
				times.getEnd(), date, activity, workerID);
	}
	
	public void insert(boolean owner) {
		database.insertBooking(String.valueOf(month), String.valueOf(day),
				String.format("%s-%s", startTime, endTime),
				clientID, workerID);
	}
	
	public String toString() {
		return String.format("%s#%s:%s#%s#%s", clientID, date, times.toString(), activity, workerID);
	}
	
	public String getTimes() {
		return times.toString();
	}
	
	public Day getDay() {
		return this.times;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getActivity() {
		return this.activity;
	}
}
