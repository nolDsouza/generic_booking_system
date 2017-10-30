package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import NWC.model.Day;

public class DayTest {

	@Test
	public void test1() {
		Day day = new Day(9, 15);
		assertEquals(day.toString(), "9-15");
	}
	
	@Test
	public void test2() {
		Day day = new Day(9, 15);
		assertEquals(day.getStart(), 9);
	}
	
	@Test
	public void test3() {
		Day day = new Day(9, 17);
		assertEquals(day.hoursOpen(), 8);
	}
	
}
