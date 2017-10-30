package NWC.model;

import Interfaces.OpeningHours;
import Interfaces.OpeningHours.Schematic;

public class WT {
	private String date;
	private String time;
	private String workerID;
	private Schematic schematic = new OpeningHours().getSchematic();
	private StringBuilder workingTimes = new StringBuilder();
	
	public WT(String date, String id, StringBuilder workingTimes) {
		this.date = date;
		this.workerID = id;
		this.workingTimes.append(workingTimes);
		
		//System.out.println(String.format("%s:%s:%s", date, id, workingTimes));
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getwID() {
		return this.workerID;
	}
	
	public StringBuilder getWorkingTimes() {
		return this.workingTimes;
	}
	
	public void combine(StringBuilder workingTime) {
		for (int i=0; i<workingTime.length(); i++) {
			if (workingTime.charAt(i) == '1') {
				this.workingTimes.setCharAt(i, '1');
			}
		}
	}
	
	public boolean full() {
		for (int i=0; i<workingTimes.length(); i++) {
			if (workingTimes.charAt(i) == '0') {
				return false;
			}
		}
		return true;
	}
	
	public static StringBuilder refine(StringBuilder sb, int n) {
		int count = n, head = 0, flag = 0;
		
		for (int i=0; i<sb.length(); i++) {
			if (sb.charAt(i)=='7') {
				sb.setCharAt(i, '0');
			}
		}
		
		while (head < sb.length()) {
			if (sb.charAt(head) == '0') {
				head++;
			}
			else {
				flag = head;
				while (head < sb.length() && sb.charAt(head)=='1') {					
					head++;
					count--;
				}
				if (count > 0) {
					for (int i=flag; i<head; i++) {
						sb.setCharAt(i, (char) '0');
					}
				}
			}
			count = n;
		}
		System.out.println(sb.toString());
		return sb;
	}
	
	public static boolean canBeStart(StringBuilder sb, int head, int n) {

		while (head < sb.length() && sb.charAt(head)=='1') {
			n--;
			head++;
		}
		
		if (n <= 0) {
			return true;
		}
		return false;
	}
	
}