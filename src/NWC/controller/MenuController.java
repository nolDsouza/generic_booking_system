package NWC.controller;

import Interfaces.SourceManipulation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuController 
extends Controller {

	@FXML private Label greeting = new Label();
	@FXML private VBox businessOperations = new VBox();
	@FXML private Button clientNewBooking = new Button();
	@FXML private Button checkBookingAvailability = new Button();
	
	@FXML
    void initialize() {
		String iD = SourceManipulation.key.toString();
		String name = database.getName(iD);
		String tempString = String.format("Welcome %s!", name);
		greeting.setText(tempString);
		checkBookingAvailability.setVisible(false);
//		try{
//			Log menu_log = new Log();
//			doLog(name + " logged in", menu_log);
//
//		}catch( Exception e){
//			e.printStackTrace();
//		}
		if (administrator.authenticate(iD)) authorise();
	}	
	
	public void authorise() {
		businessOperations.setVisible(true);
		clientNewBooking.setVisible(false);
		checkBookingAvailability.setVisible(true);
	}
	
	@FXML
	public void checkAvailClicked(ActionEvent event) 
	{
		administrator.changeScene(event, BOOK);
	}
	
	@FXML
	public void addEmployeeClicked(ActionEvent event)
	{
		administrator.changeScene(event, ADD_EMPLOYEE);
	}
	@FXML
	public void addWorkingDates(ActionEvent event)
	{
		administrator.changeScene(event, CHOOSE_EMPLOYEE, ROSTER);
	}
	@FXML
	public void showWorkingDays(ActionEvent event)
	{
		administrator.changeScene(event, CHOOSE_EMPLOYEE, VIEW_ROSTER);
	}
	
	@FXML
    private void newBookingClicked(ActionEvent event) 
	{
		administrator.changeScene(event, CALL_BOOK);
	}
	
	@FXML
	private void viewBookSum(ActionEvent event)
	{
		administrator.changeScene(event, SUMMARISE);
	}
	
	@FXML
	private void addActivity(ActionEvent event)
	{
		administrator.changeScene(event, ACTIVITY);
	}
	
	@FXML
	private void clientNewBooking(ActionEvent event)
	{
		administrator.changeScene(event, BOOK);
	}
	
	@FXML //Logging out from menu 
	public void logout(ActionEvent event)
	{	 
		SourceManipulation.key.setLength(0); //Resetting the login id
		administrator.unFreeze();
		administrator.changeScene(event, LOGIN);
	}
}
