package JUnitTests;

import java.util.ArrayList;

import org.junit.Test;

import Interfaces.TimeConstants;
import NWC.model.Booking;
import NWC.model.Origin;

public class OriginDebugs {
	Origin origin = Origin.getInstance();
	
	/*@Test
	public void printActivityIntervals() {
		HashMap<String, Integer> activityIntervals = origin.activityIntervals;
		System.err.println("Activity intervals:");
		
		for (Entry<String, Integer> entry : activityIntervals.entrySet()) {
			System.out.println(entry.getKey() + " takes " + entry.getValue() + " hour/s");
		}
	}
	
	@Test
	public void printEmployeeWorkingTimes() {
		HashMap<String, ArrayList<WT>> workingTimes = origin.db.getStaff();
		System.err.println("\n\nWorking times:");
		
		//System.out.println(workingTimes.get("a"));
		for (Entry<String, ArrayList<WT>> entry : workingTimes.entrySet()) {
			System.err.println(entry.getKey() + ":");
			for (WT i : entry.getValue()) {
				System.out.println(i.getWorkingTimes());
			}
		}
	}

	@Test
	public void addEmployeeWorkingTimes() {
		HashMap<String, ArrayList<WT>> workingTimes = origin.db.getStaff();
		System.err.println("\n\nWorking times:");
		
		ArrayList<WT> finalList = new ArrayList<WT>();
		//System.out.println(workingTimes.get("a"));
		
		for (Entry<String, ArrayList<WT>> entry : workingTimes.entrySet()) {
			
			if (!entry.getValue().isEmpty() && finalList.isEmpty()) {
				System.out.println("content for " + entry.getKey());
				finalList.addAll(entry.getValue());
				continue;
			}
			if (!entry.getValue().isEmpty()) {
				for (int i=0; i<finalList.size(); i++) {
					if (!finalList.get(i).full()) {
						StringBuilder temp = entry.getValue().get(i).getWorkingTimes();
						finalList.get(i).combine(temp);
					} else System.err.println("full");
				}
			}
			
		}
		System.out.println("\n\nfinally:");
		for (WT key : finalList) {
			System.out.println(key.getWorkingTimes());
		}
	}
	
	@Test
	public void printEmployeeSpecializations() {
		HashMap<String, HashSet<String>> specialisations = origin.db.getSpecs();
		System.out.println("Specialisations:");
		System.out.println(specialisations);
	}
	
	@Test
	public void getActivities() {
		HashSet<String> employees = Origin.getInstance().specialisations.get("Bashing");
		HashMap<String, ArrayList<WT>> workingTimes = origin.getInstance().db.getStaff();
		ArrayList<WT> wt = new ArrayList<WT>();
		
		System.out.println("employees:");
		for (String key : employees) {
			System.out.println(key + ":");
			wt = workingTimes.get(key);
			for (WT item : wt) {
				System.out.println(item.getWorkingTimes());
			}
		}			
	}
	*/
	@Test
	public void printClient() {
		ArrayList<Booking> bookings = origin.db.selectClientsBookings("DDC4076");

		for (Booking key : bookings) {
			System.out.println(key.toString());
		}
		String now = TimeConstants.getNow();
		
		for (Booking key : bookings) {
			for (int i=0; i<28; i++) {
				if (key.getDate().equals(now)) {
					System.out.println("YES!! For : " + key.toString());
					break;
				}
				now = TimeConstants.increment(now);
			}
			now = TimeConstants.getNow();
		}
	}
}
