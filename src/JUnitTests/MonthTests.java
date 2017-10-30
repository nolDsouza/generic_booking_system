package JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Test;

import Interfaces.TimeConstants;

public class MonthTests 
implements TimeConstants {

	@Test
	public void test1() {
		assertTrue(TimeConstants.getMaximum(Month.FEBRUARY) == 28
				|| TimeConstants.getMaximum(Month.FEBRUARY) == 29);
	}
	
	@Test
	public void test2() {
		assertEquals(31, TimeConstants.getMaximum(Month.JANUARY));
	}
	
	@Test
	public void test3() {
		assertEquals(30, TimeConstants.getMaximum(Month.SEPTEMBER));
	}
	
	@Test
	public void test4() {
		String date = "09/13/1997";
		date = TimeConstants.increment(date);
		assertEquals("09/14/1997", date);
	}
	
	@Test
	public void test5() {
		String date = "02/28/1997";
		date = TimeConstants.increment(date);
		assertEquals("03/01/1997", date);
	}
	
	@Test
	public void test6() {
		String date = "12/31/2017";
		date = TimeConstants.increment(date);
		assertEquals("01/01/2018", date);
	}
	
	@Test
	public void test7() {
		String date = "8/7/8";
		date = TimeConstants.increment(date);
		assertEquals("08/08/0008", date);
	}

}
