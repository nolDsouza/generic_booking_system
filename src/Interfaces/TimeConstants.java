package Interfaces;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;

public interface TimeConstants {
	
	Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:00");
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    int monthOfYear = cal.get(Calendar.MONTH);
    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    int year = cal.get(Calendar.YEAR);
    
    public static String getNow() {
    	return toString(dayOfMonth, monthOfYear+1, year);
    }
    
    public static String toString(int d, int m, int y) {
    	return String.format("%02d/%02d/%04d", m, d, y);
    }
    
    public static String increment(String date) {
    	String[] parts = date.split("/");
    	int day = Integer.parseInt(parts[1]);
    	int month = Integer.parseInt(parts[0]);
    	int year = Integer.parseInt(parts[2]);
    	
    	if (day != getMaximum(Month.values()[month-1])) {
    		day += 1;
    	} 
    	else if (month != 12) {
    		day = 1;
    		month += 1;
    	}
    	else {
    		day = 1;
    		month = 1;
    		year += 1;
    	}
    	return String.format("%02d/%02d/%04d", month, day, year);
    }
    
    public static int getMaximum(Month month) {
		switch (month) {
			case JANUARY:
			case MARCH:
			case MAY:
			case JULY:
			case AUGUST:
			case OCTOBER:
			case DECEMBER:	
				return 31;
			case APRIL:
			case JUNE:
			case SEPTEMBER:
			case NOVEMBER:
				return 30;
			default : if (year%4 == 0) return 29;
			return 28;
		}
		
	}
}
