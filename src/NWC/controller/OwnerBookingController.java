package NWC.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import Interfaces.TimeConstants;
import Interfaces.Traversal;
import NWC.model.AppTime;
import NWC.model.Booking;
import NWC.model.WorkingTime;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class OwnerBookingController 
extends Controller
implements TimeConstants {
	
	@FXML 
	private Label dateError = new Label();
	@FXML
	private ChoiceBox<String> clientBox = new ChoiceBox<String>();
	@FXML
	private ListView<String> bookingTimes = new ListView<String>();
	@FXML
	private ListView<String> workingTimes = new ListView<String>();
	@FXML
	private ChoiceBox<String> startTimes = new ChoiceBox<String>(); 
	@FXML
	private ChoiceBox<String> endTimes = new ChoiceBox<String>(); 
	@FXML
	private DatePicker datePicker = new DatePicker(); 
	@FXML
	private Button search = new Button(); 
	@FXML
	private Button back = new Button(); 
	private ArrayList<String> clients = new ArrayList<String>();
	private ArrayList<String> relevantTimes = new ArrayList<String>();
	private ArrayList<WorkingTime> buffers = new ArrayList<WorkingTime>();
	private int[] b = {0,0,0,0,0,0,0,0}; 
	private AppTime aT;
	private Booking newBooking;
	
	@FXML
    void initialize() {
		dateError.setText("");
		database.getAllClients(clients);
		for (int i=0; i<clients.size(); i++) {
			clientBox.getItems().add(clients.get(i));
		}
		System.out.println(endTimes.getId());
		
		for (int i = 10; i<18; i++) {
			String tempString = String.format("%d:00", i);
			startTimes.getItems().add(tempString);
		}
		/* override method to declare changeListener and it's method
		 * (SceneBuilder does not allow this) extend String to avoid raw values
		 * (non-Javadoc)
		 * @see javafx.beans.value.ChangeListener#changed(javafx.beans.value.ObservableValue, java.lang.Object, java.lang.Object)
		 */
		startTimes.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String arg1, String arg2) {
				endTimes.getItems().clear();
				int sT = Integer.parseInt(arg2.substring(0, arg2.indexOf(":")))+1;
			
            	endTimes.getItems().add(String.format("%d:00", sT));
	            search.setDisable(false);
	            workingTimes.getItems().clear();
			}
		});
		
		clientBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String arg1, String arg2) {
				bookingTimes.getItems().clear();
				ArrayList<String> bookings = database.tellBookingTime(clientBox.getValue());
				for (int i =0;i<bookings.size();i++) {
					String[] tempStrings = bookings.get(i).split("#");
					String tempString = String.format("%s/%s: %s", 
							tempStrings[0], tempStrings[1], tempStrings[2]);
					bookingTimes.getItems().add(tempString);
				}
			}
		});
		
		workingTimes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        int n = Character.getNumericValue(newValue.charAt(0))-1;
		    	System.out.println(newValue);
		    	buffers.get(n).getApp().flip(b);
		        buffers.get(n).update();
		        newBooking = new Booking(clientBox.getValue(),
		        		datePicker.getValue().getMonthValue(), datePicker.getValue().getDayOfMonth(),
		        		startTimes.getValue(), endTimes.getValue());
		        newBooking.insert(true);
		        
		        back.fire();
		    }
		});
	}
	
	public boolean validateDate() {
		LocalDate currentTime = LocalDate.now();
		buffers.clear();
		workingTimes.getItems().clear();
		try {
			if (datePicker.getValue().isBefore(currentTime)) {
				dateError.setText(String.format("Earliest day is %s %d\n",
						Month.values()[monthOfYear], dayOfMonth+1));
				return false;
			}
		} catch (NullPointerException e) {
			dateError.setText("Need to select a date!");
			return false;
		}
		dateError.setText("");
		workingTimes.getItems().clear();
		
		String day = String.valueOf(datePicker.getValue().minusDays(dayOfMonth).getDayOfMonth());
		relevantTimes = database.getRelevantTimes(day);
		
		for (int i=0;i<relevantTimes.size();i++) {
			buffers.add(new WorkingTime(relevantTimes.get(i)));
		}
		if (relevantTimes.isEmpty()) {
			workingTimes.getItems().add("There are no working times available\n");
		}
		search.setDisable(false);
		
		return true;
	}
	
	public boolean search(ActionEvent ae) {
		if (endTimes.getValue()==null || clientBox.getValue()==null || !validateDate()) {
			System.out.println("haven't selected all values!");
			return false;
		}
		System.out.printf("04/02#%s-%s#%s\n", startTimes.getValue(),
				endTimes.getValue(), database.getID(clientBox.getValue()));
		
		int sT = Integer.parseInt(startTimes.getValue().substring(0, startTimes.getValue().indexOf(":")));
		int eT = Integer.parseInt(endTimes.getValue().substring(0, endTimes.getValue().indexOf(":")));
		
		for (int i=0;i<8;i++) {
			b[i] = 0;
		}
		for (int i=(sT-10); i<(eT-10); i++) {
			b[i] = 1;
		}
		System.out.println(Arrays.toString(b));
		aT = new AppTime(b);
		
		for (int i=0;i<buffers.size(); i++) { 
			if (aT.matches(buffers.get(i).getApp().buffer)) {
				workingTimes.getItems().add((1+i) + ". " + buffers.get(i).toString());
			}
		}
		search.setDisable(true);
		
		return true;
		
	}
	
	@FXML
	public void back(ActionEvent event)
	{
		administrator.changeScene(event, Traversal.MENU);
	}
	
	
}
