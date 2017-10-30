package NWC.controller;

import java.util.ArrayList;

import Enumerations.Week;
import Interfaces.Traversal;
import NWC.model.Administrator;
import NWC.model.AppTime;
import NWC.model.WorkingTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class BookingAvailabilityController
extends Controller {
	
	private final int WEEKSIZE = 7;
	private final int SLOTNUM = 8;
	
	private Administrator administrator = new Administrator();
	@FXML private GridPane grid = new GridPane();
	@FXML private Button next = new Button();
	@FXML private Button previous = new Button();
	@FXML private Label weekLabel = new Label();
	private int head = 0;
	private Button[][] buttons = new Button[WEEKSIZE][SLOTNUM];
	private ArrayList<WorkingTime> times = new ArrayList<WorkingTime>();
	private ArrayList<ArrayList<String>> workingTimes = null;
	
	@FXML 
	void initialize() {
		previous.setDisable(true);
		checkAvail();
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
		checkAvail();
	}
	
	public void previous() {
		next.setDisable(false);
		clear();
		head -= WEEKSIZE;
		if (head == 0)
			previous.setDisable(true);
		checkAvail();
	}
	
	public void checkAvail() {
		int[] span = new int[WEEKSIZE];
		AppTime[] buttonVis = new AppTime[SLOTNUM];
		// get 7 day no. after head
		for (int i=head;i<(head+WEEKSIZE); i++) {
			span[i-head] = i;
		}
		workingTimes = database.getWeeksTimes(span);
		for (int i=0; i<workingTimes.size(); i++) {
			int[] b = {0,0,0,0,0,0,0,0};
			AppTime buffer = new AppTime(b);
			
			for (int j=0; j<workingTimes.get(i).size();j++) {
				times.add(new WorkingTime(workingTimes.get(i).get(j)));
				buffer.add(times.get(j).getApp());
				System.out.println(i + "." + j+ " " + times.get(j).getApp().bufferTS());
				
				if (buffer.isFull())
					break;
			}
			times.clear();
			buttonVis[i] = buffer;
		}
		fillGrid(buttonVis);
	}
	
	public void fillGrid(AppTime[] vB) {
		for (int c=0; c<WEEKSIZE; c++) {
			for (int r=0; r<SLOTNUM; r++) {
				String d = Week.values()[c].toString().substring(0, 3);
				Button button = new Button(String.format("%s %d", d,r+1));
				button.setPrefWidth(80.0);
				button.setMinHeight(50.0);
				button.setStyle("-fx-background-color: #479E7D; "
						+ "-fx-border-color: black;"
						+ "-fx-text-fill: white");
				buttons[c][r] = button;
				if (vB[c].buffer[r] == 0) {
					buttons[c][r].setVisible(false);
				}
				grid.add(buttons[c][r], c+1, r);
			}
		}
		weekLabel.setText(String.format("Week %d", 1+(head/WEEKSIZE)));
	}
	
	public void back(ActionEvent event) {
		administrator.changeScene(event, Traversal.MENU);
	}
}
