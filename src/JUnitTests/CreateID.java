package JUnitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import NWC.model.Database;
import NWC.model.Origin;

public class CreateID {

	private static int count = 0;
	private Database database = Origin.getInstance().db;
	
	@Test
	public void test1() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test2() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test3() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test4() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test5() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test6() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test7() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test8() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test9() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}
	
	@Test
	public void test10() {
		String regex = "^DDC\\d{4}";
		String newID = database.createID();
		System.out.println(++count + "." + newID);
		assertTrue(newID.matches(regex));
	}

	
}
