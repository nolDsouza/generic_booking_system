package NWC.controller;


import java.io.IOException;
import java.util.ArrayList;

import Interfaces.Traversal;
import Logs.Log;
import NWC.model.Origin;
import NWC.model.Owner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AddEmployeeController 
extends Controller {
	
	private Owner owner = (Owner) NWC.model.Origin.getInstance().user;
	@FXML private TextField nameTextbox;
	@FXML private Label errorLabel;
	@FXML private Pane pane;
	@FXML private Button add;
	private ArrayList<CheckBox> cbA = new ArrayList<CheckBox>();
	private ArrayList<String> specialties = new ArrayList<String>();
	private Log add_employee_log;
	
	@FXML
	
	public void initialize()
	{
		try {
			add_employee_log = new Log();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		errorLabel.setText("");
		
		/*Need to initialize the current activities in the system
		 * Needs to now look through the database to find out how many items are in the
		 * database "activities"
		 */
		
		/* Need to make a new checkbox's for each activity
		 * 
		 */
		
		ArrayList<String> act = database.getAllActivities();
		
		/* Going two columns across and unlimited rows
		 * 
		 */
		
		int x = 223;
		int y = 375;
		
		for(int i = 0; i < act.size(); i++){
			
			if(i % 2 == 0){
				x = 223;
			}else {
				x = 419;
			}
			if(i % 2 == 0){
				y += 40;
			}
			
			CheckBox cb = new CheckBox();
			
			cb.setLayoutX(x); 
			cb.setLayoutY(y);
			
			cb.setText(act.get(i));
			
			cb.setStyle("-fx-font: 22 Monsterrat");
			cb.setTextFill(javafx.scene.paint.Color.WHITE);
			
			cbA.add(cb);
			
			if(pane != null){
				pane.getChildren().add(cb);
			}
			
			
		}
		
		Label message = new Label();
		message.setText("Choose a specialty");
		message.setLayoutX(300);
		message.setLayoutY(375);
		message.setStyle("-fx-font: 22 Monsterrat");
		message.setTextFill(javafx.scene.paint.Color.WHITE);
		pane.getChildren().add(message);
		
		add.setLayoutY(add.getLayoutY()+(y-360)+10);
		errorLabel.setLayoutY(errorLabel.getLayoutY()+(y-360));
		
	}
	
	public void timerWithErrorText(String text){
		
		errorLabel.setText(text);
		
		errorLabel.setLayoutX((pane.prefWidth(-1)/2)-errorLabel.prefWidth(-1)/2);
		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {

		    public void handle(ActionEvent event) {
		    	errorLabel.setText("");
		    }
		}));
		
		fiveSecondsWonder.play();
		
	}
	
	@FXML
	public boolean addClicked(ActionEvent event)
	{
		
		/* Need to make sure, there had been selected, at least one activity
		 * 
		 */
		
		boolean flag = true;
		String specialty = "";
		specialties.clear();
		for(int i = 0; i < cbA.size(); i++){
			if(cbA.get(i).isSelected() == true){
				flag = false;
				specialty += "1";
				specialties.add(cbA.get(i).getText());
			}else{
				specialty += "0";
			}
			
		}
		if(flag == true){
			doLog("No Specialty Entered", add_employee_log);
			timerWithErrorText("Please Enter a Specialty");
			return false;
		}
		
		System.out.println(specialty);
		
		
		if (owner.addEmployee(nameTextbox.getText().toString(), specialty, specialties) == false){
			doLog("Invalid Staff Name", add_employee_log);
			timerWithErrorText("Please try again, the staff name is invalid or in the database");
			return false;
		} else {
			timerWithErrorText("Success you have entered the employee "+nameTextbox.getText());
		}
		
		Origin.getInstance().specialisations.clear();
		Origin.getInstance().specialisations.putAll(database.getSpecs());
		return true;
	}
	
	public boolean setOwner(Owner owner)
	{
		System.out.println(this.owner.getName());
		
		if (this.owner != null){
			
			doLog("Owner Not Set: Invalid Operation", add_employee_log);
			
			return true;
		}
		return false;
	}
	
	@FXML public boolean backClicked(ActionEvent event)
	{
		if(administrator == null){
			return false;
		}
		administrator.changeScene(event, Traversal.MENU);
		
		return true;
	}
	
	public void doLog(String message, Log log)
	{
		log.logger.info(message);
		log.closeHandler();
	}

}
