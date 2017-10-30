package Interfaces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import NWC.model.Day;


public class OpeningHours {
	
	/* Will be used later in development for PART C SEPT
	 * 
	 */
	
	private Day monday = null;
	private  Day tuesday = null;
	private  Day wednesday = null;
	private  Day thursday = null;
	private  Day friday = null;
	private  Day saturday = null;
	private Day sunday = null;
	
	private final int  DAYSOFWEEK = 7;
	
	public int MONTHSLOTS;
	private Day[] scheduele = new Day[7];
	private Schematic schematic;
	
	public class Schematic {
		int[] startTimes = new int[7];
		int[] openingHours = new int[7];
		int earliest = 24;
		int latest = 0;
		
		public Schematic() {
			for (int i=0; i<7; i++) {
				startTimes[i] = scheduele[i].getStart();
				if (earliest > scheduele[i].getStart()) {
					earliest = scheduele[i].getStart();
				}
				
				openingHours[i] = scheduele[i].hoursOpen();
				if (latest < scheduele[i].getEnd()) {
					latest = scheduele[i].getEnd();
				}
			}
		}
		
		public int getEarliest() {
			return earliest;
		}
		
		public int getLatest() {
			return latest;
		}
		
		public int[] getStartTimes() {
			return startTimes;
		}
		
		public int[] getWorkingTimes() {
			return openingHours;
		}
	}
	
	public OpeningHours() {
		try {
			scheduele = readTextFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		schematic = new Schematic();
		
		for(int i = 0; i < scheduele.length; i++){
			
			MONTHSLOTS +=scheduele[i].hoursOpen();
			
		}
		
		MONTHSLOTS *= 4;
		
	}
	
	public Day[] readTextFile() throws FileNotFoundException, IOException{
		
		Day[] d = new Day[DAYSOFWEEK];
		
		try(BufferedReader br = new BufferedReader(new FileReader("src/openingTimes.txt"))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    
		    int lineNo = 0;
		    
		    while (lineNo < DAYSOFWEEK) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        
		        
		        d[lineNo] = delimit(line);
		        line = br.readLine();
		        //System.out.println("LINE " + line);
		        lineNo++;
		    }
		    String everything = sb.toString();
		    //System.out.println(everything);
		}
		
		return d;
		
	}
	
	public Day delimit(String line){
		
		Day d = null;
		
		if(line != null | line != ""){
			
			if(line == null){
				
			}else {
				//System.out.println("IN IF " + line);
				String[] parts = line.split("-");
				int part1 = Integer.parseInt(parts[0]);
				int part2 = Integer.parseInt(parts[1]);
				
				
				
				Day tempDay = new Day(part1, part2);
				
				d = tempDay;
				
			}
		}
		
		return d;
	}
	
	public Day[] getScheduele() {
		return this.scheduele;
	}
	
	public Schematic getSchematic() {
		return this.schematic;
	}

}
