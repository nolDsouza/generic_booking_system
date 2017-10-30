package NWC.controller;

import java.util.ArrayList;

import Interfaces.Traversal;
import NWC.model.Owner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class AddActivityController extends Controller {
	
	/*Delete was implemented for testing purposes as if we were to
	 * delete a activity from the database we were to mess with data in
	 * other instances
	 */
	
	private Owner owner = (Owner) NWC.model.Origin.getInstance().user;
	
	ObservableList<String> choiceBoxList =  FXCollections.observableArrayList("Select Time Slot", "1 Hour", "2 Hours", "3 Hours");
	
	ObservableList<String> toDelete;
	
	@SuppressWarnings("rawtypes")
	@FXML
	public ChoiceBox choiceBox;
	
	@SuppressWarnings("rawtypes")
	@FXML
	public ChoiceBox deleteBox;
	
	@FXML public Label errorLabel2;
	
	@FXML
	public TextField nameTextbox;
	
	@FXML
	private Pane pane;
	
	@SuppressWarnings("unchecked")
	
	@FXML
	public void initialize()
	{
		/*Items to remove in a CheckBox, we need to do this
		 * so we can remove it without breaking anything in the database
		 */
		
		System.err.println("Out");
		
		toDelete = FXCollections.observableArrayList();
		
		ArrayList<String> removeStrings = database.getAllActivities();
		
		
		for(int i = 0; i < removeStrings.size(); i++){
			toDelete.add(removeStrings.get(i));
		
		}
		
		if(toDelete != null && toDelete.size() > 0){
			deleteBox.setItems(toDelete);
			deleteBox.setValue(toDelete.get(0));
		}
		
		if(toDelete != null){
			deleteBox.setItems(toDelete);
		}
		
		
		choiceBox.setItems(choiceBoxList);
		choiceBox.setValue(choiceBoxList.get(0));
	}
	
	public void timerWithErrorText(String text){
		
		/* A error method which displays for 4 seconds and dissapears to 
		 * inform the user the there was an issue with their selection or 
		 * not
		 */
		
		errorLabel2 = new Label(text);
		
		errorLabel2.setLayoutX((400)-errorLabel2.prefWidth(-1)/2);
		System.out.println(text);
		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {

		    public void handle(ActionEvent event) {
		    	errorLabel2.setText("");
		    }
		}));
		
		fiveSecondsWonder.play();
		
	}
	
	
	
	@FXML
	public boolean deleteClicked(ActionEvent event){
		
		/*Delete check which items in the drop down
		 * menu was selected and then removes it not 
		 * only form the data base but also removes the specialty from the 
		 * employee
		 */
		
		if(toDelete == null){
			return false;
		}
		
		if(toDelete.get(deleteBox.getSelectionModel().getSelectedIndex()) == null){
			return false;
		}
		
		ArrayList<String> staff = new ArrayList<String>();
		
		staff = database.getAllEmployees();
		
		ArrayList<String> staffS = new ArrayList<String>();
		
		staffS = database.getAllSpecialityNum();
		
		for(int i = 0; i < staff.size(); i++){
			
			StringBuilder newStr = new StringBuilder(staffS.get(i));
			
			newStr.deleteCharAt(deleteBox.getSelectionModel().getSelectedIndex());
			
			database.deleteActivityFromE(newStr.toString() ,staff.get(i));
			
			database.deleteActivity(toDelete.get(deleteBox.getSelectionModel().getSelectedIndex()));
		
		}
		
		this.initialize();
		
		return true;
		
	}
	
	@FXML
	public boolean addClicked(ActionEvent event)
	{
		//Converts the time to a int
		
		addActivity(choiceBox.getSelectionModel().getSelectedIndex(), nameTextbox.getText());
		
		this.initialize();
		
		return true;
	}
	
	public boolean addActivity(int timeIndex, String nameBoxText){
		
		/* An activity that is requested to be inserted needs 
		 * to be valid for it to work it first checks whether the input 
		 * is within the 3 hour slot time 
		 */
		
		int time = timeIndex;
		
		if(time > 3){
			return false;
		}
		
		if(time == 0){
			timerWithErrorText("Please Select a Time Slot");
			return false;
		}
		
		
		if (nameBoxText == null) {
			timerWithErrorText("Enter the name of the activity");
			return false;
		}
			
		
		String localName = nameBoxText.trim();
		
		
		// Make sure the user had entered a name and not an empty string. 
		
		/* Makes sure that there is a valid activity enterdd
		 * 
		 */
		
		if (localName == null){
			timerWithErrorText("Enter the name of the activity");
			return false;
		}
		
		if (localName.isEmpty())
		{
			timerWithErrorText("Enter the name of the activity");
			return false;
		}
		
		// Make sure the length is between 2 and 17 no more no less
		
		if (localName.length() > 18 || localName.length() <= 1)
		{
			timerWithErrorText("Enter the activity of the activity");
			return false;
		}
		
		
		if(localName.matches("^[a-zA-z]+([a-zA-z]|\\d?)$") == false)
		{
			timerWithErrorText("The activity you have entered is invalid");
			return false;
		}
		
		
		// Siphon through the current database and check if a user of the same name has been entered. No double ups
		
		boolean flag = database.checkForDuplicate("activities", "name", localName);
		
		for(int i = 0; i < localName.length(); i++){
			if(Character.isWhitespace(localName.charAt(i))){
				timerWithErrorText("The activity you have entered is invalid. No whitespaces");
				flag = false;
			}
		
		}
		
		if (flag == false)
		{
			timerWithErrorText("The activity you have entered is already in the system");
			return false;
		}
		
		ArrayList<String> staff = new ArrayList<String>();
		
		staff = database.getAllEmployees();
		
		ArrayList<String> staffS = new ArrayList<String>();
		
		staffS = database.getAllSpecialityNum();
		
		for(int i = 0; i < staff.size(); i++){
			
			String newStr = new String(staffS.get(i));
			
			newStr += "0";
			
			database.deleteActivityFromE(newStr.toString() ,staff.get(i));
			
		
		}
		
		database.addActivityDatabase(localName, time);
		
		timerWithErrorText("Success, you have succesfully added "+ localName);
		
		return true;
	}
	
	public boolean setOwner(Owner owner)
	{
		//this.owner = new Owner(owner.getName(), owner.getAddress(), owner.getUsername(), owner.getPassword(), owner.getPhone());
		
		System.out.println(this.owner.getName());
		
		if (this.owner != null)
			return true;
		return false;
	}
	
	@FXML public void backClicked(ActionEvent event)
	{
		administrator.changeScene(event, Traversal.MENU);
	}

}
