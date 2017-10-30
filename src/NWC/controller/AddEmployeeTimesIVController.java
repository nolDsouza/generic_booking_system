package NWC.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Interfaces.OpeningHours;
import Interfaces.TimeConstants;
import Interfaces.Traversal;
import Logs.Log;
import NWC.model.AppTime;
import NWC.model.Day;
import NWC.model.PersonalTrainer;
import NWC.model.WorkingTime;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AddEmployeeTimesIVController 
extends Controller
implements TimeConstants {
	
	private int TIMEPARAMS = 0;
	private final int DAYPARAMS = 7;
	private final int WEEKPARAMS = 4;
	
	@FXML public Button staffNameLabel;
	@FXML public Label weekLabel;
	
	public Pane week1;
	public Pane week2;
	public Pane week3;
	public Pane week4;
	
	public Day[] d = null;
	
	public ScrollPane week1SP;
	public ScrollPane week2SP;
	public ScrollPane week3SP;
	public ScrollPane week4SP;
	
	@FXML AnchorPane mainp;
	
	private List<Button> w1Buttons;
	private PersonalTrainer employee;
	private String trainerName; 
	private int PageNo = 0; 
	
	private int workingTimeBox;
	private int earliest;
	private int latest;
	
	Log add_times_log;
	
	Pane[] weekPaneArray = {week1 = new Pane() ,week2 = new Pane(),week3= new Pane(),week4= new Pane()};
	ScrollPane[] weekScrollPane = {week1SP, week2SP, week3SP, week4SP};

	public void initialize() {
		
		/* Initializes the textfile and assigns 
		 * it to an array which holds each
		 * data of the 7 days.
		 */
		
		
		
		try {
			add_times_log = new Log();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		OpeningHours op = new OpeningHours();
		
		TIMEPARAMS = op.MONTHSLOTS;
		
		earliest = op.getSchematic().getEarliest();
		latest = op.getSchematic().getLatest();
		
		
		
		try {
			d = op.readTextFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < 7; i++){
			workingTimeBox += d[i].hoursOpen();
		}
		
		/*
		 * TIMEPARAMS is the integer which holds the largest day in memory
		 * The for loop below iterates through the list to see which day is 
		 * the largest.
		 */
		
		TIMEPARAMS = d[0].hoursOpen();
		
		for(int i = 0; i < d.length; i++){
			
			if(d[i] != null && d[i].hoursOpen() > TIMEPARAMS){
				TIMEPARAMS = d[i].hoursOpen();
			}
		}
		
		mainp.setPrefHeight(800);
		mainp.setPrefWidth(800);
		w1Buttons = new ArrayList<>();
		
		/*Arrays set to hold each element of the 
		 * pane and scrollablePane
		 * Each is in a for loop and initializes 
		 * each instance
		 */
		
		
		for(int i = 0; i < weekPaneArray.length; i++){
			weekPaneArray[i].setStyle("-fx-background-color: #36373A");
			timeInsert(weekPaneArray[i]);
		}
		
		for(int i = 0; i < weekScrollPane.length; i++){
			weekScrollPane[i] = initScrollable(weekPaneArray[i]);
			mainp.getChildren().add(weekScrollPane[i]);
			
			if(i > 0){
				weekScrollPane[i].setVisible(false);
			}
		}
		
	}
	
	public void timeInsert(Pane pane){
	
		int down = 18;
		
		ArrayList<Label> lA = new ArrayList<Label>();
		
		//Need to find the day with the largest working times, to add to the list. 
		
		String[] times = {"0"};
		Day dayTemp = null;
		
		/* A simple comparison to store a strings array
		 * this will be used to add to the side bar of times
		 * HALF DONE DYNAMICALLY
		 */
		
		
		
		
		String[] AMPM = new String[latest-earliest];
		
		int tempStart = earliest;
		
		for(int i = 0; i < AMPM.length; i++){
			if(tempStart < 12){
				AMPM[i] = "AM";
			} else {
				AMPM[i] = "PM";
			}
			
			tempStart++;
		}
		
		
		int w = earliest;
		
		for(int i = 0; i < AMPM.length; i++){
			
			Label l = new Label(); 
			
			l.setLayoutX(8);
			l.setLayoutY(down);
			
			/* The AM and PM of the
			 * DAY
			 */
			if(w > 12){
				l.setText(w-12 + ":00 "+ AMPM[i]);
			} else {
				l.setText(w + ":00 "+ AMPM[i]);
			}
			
			l.setStyle("-fx-font: 22 Monsterrat");
			l.setTextFill(javafx.scene.paint.Color.WHITE);
			
			down += 58;
			lA.add(l);
			w++;
			
		}
		
		for(int i = 0; i < lA.size(); i++){
			pane.getChildren().add(lA.get(i));
		}
	}
	
	public ScrollPane initScrollable(Pane weekPane){
		
		ScrollPane weekSP = new ScrollPane();
		weekSP.setLayoutX(25);
		weekSP.setLayoutY(260);

		weekSP.setPannable(true);
		
		weekSP.setMaxSize(790, 540);
		
		weekSP.setContent(weekPane);
		weekSP.setStyle("-fx-background-color: transparent");
		weekSP.setStyle("-fx-background-insets: 10px; -fx-padding: 10px;");
		weekSP.setStyle("-fx-background-color: #36373A");
		weekSP.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		return weekSP;
		
	}
	
	public void callInit()
	{
		employee = new PersonalTrainer(this.getTrainerName());
		
		staffNameLabel.setText(this.getTrainerName());
		makeButtons(TIMEPARAMS);
		renderSlots();
	}
	
	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		
		this.trainerName = trainerName;
	}

	public PersonalTrainer getEmployee() {
		return employee;
	}

	public void setEmployee(PersonalTrainer employee) {
		this.employee = employee;
	}
	
	/* 
	 * Make buttons is called during the initialize method, it 
	 * is used to set each button in accordance to the text file
	 * which stated in it, is the working times for the week
	 */
	
	public void makeButtons(int slots) {
		
		int left = 130;
		int down = 9;
		
		int x = 0;
		
		int tempi = 0;
		
		boolean tempBool = true;
		
		/*FOR method which will initialize every working time for the 
		 * month ahead
		 */
		
		for (int i = 0; i < 4*workingTimeBox; i++){
			
			Button tempButton = new Button();
			tempButton.setId(String.valueOf(i));
			tempButton.setPrefSize(55, 50);
			tempButton.setStyle("-fx-background-color: #FFFFFF");
			tempButton.setOnAction(new EventHandler<ActionEvent>() {
				/*Dynamic method set for each of the buttons 
				 * (non-Javadoc)
				 * @see javafx.event.EventHandler#handle(javafx.event.Event)
				 */
	            @Override
	            public void handle(ActionEvent event) {
	            	for (int i = 0; i < w1Buttons.size(); i++)
	        		{
	        			if (event.getSource().toString().equals(w1Buttons.get(i).toString()))
	        			{
	        				String tempString = event.getSource().toString();
	        				tempString = tempString.substring(
	        						1+tempString.indexOf("="), tempString.indexOf(","));
	        		
	        				setAlt(i);
	        				
	        			
	        			}
	        		}
	            }
	        });
			
			// Goes left every number of time slots in the day.
			
			
			
			/* Adds each button to the week pane respectively
			 * 
			 */
			
			if (((tempi+1) % d[x].hoursOpen()) == 0) {
				
				left += 89;
				down = 39-30;
				
				tempi = 0;
				System.out.println("Moving to the new side");
				
				
				if(x < 6){
					int tempP = d[x+1].getStart() - earliest;
					
					for(int n = 0; n < tempP; n++){
						System.out.println("Moving down " + (i+1));
						down += 58;
						tempBool = false;
					}
				}
				
				
				
				x++;
				
			} else {
				
				if(x < 6 && tempBool){
					int tempP = d[x].getStart() - earliest;
					
					for(int n = 0; n < tempP; n++){
						System.out.println("Moving down                             " + (i+1));
						down += 58;
						
					} tempBool = false;
				}
				
				down += 58;
				tempi++;
			}
			
			if((i+1) % (workingTimeBox) == 0){
			
				left = 130;
				x = 0;
			}
			
			tempButton.setLayoutX(left);
			tempButton.setLayoutY(down);
			
			
			if (i < workingTimeBox) {
				
				System.out.println("Adding to position X "+ tempButton.getLayoutX() + " Y "+ tempButton.getLayoutY());
				
				week1.getChildren().add(tempButton);
				
			}
			
			if(i >= workingTimeBox && i < 2*workingTimeBox)
			{
				week2.getChildren().add(tempButton);
			}
			
			if(i >= 2*workingTimeBox && i < 3*workingTimeBox)
			{
				week3.getChildren().add(tempButton);
			}
			
			if(i >= 3*workingTimeBox && i < 4*workingTimeBox)
			{
				week4.getChildren().add(tempButton);
			}
			

			
			
			 
			w1Buttons.add(tempButton);
			
		}
		
		
		
	}
	
	
	public int getDay(String ae) {
		int i = 0;
		while (i < ae.length() && !Character.isDigit(ae.charAt(i))) {
			i++;
		}
		return i;
	}
	
	
	public void renderSlots() {
		for (int i = 0; i < w1Buttons.size(); i++)
		{
			if(i >= employee.getWorkingTimes().length){
				break;
			}
			
			if (employee.getWorkingTimes()[i] == 0)
			{
				w1Buttons.get(i).setStyle("-fx-background-color: #ffffff");
			} 
			else if (employee.getWorkingTimes()[i] == 7) {
				w1Buttons.get(i).setStyle("-fx-background-color: F6A623");
				w1Buttons.get(i).setDisable(true);
			}
			else 
			{
				w1Buttons.get(i).setStyle("-fx-background-color: #479E7D");
			}
		}
	}
	
	/* The set Alt function is pre input into 
	 * the buttons actions
	 * this will let the buttons action know 
	 * to set the alternate button to 
	 * the other number
	 * 
	 */
	
	public void setAlt(int section)
	{
		if (employee.getWorkingTimes()[section] == 0){
		employee.setWorkingTimes(1, section);
		
	} else
	{
		employee.setWorkingTimes(0, section);
	}
		renderSlots();
	}
	
	/* The update method communicates with the database 
	 * This sets a string dependent on the number of 
	 * times due to the bet in the database
	 */
	
	@FXML
	public void update(ActionEvent event)
	{
		int[] buffer = new int[TIMEPARAMS];
		
		employee.updateWorkingTimes();
		for (int i = 0; i < employee.getWorkingTimes().length; i += TIMEPARAMS) {
			for (int j = i; j < i + TIMEPARAMS; j++) {
				buffer[j-i] = employee.getWorkingTimes()[j];
			}
			if (!AppTime.isEmpty(buffer)) {
				//System.out.println(Arrays.toString(buffer));
				WorkingTime tempWT = new WorkingTime(5, (i/TIMEPARAMS), new AppTime(buffer), employee.getName());
				tempWT.insert();
			}
			
		}
	}
	
	@FXML
	public void back(ActionEvent event)
	{
		administrator.changeScene(event, Traversal.CHOOSE_EMPLOYEE, Traversal.ROSTER);
	}
	
	@FXML
	public void fillWeek(ActionEvent event){
		
		/*Finds the whole array of the day and 
		 * fills in in accordance to the day each 
		 * value on the next week
		 */
		
		int[] monArray = new int[TIMEPARAMS];
		
	
		for (int i = 0; i < TIMEPARAMS; i++){
			monArray[i] = employee.getWorkingTimes()[i];
		} 
		
		for (int i = 0; i < TIMEPARAMS*DAYPARAMS; i++){
			employee.setWorkingTimes(monArray[i%TIMEPARAMS], i);;
		}
		
		renderSlots();
	
	}
	
	@FXML
	public void fillMonth(ActionEvent event){
		
		/*Finds the whole array of the week and 
		 * fills in in accordance to the week each 
		 * value on the next week
		 */
		
		int[] monArray = new int[TIMEPARAMS*DAYPARAMS];
		
	
		for (int i = 0; i < TIMEPARAMS*DAYPARAMS; i++){
			monArray[i] = employee.getWorkingTimes()[i];
		} 
		
		for (int i = 0; i < TIMEPARAMS*DAYPARAMS*WEEKPARAMS; i++){
			employee.setWorkingTimes(monArray[i%(TIMEPARAMS*DAYPARAMS)], i);;
		}
		
		renderSlots();
	
	}
	
	@FXML
	public void nextPage(ActionEvent event){
		//Finds on Monday the working times, and add to the rest of the week.
		
		if(PageNo != 3){
			weekScrollPane[PageNo].setVisible(false);
			weekLabel.setText("Week " + (PageNo+2));
			weekScrollPane[PageNo+1].setVisible(true);
			PageNo++;
		}
		
		doLog("Next Page found and is not null", add_times_log);
	}
	
	@FXML
	public void previousPage(ActionEvent event){
		
		/* Finds the PageNo and deals with the previous page adequetly
		 * Code sets the text.
		 */
		
		if(PageNo != 0){
			weekScrollPane[PageNo].setVisible(false);
			weekLabel.setText("Week " + (PageNo));
			weekScrollPane[PageNo-1].setVisible(true);
			PageNo--;
		}
		
		doLog("Previous Page found and is not null", add_times_log);
	}
	
	/*Logging
	 * 
	 */
	
	public void doLog(String message, Log log)
	{
		log.logger.info(message);
		log.closeHandler();
	}
	

}
