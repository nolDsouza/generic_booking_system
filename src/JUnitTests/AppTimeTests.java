package JUnitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import NWC.model.AppTime;

public class AppTimeTests {

	@Test public void test1() {
		int[] b = {0,1,1,1,1,1,1,0};
		AppTime a = new AppTime(b);
		assertTrue(a.toString().equals("11:00-17:00"));
	}
	
	@Test public void test2() {
		int[] b = {0,0,0,0,0,0,0,0};
		assertTrue(AppTime.isEmpty(b));
		b[0] = 1;
		assertFalse(AppTime.isEmpty(b));
	}
	
	@Test public void test3() {
		int[] b = {1,1,1,1,1,1,1,1};
		AppTime a = new AppTime(b);
		assertTrue(a.isFull());
		b[0] = -1;
		assertTrue(a.isFull());
		b[7] = 0;
		assertFalse(a.isFull());
	}
	
	@Test public void test4() {
		int[] b1 = {0,1,1,1,1,1,1,0};
		int[] b2 = {0,0,1,1,0,0,0,0};
		AppTime a1 = new AppTime(b1);
		AppTime a2 = new AppTime(b2);
		
		assertTrue(a2.matches(a1.buffer));
		assertFalse(a1.matches(a2.buffer));
		assertTrue(a1.matches(a1.buffer));
	}
	
	@Test public void test5() {
		AppTime a = new AppTime(10, 18);
		assertTrue(a.isFull());
		assertTrue(a.bufferTS().equals("[11111111]"));
	}
	
	@Test public void test6() {
		AppTime a = new AppTime(11, 17);
		assertTrue(a.bufferTS().equals("[01111110]"));
	}
	
	@Test public void test7() {
		AppTime a = new AppTime(14, 18);
		assertTrue(a.bufferTS().equals("[00001111]"));
	}
	
	@Test public void test8() {
		AppTime a = new AppTime(10, 11);
		assertTrue(a.bufferTS().equals("[10000000]"));
	}
	
	@Test public void test9() {
		int[] b1 = {1,1,1,1,1,1,1,1};
		int[] b2 = {0,0,0,0,0,0,0,0};
		AppTime a = new AppTime(b1);
		
		for (int i=0; i<8; i++) {
			b2[i] = 1;
			a.flip(b2);
			assertTrue(a.buffer[i] == -1);
		}
		assertTrue(a.bufferTS().equals("[-1-1-1-1-1-1-1-1]"));
	}
	
	@Test public void test10() {
		int[] b1 = {0,0,1,1,1,1,1,1};
		int[] b2 = {0,0,0,1,1,1,1,0};
		AppTime a = new AppTime(b1);
		
		a.flip(b2);
		assertTrue(a.bufferTS().equals("[001-1-1-1-11]"));
	}

	@Test public void test11() {
		int[] b1 = {0,1,1,1,1,1,1,0};
		int[] b2 = {0,0,0,1,1,1,1,1};
		int[] b3 = {1,0,0,0,0,0,0,0};
		AppTime a1 = new AppTime(b1);
		AppTime a2 = new AppTime(b2);
		AppTime a3 = new AppTime(b3);
		
		assertFalse(a1.isFull());
		a2.add(a1);
		assertTrue(a1.bufferTS().equals("[01111110]"));
		a1.add(a2);
		assertTrue(a1.bufferTS().equals("[01111111]"));
		a1.add(a3);
		assertTrue(a1.bufferTS().equals("[11111111]"));
		assertTrue(a1.isFull());
	}
}
