package NWC.controller;

import java.io.IOException;
import java.util.ArrayList;

import Interfaces.Traversal;
import Logs.Log;
import NWC.model.Administrator;
import NWC.model.PersonalTrainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class EmployeeViewController 
extends Controller {

	private Administrator administrator = new Administrator();
	private ArrayList<Label> labels = new ArrayList<Label>();
	@FXML public Pane EmployeePane;
	@FXML public ScrollPane sc;
	@FXML Label noRoster;
	//ArrayList<String> staff = database.getAllEmployees();
	int k, i, j = 0;
	
	//String staffName;
	String workingTimes;
	String def = "-";
	int slots = 8;
	
	private Log view_avail_log;
	
//-----------------------------------------
	public PersonalTrainer trainer;
	public String trainerName; 
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		System.out.println("Setting"+trainerName);
		this.trainerName = trainerName;
	}
//-----------------------------------------	
	public int getSlots() {
			return slots;
		}
	
	public void setSlots(int slots) {
			this.slots = slots;
		}
	
	public void initialize(){
	
	
		System.out.println("IN INITTTT" );
		
		System.out.println(trainerName);
			
			int left = 0;
			int down = 140 + (k*15);
		
	
			for(i = 0; i<slots*7; i++)
			{	
				//creates 56 new labels from each employee 
				Label tempLabel = new Label();
				
				tempLabel.setId("D" + i);
				
				if(i == 0)
				{
					tempLabel.setLayoutX(left);
				}
				
				if(i % slots == 0 )
				{
					left+=105;
					System.out.println(left);
					down = 140 + (k*15);
				}
				
				System.out.println("x " + left + " y "+ down);
				
				//sets the spot on the screen 
				tempLabel.setLayoutX(left);
				tempLabel.setLayoutY(down);
				down+=75;
				
				//sets the properties of the label
				tempLabel.setPrefSize(100, 50);
				tempLabel.setTextFill(Color.WHITE);
				//tempLabel.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				tempLabel.setText(def);
		
				labels.add(tempLabel);
				EmployeePane.getChildren().add(tempLabel);
				
			}
		}
	/* calls all the methods to display on screen*/
	public void callInit(String name)
	{
		setTrainerName(name);
		System.out.println(trainerName);
		workingTimes = database.getWorkingTimes(trainerName);
		//checks that there is an array to display the times
		System.out.println(workingTimes.length() );
		if(workingTimes.length() > 1)
		{
			fillLabels(workingTimes,trainerName,labels);
		}
		if(workingTimes.length() == 1){
			try {
				view_avail_log = new Log();
				doLog("No times to display, not rostered on yet",view_avail_log);
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/*fills the labels that have been previously generated with information from the working times string*/
	public void fillLabels(String workingTimes, String fillWith, ArrayList<Label> labels)
	{
		for(i=0;i < (labels.size());i++ )
		{
			System.out.println(workingTimes.charAt(i));
			if(workingTimes.charAt(i) == Character.forDigit(1, 10))
			{				
				if(labels.get(i).getText() == def)
				{
					labels.get(i).setText(trainerName);
				}
			}
		}
		
	}
	
	@FXML
	public void back(ActionEvent event)
	{
		administrator.changeScene(event, Traversal.CHOOSE_EMPLOYEE, Traversal.VIEW_ROSTER);
	}

	public void doLog(String message, Log log)
	{
		log.logger.info(message);
		log.closeHandler();
	}
}
