package NWC.model;

public class WorkingTime {
	private int month;
	private int daysAhead;
	private AppTime appointment;
	private String workerID;
	private String iD;
	private boolean covered;
	private Database database = Origin.getInstance().db;
	
	public WorkingTime(int month, int day, AppTime aT, String wID) {
		this.month = month;
		this.daysAhead = day;
		this.appointment = aT;
		this.workerID = wID; 
		this.iD = String.format("%s%02d", wID.substring(0, 3), daysAhead);
		covered = false;
	}
	
	public WorkingTime(String dbString) {
		int[] buffer = new int[8];
		String[] delims = dbString.split("#");
		this.iD = delims[0];
		this.month = Integer.parseInt(delims[1]);
		this.daysAhead = Integer.parseInt(delims[2]);
		
		String[] times = delims[3].split(":");
		this.appointment = new AppTime(
				Integer.parseInt(times[0]), Integer.parseInt(times[1].substring(
						1+times[1].indexOf("-"), times[1].length())));
		
		this.workerID = delims[4];
		for (int i=1; i<=8; i++) {
			buffer[i-1] = Character.getNumericValue(delims[5].charAt(i));
		}
		appointment.setBuffer(buffer);
	}
	
	public AppTime getApp() {
		return this.appointment;
	}
	
	public String getWorkerID() {
		return workerID;
	}
	
	public String toString() {
		String tempString = String.format("%s - %s", 
				workerID, appointment.toString());
		return tempString;
	}
	
	public void update() {
		System.out.println(appointment.bufferTS());
		database.updateWorkingTimes(appointment.bufferTS(), iD);
	}
	public boolean insert() {
		return database.insertWorkingTime(iD, month, daysAhead, 
				appointment.toString(), appointment.bufferTS(), workerID);
	}
	
	public boolean isCovered() {
		return covered;
	}
	
	public void cover() {
		covered = true;
	}

}
