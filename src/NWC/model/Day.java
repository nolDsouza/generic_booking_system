package NWC.model;

import Interfaces.TimeConstants;

public class Day 
implements TimeConstants {
	
	private int start;
	private int end;
	
	public Day(int start, int end){
		this.start = start;
		this.end = end;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int hoursOpen(){
		return end-start;
	}
	
	public int next(int hour) {
		if (hour == 24) {
			return 1;
		}
		return ++hour;
	}
	
	public String toString() {
		return String.format("%d-%d", start, end);
	}
	
	public String fullString() {
		return String.format("%d/%d/%d:%s", dayOfMonth, monthOfYear, year, toString());
	}
	
	/* Is able to return the AM and PM
	 * String.
	 */
	
	public String[] AMPM(){
		
		String[] AMPM = new String[hoursOpen()];
		
		int tempStart = start;
		
		for(int i = 0; i < AMPM.length; i++){
			if(tempStart < 12){
				AMPM[i] = "AM";
			} else {
				AMPM[i] = "PM";
			}
			
			tempStart++;
		}
		
		
		return AMPM;
		
		
	}

}
