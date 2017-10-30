package NWC.model;
import java.util.ArrayList;
import java.util.Iterator;

import Interfaces.OpeningHours;
import Interfaces.TimeConstants;
import javafx.beans.property.SimpleIntegerProperty;

public class PersonalTrainer {
	
	private final String name;
	
	/* Working times is a 2D array which will be populated using the database it will house all the hours they will work for a week.
	 * When the owner updates the timetable view they will be added to this array locally they pushed to the database. 
	 * 
	 */
	private int[] workingTimes;
	private ArrayList<WT> workingDays = new ArrayList<WT>();
	
	private Database database = Origin.getInstance().db;
	
	public SimpleIntegerProperty Monday = new SimpleIntegerProperty();

	public PersonalTrainer(String name) {
		super();
		this.name = name;
		
		OpeningHours op = new OpeningHours();
		
		workingTimes = new int[op.MONTHSLOTS];
		System.out.println(op.MONTHSLOTS);
		
		String workingTimesString = database.getWorkingTimes(getName());
		if(workingTimesString.length() < workingTimes.length){
			int dif = workingTimes.length - workingTimesString.length();
			
			for(int i = 0; i < dif; i++){
				workingTimesString += "0";
			}
		}
		System.out.println(workingTimesString);
		
		if (workingTimesString.equals("0"))
		{
			for (int i = 0; i < workingTimes.length; i++)
			{
				workingTimes[i] = 0;
			}
		} else {
			
			for (int i = 0; i < workingTimes.length; i++)
			{
				
				workingTimes[i] = Character.getNumericValue(workingTimesString.charAt(i));
				
			}
		}
		
		processTimes(workingTimes);
	}

	public Integer getMonday() {
		return Monday.get();
	}

	public void setMonday(Integer monday) {
		Monday.add(monday);
	}

	public int[] getWorkingTimes() {
		return workingTimes;
	}
	
	public ArrayList<WT> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingTimes(int time, int se) {
		this.workingTimes[se] = time;
	}
	
	public void setFinalWorkingTimes(int[] WT) {
		this.workingTimes = WT;
	}

	public String getName() {
		return name;
	}
	
	public void updateWorkingTimes()
	{
		StringBuilder strNum = new StringBuilder();

		for (int num : getWorkingTimes()) 
		{
		     strNum.append(num);
		}
		//long finalInt = Long.parseLong(strNum.toString());
		processTimes(workingTimes);
		System.out.println(strNum);
		
		database.addWorkingTimes(strNum, getName());
		database.clearTimes(name);
		System.out.println("debug");
		insertWorkingDays();
	}
	
	public void processTimes(int[] workingTimes) {
		
		int day = TimeConstants.dayOfMonth-1;
		int month = TimeConstants.monthOfYear;
		int y = TimeConstants.year;
		
		Day[] scheduele = new OpeningHours().getScheduele();
		StringBuilder workingTime = new StringBuilder();
		String date = TimeConstants.toString(day, month+1, y);
		int count = 0;
		
		workingDays.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				date = TimeConstants.increment(date);
				for (int k = 0; k < scheduele[j].hoursOpen(); k++) {
					workingTime.append(workingTimes[count]);
					count++;
				}
				WT wt = new WT(date, name, workingTime);
				workingDays.add(wt);
				//database.insertWorkingDay(wt);
				workingTime.setLength(0);
			}
		}
	}
	
	public void insertWorkingDays() {
		database.clearDays(name);
		for (Iterator<WT> i = workingDays.iterator(); i.hasNext();) {
		    WT item = i.next();
		    database.insertWorkingDay(item);
		}
	}
	
	public void applyTimes(int start, int hours) {
		
		for (int head = 0; head < hours; head++) {
			workingTimes[start+head] = 7;
		}
		
		updateWorkingTimes();
	}
	

}
