package JUnitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import NWC.model.Origin;
import NWC.model.Owner;

public class OwnerTests {
	ArrayList<String> a = Origin.getInstance().db.getAllActivities();
	Owner owner = new Owner("Jim", "Beam", "J");

	@Test 
	public void nameMissing() {
		
		assertFalse("Name Missing 1", owner.addEmployee(null, "001", a));
		
		assertFalse("Empty String",owner.addEmployee(" ", "001", a));
		
		assertTrue("Name with leading spaces", owner.addEmployee("John8", "001", a));
		
		
	}
	
	@Test // Name needs to be less then 18 char // false for 18 char // true for 17
	public void nameTooLong(){
		
		assertFalse(owner.addEmployee("jjjjjjjjjjjjjjjjjjjj", "001", a));
		
		assertFalse(owner.addEmployee("              ", "001", a));
		
	}
	
	@Test //Names less then 2 char are false
	public void nameTooShort() {
		
		assertTrue(owner.addEmployee("Li", "001", a));
		
		assertFalse(owner.addEmployee("L", "001", a));
	}
	
	@Test // Leading or trailing Spaces with char greater then 18 or lower then 2
	public void nameWithLeadingSpaces(){
		
		assertTrue(owner.addEmployee("John Doe6", "001", a));
		
		assertFalse(owner.addEmployee("  John Doehhhhhhhhhhhhhhhhhhhhhhhhhhh             hhhhhhhhh    ", "001", a));
		
	}
	
	@Test // Make sure "John Doe" is pre entered in the database
	public void duplicateName() {
		
		assertFalse(owner.addEmployee("John Doe", "001", a));
		
	}

}
