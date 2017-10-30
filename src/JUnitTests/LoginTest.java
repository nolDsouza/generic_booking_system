package JUnitTests;

import static org.junit.Assert.*;
import org.junit.Test;

import NWC.model.Database;

public class LoginTest {

	Database db = new Database("jdbc:sqlite:NWC.db");
	
	
	@Test
	public void test1() {
		String result = db.login(null, null);
		assertEquals(null, result);
		// hard coded assertion will determine a pass or fail
	}
	

	@Test
	public void test2() {
		String result = db.login("     ", "");
		assertEquals(null, result);
	}
	
	@Test
	public void test3() {
		String result = db.login("username", null);
		assertEquals(null, result);
	}

	@Test
	public void test4() {
		String result = db.login("username", "password");
		assertEquals(null, result);
	}
	
	
	@Test
	public void test5() {
		String result = db.login("eb", "123");
		assertEquals("eb11111", result);
	}
}