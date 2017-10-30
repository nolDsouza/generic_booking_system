package JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Interfaces.OpeningHours;
import Interfaces.OpeningHours.Schematic;
import NWC.model.Day;

public class OpeningHoursTests {

	@Test
	public void test1() {
		OpeningHours op = new OpeningHours();
		Day[] days = op.getScheduele();
		String tempString = "";
		int total = 0;
		
		tempString += days[0].hoursOpen();
		total += days[0].hoursOpen();
		for (int i=1; i<7; i++) {
			tempString += String.format("+%d", days[i].hoursOpen());
			total += days[i].hoursOpen();
		}
		
		assertEquals("8+8+8+8+8+8+8", tempString);
		assertTrue(total == 56);
	}
	
	@Test
	public void test2() {
		OpeningHours op = new OpeningHours();
		Schematic schematic = op.getSchematic();
		int start = schematic.getEarliest();
		int end = schematic.getLatest();
		for (int i=start; i<end; i++) {
			System.out.printf("%d-%d\n", i, i+1);
		}
	}

}
