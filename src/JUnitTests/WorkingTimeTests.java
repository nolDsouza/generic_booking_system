package JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import NWC.model.AppTime;
import NWC.model.WorkingTime;

public class WorkingTimeTests {

	@Test public void test1() {
		String w = "New01#0#0#10:00-18:00#test#[11111000]";
		WorkingTime wT = new WorkingTime(w);
		assertTrue(wT.getWorkerID().equals("test"));
		assertTrue(wT.getApp().bufferTS().equals("[11111000]"));
	}
	
	@Test public void test2() {
		String w = "New01#0#0#10:00-18:00#test#[11111111]";
		WorkingTime wT = new WorkingTime(w);
		assertTrue(wT.getApp().isFull());
	}
	
	@Test public void test3() {
		String w = "New01#0#0#10:00-18:00#john#[11111000]";
		WorkingTime wT = new WorkingTime(w);
		assertEquals(wT.toString(), "john - 10:00-18:00");
	}
	
	@Test public void test4() {
		int[] b = {1,1,1,1,0,0,0,0};
		AppTime a = new AppTime(b);
		WorkingTime wT = new WorkingTime(1,1,a,"New01");
		assertFalse(wT.isCovered());
	}
	

}
