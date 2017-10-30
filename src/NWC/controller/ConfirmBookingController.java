package NWC.controller;

import java.util.ArrayList;

import NWC.model.Booking;
import NWC.model.PersonalTrainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class ConfirmBookingController 
extends Controller
{
	@FXML private Label dateLabel = new Label();
	@FXML private Label timeLabel = new Label();
	@FXML private Label activityLabel = new Label();
	@FXML private Label notification = new Label();
	@FXML private GridPane grid = new GridPane();
	private Booking booking;
	private PersonalTrainer employee;
	private final ToggleGroup group = new ToggleGroup();
	private int timeslot, activityLength;
	
	@Override
	void initialize() {
		notification.setText("Select Professional:");
	}	
	
	public void callInit(Booking booking, ArrayList<PersonalTrainer> relevantEmployees, int time) {
		this.timeslot = time;
		this.activityLength = booking.getDay().hoursOpen();
		this.booking = booking;
		
		System.out.printf("Working on %d, for %d hours\n", timeslot, activityLength);
		boolean alternator = true;
		int count = 0;
		for (PersonalTrainer employee : relevantEmployees) {
			RadioButton rb = new RadioButton(employee.getName());
			rb.setToggleGroup(group);
			rb.setStyle("-fx-text-fill: white;"
					+ "-fx-font-size: 16px;");
			
			if (relevantEmployees.get(0).getName() == employee.getName()) {
				rb.setSelected(true);
			}
			
			if (alternator) {
				grid.add(rb, 0, count); 
			}
			else {
				grid.add(rb, 1, count);
				count++;
			}
			alternator = !alternator;
			
		}
		
		dateLabel.setText(String.format("%s%30s", dateLabel.getText(), booking.getDate()));
		timeLabel.setText(String.format("%s%30s", timeLabel.getText(), booking.getTimes()));
		activityLabel.setText(String.format("%s%25s", activityLabel.getText(), booking.getActivity()));
	}
	
	public void confirm(ActionEvent event) {
		
		String tempString = group.getSelectedToggle().toString();
		tempString = tempString.substring(tempString.indexOf('\'')+1, tempString.length()-1);
		
		booking.setEmployee(tempString);		
		System.out.println(booking.toString());
		System.out.println("Name is " + tempString);
		employee = new PersonalTrainer(tempString);
		employee.applyTimes(timeslot, activityLength);
		booking.insert();
		
		administrator.changeScene(event, MENU);
	}
	
	public void back(ActionEvent event) {
		administrator.changeScene(event, BOOK);
	}


}
