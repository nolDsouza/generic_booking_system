package JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Interfaces.SourceManipulation;
import NWC.model.Administrator;
import NWC.model.Database;
import NWC.model.Origin;

public class MenuTests implements SourceManipulation {

	Administrator administrator = new Administrator();
	private Database database = Origin.getInstance().db;

	
	@Test
	public void getName() {
		key.append(database.login("eb", "123"));
		assertEquals("Auto Moderator", database.getName(key.toString()));
		key.setLength(0);
	}
	
	@Test
	public void authenticateOwnerSuccess() {
		key.append(database.login("eb", "123"));
		assertTrue(administrator.authenticate(key.toString()));
		key.setLength(0);
	}
	
	@Test
	public void authenticateOwnerFail() {
		key.append(database.login("john", "doe"));
		assertFalse(administrator.authenticate(key.toString()));
		key.setLength(0);
	}

	@Override
	public void setKey(String iD) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUser(boolean authorised) {
		// TODO Auto-generated method stub
		
	}

}
