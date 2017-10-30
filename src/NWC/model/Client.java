package NWC.model;

import java.util.ArrayList;
import java.util.List;

// Making our model class for our client

public class Client extends User {
	
	private String username;
	private String password;
	private List<Booking> bookings = new ArrayList<Booking>();
	
	public Client(String name, String address, String username, String password, String phone) {
		super(name, address, phone);
		this.username = username;
		this.password = password;
	}
	
	public void setBookings(ArrayList<Booking> dbList) {
		bookings.addAll(dbList);
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}
