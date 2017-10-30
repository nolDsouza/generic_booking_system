package JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import NWC.model.WT;

public class WTTests {

	@Test
	public void test0() {
		StringBuilder a = new StringBuilder().append(10100100);
		assertTrue(a.charAt(0) == '1');
	}
	
	@Test
	public void test1() {
		StringBuilder a = new StringBuilder().append("0000");
		StringBuilder b = new StringBuilder().append(1111);
		
		WT workingTime = new WT(null, null, a); 
		workingTime.combine(b);
		assertEquals("1111", workingTime.getWorkingTimes().toString());
	}
	
	@Test
	public void test2() {
		StringBuilder a = new StringBuilder().append(10100100);
		StringBuilder b = new StringBuilder().append(10111111);
		
		WT workingTime = new WT(null, null, a); 
		workingTime.combine(b);
		assertEquals("10111111", workingTime.getWorkingTimes().toString());
	}
	
	@Test
	public void test3() {							  
		StringBuilder a = new StringBuilder().append("100001111100001101010101110001001101001110110");
		StringBuilder b = new StringBuilder().append("011110000011110010101010001110110010110001000");
		
		WT workingTime = new WT(null, null, a); 
		workingTime.combine(b);
		assertEquals("111111111111111111111111111111111111111111110", workingTime.getWorkingTimes().toString());
	}
	
	@Test
	public void test4() {
		StringBuilder a = new StringBuilder().append(110011010);
		StringBuilder b = new StringBuilder().append(111100111);
		WT workingTime = new WT(null, null, a); 
		
		assertFalse(workingTime.full());
		workingTime.combine(b);
		assertTrue(workingTime.full());
	}
	
	@Test
	public void test5() {
		int n = 3, count = n, head = 0, flag = 0, innerHead;
		StringBuilder sb = new StringBuilder().append("101101110101011111100777010");
		
		for (int i=0; i<sb.length(); i++) {
			if (sb.charAt(i)=='7') {
				sb.setCharAt(i, (char) '0');	
			}
		}
		
		
		while (head < sb.length()) {
			if (sb.charAt(head) == '0') {
				head++;
			}
			else {
				flag = head;
				while (head < sb.length() && sb.charAt(head)=='1') {					
					head++;
					count--;
				}
				if (count > 0) {
					for (int i=flag; i<head; i++) {
						sb.setCharAt(i, (char) '0');
					}
				}
			}
			count = n;
		}

		System.out.println(sb.toString());
	}

}
