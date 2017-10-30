package NWC.model;

import java.io.IOException;
import java.util.ArrayList;

import Logs.Log;

public class Owner extends User {
	
	private Database database = Origin.getInstance().db;
	private Log owner_log;
	
	public Owner(String id, String address, String phone) {
		super(Origin.getInstance().db.getName(id), address, phone);
	}
	
	
	public boolean addEmployee(String name, String specialty, ArrayList<String> specialties) {
		
		try {
			owner_log = new Log();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Need to check firstly if there are trailing spaces at the beginning of the name
		
				if (name == null) {
					return false;
				}
				
				if(specialty == null){
					return false;
				}
				
				
				
				String localName = name.trim();
					
				// Make sure the user had entered a name and not an empty string. 
				
				if (localName == null){
					return false;
				}
				
				if (localName.isEmpty())
				{
					doLog("No inputted data", owner_log);
					return false;
				}
				
				// Make sure the length is between 2 and 17 no more no less
				
				if (localName.length() > 18 || localName.length() <= 1)
				{
					doLog("Too short or too long", owner_log);
					return false;
				}
				
				if(localName.charAt(0) == ' '){
					doLog("Leading White Space: INVALID", owner_log);
					return false;
				}
				
				
				if(localName.matches("^[a-zA-z]+([a-zA-z]|\\d?)$") == false)
				{
					doLog("Does Not Match Regex", owner_log);
					
					return false;
				}
				
				
				// Siphon through the current database and check if a user of the same name has been entered. No double ups
				
			
				
				boolean flag = database.checkForDuplicate("staff", "name", localName);
				
				for(int i = 0; i < localName.length(); i++){
					
					if(Character.isWhitespace(localName.charAt(i))){
						flag = false;
					}
				}
				if (flag == false)
				{
					doLog("Spaces in the string", owner_log);
					return false;
				}
				
				// Use the insert method from the database to finally add the Staff member
				
				if (database.addEmployeeDatabase(localName, specialty) == false) {
					return false;
				}
				
				for (int i=0; i<specialties.size(); i++) {
					if (!database.addActivities(localName, specialties.get(i))) {
						return false;
					}
				}
				// return true
				
				//if at any point a condition isn't met return false. 
				
				return true;
				
			}
			
		public void doLog(String message, Log log)
		{
			log.logger.info(message);
			log.closeHandler();
		}

}



