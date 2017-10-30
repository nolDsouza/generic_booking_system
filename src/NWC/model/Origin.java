package NWC.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class Origin {
	private static final Origin ORIGIN  = new Origin();
	
	public boolean isFrozen;
	public User user;
	public Booking newBooking;
	
	public final Database db = new Database("jdbc:sqlite:NWC.db");
	// Activity -> key, Integer -> Number of Hours
	public final HashMap<String, Integer> activityIntervals = db.getFromActivities();
	//public final TreeMap<String, StringBuilder> workingTimes = db.mapWorkingTimes();
	// Employee name -> key, each employee has a list of working times
	public final HashMap<String, ArrayList<WT>> workingTimes = db.getStaff();
	// Activity -> key, each activity has a set of employees
	public final HashMap<String, HashSet<String>> specialisations = db.getSpecs();
	
	
	private Origin() {}
	
	public static Origin getInstance() {
		return ORIGIN;
	}
}
