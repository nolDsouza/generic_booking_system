package NWC.controller;
import java.time.Month;
import java.util.ArrayList;

import Interfaces.Traversal;
import NWC.model.Administrator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class BookingSummaryController
extends Controller
{	
	
	private Administrator administrator = new Administrator();
	@FXML private ListView<String> list= new ListView<String>();
	private ArrayList<String> clients = new ArrayList<String>();
	private Month month;	//Initialization of variables
	private String day;
	private String time;
	private String worker;
	
	@FXML
	void initialize()
	{
		database.getAllClients(clients);
		for(int i = 0; i < clients.size(); i++)
		{
			ArrayList<String> bookings = database.tellBookingTime(clients.get(i));
			if (!bookings.isEmpty())
			{
				list.getItems().add(clients.get(i)); //If the client has no bookings name does not show otherwise show clients name through loop
			}
			for(int j = 0; j < bookings.size(); j++)
			{
				String tempString = String.format("-%70s", summarise(bookings.get(j)));
				list.getItems().add(tempString);	//Prints out the the booking date with a span after - through a loop and utilization of summarise method below
			}										
		}
		
	}
	
	public String summarise(String unformatted)	//Summarise method utilized with tokens to convert int month to string through parse
	{
		String[] tokens = unformatted.split("#"); //# used as a delimeter
		month = Month.values()[Integer.parseInt(tokens[0])];
		day = tokens[1];
		time = tokens[2];
		worker = tokens[3];
		return String.format("%s %s: %s with %s", month,day,time,worker); //returns new format of date
	}
	
	@FXML
	public void backClicked(ActionEvent event)
	{
		administrator.changeScene(event, Traversal.MENU);
	}
	
	
}
