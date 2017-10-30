package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import NWC.controller.RegController;
import NWC.model.Client;
import NWC.model.Database;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart.Data;
import junit.framework.Assert;

public class RegTest {
	
	Database testData = new Database("jdbc:sqlite:NWC.db");
	RegController register = new RegController();
	Client testClient = new Client("name", "address", "username", "password", "123456");
	ActionEvent event = new ActionEvent();
	
	
	@Test
	public void checkForNumbers()
	{
		String regex = "\\d+";
		assertTrue("is a phone number", testClient.getPhone().matches(regex));
	}

	@Test
	public void IDMatches()
	{
		String regex = "^DDC\\d{4}";
		assertTrue(testData.createID().matches(regex));
	}
	
	@Test 
	public void addsToDatabase()
	{
		assertTrue(testData.addClientDatabase(testClient) == true);
	}
	
	@Test
	public void checkName()
	{
		assertFalse(testClient.getName().equals(" "));
	}
	
	@Test
	public void validUsername()
	{
		equals(testData.checkForDuplicate("customerinfo", "username", testClient.getUsername()));
	}
}

