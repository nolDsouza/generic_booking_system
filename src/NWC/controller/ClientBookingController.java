package NWC.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import Interfaces.OpeningHours;
import Interfaces.OpeningHours.Schematic;
import Interfaces.TimeConstants;
import NWC.model.Administrator;
import NWC.model.Booking;
import NWC.model.Client;
import NWC.model.Day;
import NWC.model.Origin;
import NWC.model.PersonalTrainer;
import NWC.model.WT;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientBookingController 
extends Controller 
implements TimeConstants {
	
	final int WEEKSIZE = 7;
	final int SLOTNUM = 8;
	final double INTERVAL = 1.0;
	
	private Administrator administrator = new Administrator();
	@FXML private GridPane grid = new GridPane();
	@FXML private ChoiceBox<String> activityBox = new ChoiceBox<String>();
	@FXML private Button next = new Button();
	@FXML private Button previous = new Button();
	@FXML private Label weekLabel = new Label();
	private int head = 0;
	private Button[][] buttons = new Button[WEEKSIZE][SLOTNUM];
	private OpeningHours op = new OpeningHours();
	private Schematic schematic = op.getSchematic();
	private Label[] labels = new Label[schematic.getLatest()-schematic.getEarliest()];
	private String selectedActivity;
	private ArrayList<PersonalTrainer> relevantEmployees = new ArrayList<PersonalTrainer>();
	private ArrayList<String> activities = Origin.getInstance().db.getAllActivities();
	private String clientName = Origin.getInstance().user.getName();
	private String clientID = Origin.getInstance().db.getID(clientName);
	private ArrayList<Booking> bookings = database.selectClientsBookings(clientID);
	
	@FXML 
	void initialize() {
		previous.setDisable(true);
		initChoiceBox();
		setIntervals();
		System.err.println("Please select an activity");
		System.out.println(Arrays.toString(labels));
	}
	
	public void initChoiceBox() {
		activityBox.getItems().addAll(activities);
		activityBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String arg1, String arg2) {				
				selectedActivity = arg2;
				relevantEmployees.clear();
				fillGrid(combineAllAvailable(selectedActivity));
			}
		});
	}
	
	public void clear() {
		for (int c=0; c<WEEKSIZE; c++) {
			for (int r=0; r<SLOTNUM; r++) {
				grid.getChildren().remove(buttons[c][r]);
			}
		}
	}
	
	public void next() {
		previous.setDisable(false);
		clear();
		head += WEEKSIZE;
		System.out.println(head);
		if (head == 21)
			next.setDisable(true);
		fillGrid(combineAllAvailable(selectedActivity));
	}
	
	public void previous() {
		next.setDisable(false);
		clear();
		head -= WEEKSIZE;
		if (head == 0)
			previous.setDisable(true);
		fillGrid(combineAllAvailable(selectedActivity));
	}
	
	public void setIntervals() {
		
		int start = schematic.getEarliest();
		int end = schematic.getLatest();
		for (int i=start; i<end; i++) {
			int index = i-start;
			labels[index] = new Label(String.format("%12d-%d\n", i, i+(int) INTERVAL));
			labels[index].setPrefWidth(80.0);
			labels[index].setMinHeight(50.0);
			grid.add(labels[index], 0, index);
		}
	}
	
	public ArrayList<WT> combineAllAvailable(String activity) {
		HashMap<String, ArrayList<WT>> workingTimes = database.getStaff();		
		ArrayList<WT> finalList = new ArrayList<WT>();
		HashSet<String> hs = Origin.getInstance().specialisations.get(activity);
		
		if (hs.isEmpty()) {
			return finalList;
		}
		
		for (Entry<String, ArrayList<WT>> entry : workingTimes.entrySet()) {
			ArrayList<WT> value = entry.getValue();
			if (!hs.contains(entry.getKey()) || value.isEmpty()) {
				continue;
			}
			String name = entry.getValue().get(0).getwID();
			relevantEmployees.add(new PersonalTrainer(name));
			if (finalList.isEmpty()) {
				finalList.addAll(entry.getValue());
				continue;
			}
			
			for (int i=0; i<finalList.size(); i++) {
				if (!finalList.get(i).full()) {
					StringBuilder temp = entry.getValue().get(i).getWorkingTimes();
					finalList.get(i).combine(temp);
				} else System.err.println("full");
			}

			
		}
		return finalList;
	}
	
	public void fillGrid(ArrayList<WT> finalList) {
		int c = 0;
		
		grid.getChildren().clear();
		setIntervals();
		if (finalList.isEmpty()) {
			System.err.println("No staff available right now");
			return;
		}
		
		for (int i=head; i<(head+WEEKSIZE); i++) {
			int activityLength = Origin.getInstance().activityIntervals.get(selectedActivity);
			StringBuilder tempString = finalList.get(i).getWorkingTimes();			
			tempString = WT.refine(tempString, activityLength);
			
			for (int r=0;r<tempString.length(); r++) {
				if (tempString.charAt(r) == '1') {
					Button button = new Button("");
					button.setPrefWidth(80.0);
					button.setMinHeight(50.0);
					button.setStyle("-fx-background-color: #479E7D; "
							+ "-fx-border-color: black;"
							+ "-fx-text-fill: white");
					if (Origin.getInstance().user.getClass() == Client.class) {
					//System.out.printf("For %d was %s\n", r, WT.canBeStart(tempString, r, activityLength));
						if (WT.canBeStart(tempString, r, activityLength)) {
							button.setText(String.format("%d-%d\n", r, i-head));
							button.setOnAction(new EventHandler<ActionEvent>() {
					            @Override public void handle(ActionEvent e) {
					            	String tempString = e.getSource().toString();
					            	tempString = tempString.substring(tempString.indexOf('\'')+1
					            			, tempString.length()-1);
					            	int column = Character.getNumericValue(tempString.charAt(2));
					            	int row = Character.getNumericValue(tempString.charAt(0));
					            	String time = labels[row].getText().trim();
					            	
					            	int start = Integer.parseInt(time.substring(0, time.indexOf('-')));
					            	int end = Integer.parseInt(time.substring(time.indexOf('-')+1, time.length()));
					            	end += activityLength-1;
					            	String now = TimeConstants.getNow();
					            	int ahead = head+column+1;
					            	int t = (row+column+(column*WEEKSIZE))+(labels.length*WEEKSIZE*(head/WEEKSIZE));

					            	for (int k=0;k<ahead; k++) {
					            		now = TimeConstants.increment(now);
					            	}
					            	Booking b = new Booking(Origin.getInstance().user.getName(),
					            			new Day(start,end), selectedActivity, now);
					            	checkAvailable(row, column);
					            	
					            	getConfirmation(e, b, t);
									
					            }
					        });
						}
					}
					c = i-head;
					int d = c*((head+WEEKSIZE)/WEEKSIZE);
					buttons[c][r] = button;
					if (bookedForDay(d)) {
						buttons[c][r].setDisable(true);
					}
					grid.add(buttons[c][r], c+1, r);
				}
			}
		}
	}
	
	public boolean bookedForDay(int n) {
		String now = TimeConstants.getNow();
		//now = TimeConstants.increment(now);
		System.out.println("Day is " + n);
		for (int i=0; i<n; i++) {
			now = TimeConstants.increment(now);
		}
		System.out.println("Checking for " + now);
		for (Booking key : bookings) {
			if (key.getDate().equals(now)) {
				System.out.println("YES!! For : " + key.toString() + " AND " + now);
				return true;
			}
				now = TimeConstants.increment(now);
		}
		return false;
		
	}
	
	public boolean checkAvailable(int row, int column) {
		
		List<PersonalTrainer> irrelevantEmployees = new ArrayList<PersonalTrainer>();
		System.out.printf("Row is %d, column is %d\n", row, column);
		// Irritating, index is a synchronized reference between employee times & grid selection
		int index = (row+column+(column*WEEKSIZE))+(labels.length*WEEKSIZE*(head/WEEKSIZE));

		
		for (PersonalTrainer employee : relevantEmployees) {
			System.out.println("Checking at " + index + " for " + employee.getName());
			System.out.println(Arrays.toString(employee.getWorkingTimes()));
			
			if (employee.getWorkingTimes()[index] == 1) {
				System.out.println("a valid employee!");
			} 
			else {
				System.out.println("Not available");
				irrelevantEmployees.add(employee);
			}
		}
		
		System.out.println("got " + irrelevantEmployees.toString());
		relevantEmployees.removeAll(irrelevantEmployees);
		
		return false;
    	
	}
	
	public void getConfirmation(ActionEvent ae, Booking booking, int time) {
		PersonalTrainer employee = new PersonalTrainer("Nol");
		System.out.println(employee.getWorkingTimes());
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(views[CONFIRM]));     			
		    Parent root = (Parent)fxmlLoader.load();     		    
	        Scene scene = new Scene(root); 					            
	        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
	        stage.setScene(scene);	        	        
	        ConfirmBookingController controller = fxmlLoader.<ConfirmBookingController>getController();        
	        controller.callInit(booking, relevantEmployees, time);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
        
	}
	
	public void back(ActionEvent event) {
		administrator.changeScene(event, MENU);
	}
}
