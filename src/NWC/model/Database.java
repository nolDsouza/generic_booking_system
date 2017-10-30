package NWC.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;

public class Database {
	private String url = "jdbc:sqlite:NWC.db";
	private Connection connection;
	/* Database login Structure follows |ID|Username|Password
	 * Can return the ID from login function if correct
	 * Username and Password is given... use ID as key for account
	 * For now, edit the database in SQL if it is required, 
	 * You can view what is in the accounts table with selectAccounts()
	 */

	public Database(String url) {
		if (!connect(url)) {
			System.out.println("Warning: could not connect to NWC databse");
		}
	}
	public boolean connect(String url) {
        // SQLite connection string
        connection = null;
        try {
            connection = DriverManager.getConnection(url);
            
            DatabaseMetaData metaData = connection.getMetaData();
            String checkUrl = metaData.getURL();
            
            if (checkUrl.substring(checkUrl.lastIndexOf("/") + 1).equals(this.url)) {
            	return true;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
	}
	
	public String login(String username, String password) {
		
		String sql = "SELECT ID FROM accounts "
				+ "WHERE username = ?"
				+ "AND password = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);   
	
		    statement.setString(1, username);
		    statement.setString(2, password);
		    ResultSet resultSet = statement.executeQuery();

		    statement.clearParameters();
		    
		    if (resultSet.next()) {
		    	String result = resultSet.getString("ID");
		    	return result;
		    }
		    
		} catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
		return null;
	}
	
	/*public User (String iD, boolean authorised) {
		String sql = "SELECT * FROM ?";
		PreparedStatement statement = null;
	}*/
	
	public String getID(String name) {
		String sql = "SELECT ID FROM customerinfo WHERE name = ?";
				
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			statement.clearParameters();
			    
		    if (resultSet.next()) {
		    	String result = resultSet.getString("ID");
		    	return result;
		    }
		} catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
		return null;
	}
	
	public boolean deleteActivityFromE(String strNum, String name2){
		
		/* Need to get the "specialty" column from all staff and delete the index at that column
		 * 
		 */
		
		String sql = "UPDATE staff SET specialty = ? WHERE name = '"+name2+"'";
			PreparedStatement statement = null;
			
			//java.sql.Statement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, strNum);
				//statement = connection.createStatement();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			// insert the data
			try {
				statement.execute();
				//statement.executeUpdate("INSERT INTO staff VALUES ('"+name+"')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			
			return true;
	
	}
	
	public boolean deleteActivity(String name2){
		
		String sql1 = "DELETE from activities WHERE name = '"+name2+"'";
		String sql2 = "DELETE from employee_activities WHERE activity = '"+name2+"'";
		PreparedStatement statement = null;
		
		//java.sql.Statement statement = null;
		try {
			statement = connection.prepareStatement(sql1);
			statement.execute();
			statement = connection.prepareStatement(sql2);
			statement.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	public boolean addEmployeeDatabase(String name, String specialty){
			
			String sql = "INSERT INTO staff(name, workingTimes, specialty) VALUES(?,?,?)";
			PreparedStatement statement = null;
			
			//java.sql.Statement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, name);
				statement.setString(2, "0");
				statement.setString(3, specialty);
				//statement = connection.createStatement();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			// insert the data
			try {
				statement.execute();
				//statement.executeUpdate("INSERT INTO staff VALUES ('"+name+"')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			
			return true;
	}
	
	public boolean addActivities(String name, String specialty) {
		String sql = "INSERT INTO 'employee_activities' VALUES(?,?)";
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, specialty);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean addActivityDatabase(String name, int time){
		
		String sql = "INSERT INTO activities(name, time) VALUES(?,?)";
		PreparedStatement statement = null;
		
		//java.sql.Statement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, time);
			//statement = connection.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		// insert the data
		try {
			statement.execute();
			//statement.executeUpdate("INSERT INTO staff VALUES ('"+name+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	public ArrayList<String> getAllActivities() {
		String sql = "SELECT * FROM activities";
		
		ArrayList<String> activities;
			
		activities = new ArrayList<>();
		
	    try {	    
	    	Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
	    	
        
	    	while(result.next()){
	    		activities.add(result.getString("name"));
	    	}
        	
	        // loop through the result set 
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } 
		
	    return activities;
	
	}
	
	public ArrayList<String> getAllSpecialityNum() {
		String sql = "SELECT * FROM staff";
		
		ArrayList<String> activities;
			
		activities = new ArrayList<>();
		
	    try {	    
	    	Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
	    	
        
	    	while(result.next()){
	    		activities.add(result.getString("specialty"));
	    	}
        	
	        // loop through the result set 
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } 
		
	    return activities;
	
	}

	public boolean checkForDuplicate(String tableName, String name, String duplicateCheck)
	{
		
		String sql = "SELECT "+name+" FROM "+tableName;
	    
	    try {	    
	    	Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql); 
	    	
	    	
	        // loop through the result set 
	        while (result.next()) {
	        	String tempString = result.getString(name);
	        	System.out.println(tempString);
	        	if (tempString.equals(duplicateCheck))
	        	{
	        		System.out.println("Found");
	        		return false;
	        	}
	   
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } catch (NullPointerException e) {}
		
		return true;
	}
	
	public String getName(String ID) {
		String sql, column;
		System.out.println(ID);
		if (ID.charAt(0) == 'E') {
			column = "business owner name";
			sql = "SELECT * FROM business "
					+ "WHERE ID = ?";
		}
		else {
			column = "name";
			sql = "SELECT name FROM customerinfo WHERE ID = ?";
		}
		try {
			PreparedStatement statement = connection.prepareStatement(sql);   
		    statement.setString(1, ID);
		    ResultSet resultSet = statement.executeQuery();
		    statement.clearParameters();
		    
		    if (resultSet.next()) {
		    	String tempString = resultSet.getString(column);
		    	resultSet.close();
		    	return tempString;
		    }
		} catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
		return null;
		
	}
	
	// Avital's Methods 
	
	public void insertClient(String name, String address, String username, String password, String phone, String ID ) {
        String sql1 = "INSERT INTO customerinfo(ID,name,address,phone) VALUES(?,?,?,?)";
        String sql2 = "INSERT INTO accounts VALUES(?,?,?)";
        //tries to add the values from the client in the parameters to the database 
        try
        {
        	//establishes connection with database
        	PreparedStatement state = connection.prepareStatement(sql1);  
            state.setString(1, ID);
            state.setString(2, name);
            state.setString(3, address);
            state.setString(4, phone);
            state.executeUpdate();
            state = connection.prepareStatement(sql2);
            state.setString(1, ID);
            state.setString(2, username);
            state.setString(3, password);
            state.executeUpdate();
            
            state.clearParameters();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	// a method that calls insert client and make sure it adds correctly 
	public boolean addClientDatabase(Client clientData){
	
		insertClient(clientData.getName(), clientData.getAddress(),clientData.getUsername(),clientData.getPassword(), clientData.getPhone(), clientData.getID());
		System.out.println("ADDED!!!!!");
		return true;

	}
	
	//method that creates the unique ID upon registration 
	public String createID() {
		String sql = "SELECT ID FROM accounts "
				   + "WHERE ID = ? ";
		int numCode;
		Random random = new Random();
		// generate random number between 0 and 9999
		numCode = random.nextInt(10000);
		String tempString = String.format("DDC%04d", numCode);
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql); 
			statement.setString(1, tempString);
			ResultSet rs = statement.executeQuery();
			// .001% chance ID is in database and recursion required
			if (rs.next())
				return createID();
			
			return tempString;
		 } catch (Exception e) {
	            System.out.println(e.getMessage());
	     }
         return null;
		
		
	}

	public boolean addWorkingTimes(StringBuilder strNum, String name2){
		
		String sql = "UPDATE staff SET workingTimes = ? WHERE name = '"+name2+"'";
		PreparedStatement statement = null;
		
		//java.sql.Statement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, strNum.toString());
			//statement = connection.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		// insert the data
		try {
			statement.execute();
			//statement.executeUpdate("INSERT INTO staff VALUES ('"+name+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	
	
	public String getWorkingTimes(String name) {
		String sql = "SELECT * FROM staff";
		
		try {	    
	    	Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
	    	
	    	
        	do {
        		if (result.getString("name").equals(name))
	        	{
	        		
	        		return result.getString("workingTimes");
	        	}
        	}while(result.next());
	    	
	    	
	        // loop through the result set 
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } 
		
	    return "NULL";
	
	}
	
	public boolean insertBooking(String month, String day, String time, String cID, String wID) {
		String sql = "INSERT INTO bookings VALUES(?,?,?,?,?,?)";
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, createID().substring(3));
			statement.setString(2, month);
			statement.setString(3, day);
			statement.setString(4, time);
			statement.setString(5,  cID);
			statement.setString(6, wID);
			statement.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean insertBooking(String clientID, int start, int end, String date, String activity, String workerID) {
		String sql = "INSERT INTO booking VALUES(?,?,?,?,?,?)";
		PreparedStatement statement = null;
		System.out.println("debug");
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, clientID);
			statement.setInt(2, start);
			statement.setInt(3, end);
			statement.setString(4, date);
			statement.setString(5,  activity);
			statement.setString(6, workerID);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public ArrayList<Booking> selectClientsBookings(String clientID) {
		String sql = "SELECT * FROM booking WHERE client = '"+clientID+"'";
		ArrayList<Booking> bookings = new ArrayList<Booking>(); 
	    try {	    
	    	Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
	    	
	    	while(result.next())	{
	    		String client = getName(clientID);
	    		int start = result.getInt("start");
	    		int end = result.getInt("end");
	    		String date = result.getString("date");
	    		String activity = result.getString("activity");
	    		String employee = result.getString("employee");
	    		Booking booking = new Booking(client, new Day(start, end), activity, date);
	    		booking.setEmployee(employee);
	    		bookings.add(booking);
	    	}
        	
	        // loop through the result set 
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        return null;
	    } 
		for (int i=0;i<bookings.size();i++) {
			System.out.println(bookings.get(i).toString());
		}
	    return bookings;
		
	}
	
	public void clearTimes(String name) {
		String sql = "DELETE FROM workingtimes WHERE workerID = ?";
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean insertWorkingTime(String iD, int month, int day, String time, String covered, String name) {
		String sql = "INSERT INTO workingtimes VALUES(?,?,?,?,?,?)";
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, iD);
			statement.setString(2, String.valueOf(month));
			statement.setString(3, String.valueOf(day));
			statement.setString(4, time);
			statement.setString(5, covered);
			statement.setString(6, name);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ArrayList<String> tellBookingTime(String name) {
		String sql = "SELECT * FROM bookings WHERE clientID = '"+getID(name)+"'";
		
		ArrayList<String> bookings = new ArrayList<>();
		
	    try {	    
	    	Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
	    	
        
	    	while(result.next())	{
	    		String tempString = "";
	    		tempString = tempString + result.getString("month");
	    		tempString = tempString + "#" + result.getString("day");
	    		tempString = tempString + "#" + result.getString("time");
	    		tempString = tempString + "#" + result.getString("workerID");
	    		bookings.add(tempString);
	    	}
        	
	        // loop through the result set 
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } 
		for (int i=0;i<bookings.size();i++) {
			System.out.println(bookings.get(i));
		}
	    return bookings;
		
	}
	
	public ArrayList<String> getAllEmployees() {
		String sql = "SELECT * FROM staff";
		
		ArrayList<String> staffnames;
			
			staffnames = new ArrayList<>();
		
	    try {	    
	    	Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
	    	
        
	    	while(result.next()){
	    		staffnames.add(result.getString("name"));
	    	}
        	
	        // loop through the result set 
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } 
		
	    return staffnames;
	
	}
	
	public void getAllClients(ArrayList<String> clients) {
		String sql = "SELECT name FROM customerinfo";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
				
			while(rs.next()){
	    		clients.add(rs.getString("name"));
	    	}
		}  catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } 
		
	} 
	
	public ArrayList<String> getRelevantTimes(String day) {
		System.out.println(day);
		String sql = "SELECT * FROM workingtimes "
				+ "WHERE day = '"+day+"'";
		ArrayList<String> workingTimes = new ArrayList<String>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				String iD = rs.getString("ID");
				String time = rs.getString("time");
				String month = rs.getString("month");
				String name = rs.getString("workerID");
				String covered = rs.getString("covered");
				String tempString = String.format("%s#%s#%s#%s#%s#%s", iD, month, day, time, name, covered);
				workingTimes.add(tempString);
			}
			return workingTimes;
		} catch(SQLException e) {
			e.printStackTrace();
			return null; 
		}
	}
	
	public ArrayList<ArrayList<String>> getWeeksTimes(int[] span) {
		ArrayList<ArrayList<String>> times = new ArrayList<ArrayList<String>>();
		for (int i =0;i<span.length;i++) {
			times.add(getRelevantTimes(String.valueOf(span[i])));
		}
		return times;
	}
	
	public void updateWorkingTimes(String covered, String iD) {
		String sql = "UPDATE workingtimes SET covered = ? WHERE ID = ?";
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, covered);
			statement.setString(2, iD);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertWorkingDay(WT wt) {
		String sql = "INSERT INTO wt VALUES(?,?,?)";
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, wt.getDate());
			statement.setString(2, wt.getwID());
			statement.setString(3, wt.getWorkingTimes().toString());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clearDays(String name) {
		String sql = "DELETE FROM wt WHERE name = ?";
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean storeCache(String username, String password) 
	{
		String sql = "INSERT INTO cache VALUES(?,?)";
		PreparedStatement statement = null;
		try {
				clearCache();
				statement = connection.prepareStatement(sql);
				statement.setString(1, username);
				statement.setString(2, password);
				statement.execute();
			} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void clearCache() 
	{
		String sql = "DELETE FROM cache";
		PreparedStatement statement = null;
		try 
		{
			statement = connection.prepareStatement(sql);
			statement.execute();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String[] retrieveCache() 
	{
		String[] cacheData = new String[2];
		String sql = "SELECT * FROM cache";
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			if (!rs.next()) return null;
			
			else {
				cacheData[0] = rs.getString("USERNAME");
				cacheData[1] = rs.getString("PASSWORD");
			}
			
			System.out.printf("Username is %s\nPassword is %s\n", cacheData[0], cacheData[1]);
			return cacheData;
			
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			return null; 
		}	
	}
	
	public HashMap<String, Integer> getFromActivities() {
		String sql = "SELECT * FROM activities";
		HashMap<String, Integer> activityIntervals = new HashMap<String, Integer>();
		
		try {	    
			Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
		    	
	        
	    	while(result.next()){
	    		String activity = result.getString("name");
	    		int specialty = result.getInt("time");
	    		activityIntervals.put(activity, specialty);
	    	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
	        return null;
		} 
		return activityIntervals;
	}
	
	public TreeMap<String, StringBuilder> mapWorkingTimes() {
		String sql = "SELECT * FROM wt";
		TreeMap<String, StringBuilder> workingTimes = new TreeMap<String, StringBuilder>();
		
		try {	    
			Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
		    	
	        
	    	while(result.next()){
	    		String name = result.getString("name");
	    		String date = result.getString("date");
	    		String key = String.format("%s:%s", name, date);
	    		StringBuilder times = new StringBuilder().append(result.getString("times"));	
	    		/*for (int i=0; i<time.length(); i++) {
	    			buffer[i-1] = Character.getNumericValue(delims[5].charAt(i));
	    		}*/
	    		workingTimes.put(key, times);
	    		
	    	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
	        return null;
		} 
		return workingTimes;
	}
	
	public HashMap<String, ArrayList<WT>> getStaff() {
		HashMap<String, ArrayList<WT>> employees = new HashMap<String, ArrayList<WT>>();
		String sql = "SELECT DISTINCT name FROM staff";
		
		try {	    
			Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
	    	
	    	while(result.next()) {
	    		String name = result.getString("name");
	    		sql = "SELECT date, times FROM wt WHERE name = '"+name+"'";
	    		
	    		statement = connection.createStatement();
		    	ResultSet rs = statement.executeQuery(sql);
		    	ArrayList<WT> al = new ArrayList<WT>();
		    	
		    	while (rs.next()) {
		    		String date = rs.getString("date");
		    		StringBuilder times = new StringBuilder().append(rs.getString("times"));
		    		al.add(new WT(date, name, times));
		    	}
	    		employees.put(name, al);
	    	}
	    	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
	        return null;
		} 
		return employees;
	}
	
	public HashMap<String, HashSet<String>> getSpecs() {
		HashMap<String, HashSet<String>> specialisations = new HashMap<String, HashSet<String>>();
		String sql = "SELECT name FROM activities";
		
		try {
			Statement statement = connection.createStatement();
	    	ResultSet result = statement.executeQuery(sql);
	    	
	    	while (result.next()) {
	    		String activity = result.getString("name");
	    		sql = "SELECT employee FROM employee_activities WHERE activity = '"+activity+"'";
	    		
	    		statement = connection.createStatement();
		    	ResultSet rs = statement.executeQuery(sql);
		    	HashSet<String> hs = new HashSet<String>();
		    	
		    	while (rs.next()) {
		    		String name = rs.getString("employee");
		    		hs.add(name);
		    	}
		    	specialisations.put(activity, hs);
	    	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
	        return null;
		} 
		return specialisations;
	}

	
	
	

}