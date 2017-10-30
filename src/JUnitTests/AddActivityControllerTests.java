package JUnitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import NWC.controller.AddActivityController;

public class AddActivityControllerTests {
	
	AddActivityController AC = new AddActivityController();

	@Test
	public void timeSlotIndex() {
		
		/*
		 * Out of range index MAX is 3
		 */
		assertFalse(AC.addActivity(5, "Rowing"));
		
		/*A 3 hour time slot, given that there is no 
		 * rowing in the database it will pass.
		 */
		assertTrue(AC.addActivity(3, "Rowing"));
		
		
	}
	
	@Test
	public void invalidNameSpaces() {
		
		/*
		 * Out of range index MAX is 3
		 */
		assertFalse(AC.addActivity(3, "Rowi ng"));
		
		/*A 3 hour time slot
		 * 
		 */
		assertFalse(AC.addActivity(3, "Rowing  Hello"));
	}
	
	@Test
	public void invalidNameRegex() {
		
		/*
		 * Out of range index MAX is 3
		 */
		assertFalse(AC.addActivity(3, "3535Rowing"));
		
		/*A 3 hour time slot, given that there is no 
		 * rowing in the database it will pass.
		 */
		assertFalse(AC.addActivity(3, "...Rowing"));
	}
	
	@Test
	public void noTimeSlotEntered() {
		

		/* -1 is for no input 
		 * 
		 */
		
		assertFalse(AC.addActivity(-1, "Rowing"));
		
		assertFalse(AC.addActivity(-1, "Rowing654"));
	}

}
