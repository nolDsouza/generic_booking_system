package NWC.model;

public abstract class User {
	
	protected String address;
	protected String phone;
	protected String ID;
	protected String name;
	
	public User(String name, String address, String phone) {
		setUser(name, address, phone);
	}
	
	public void setUser(String name, String address, String phone) {
		setName(name);
		setAddress(address);
		setPhone(phone);		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getID() {
		return ID;
	}

}
