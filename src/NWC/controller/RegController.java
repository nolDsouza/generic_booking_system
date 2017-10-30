package NWC.controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interfaces.Traversal;
import Logs.Log;
import NWC.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class RegController
extends Controller {
	
	@FXML public TextField usernameText;
	@FXML public TextField passwordText1;
	@FXML public TextField passwordText2;
	@FXML public TextField nameText;
	@FXML public TextField phoneText;
	@FXML public TextField addressText;
	@FXML public Label invalidUsername;
	@FXML public Label invalidName;
	@FXML public Label invalidNo;
	@FXML public Label invalidPassword;
	@FXML public Label invalidAddress;
	@FXML public ImageView tick;
	 
	@Override void initialize() {}
	
	/* a series of checks before it allows the user to register, may need refactoring*/ 
	public boolean register(ActionEvent event )
	{
		//assign a ID an put in create client method
		try {
			Log register_log = new Log();
			register_log.logger.info("clicked register with empty field");
			register_log.closeHandler(); // every log has to close the files to prevent the exception generating a .lck (lock file)
			
			//------------------------------- Initalizing all the variables on the page 
			String name = nameText.getText();
			String address = addressText.getText();
			String username = usernameText.getText();
			String password1 = passwordText1.getText();
			String password2 = passwordText2.getText();
			String phone = phoneText.getText();
			
			invalidUsername.setText(null);
			invalidName.setText(null);
			invalidNo.setText(null);
			invalidPassword.setText(null);
			invalidAddress.setText(null);
			//--------------------------------
			
			//validation for sumbitting all empty fields 
			if(checkIfEmpty(name,username,address,password1,phone, register_log) == false){
				return false;
			}
			
			//runs the checks on all the user input to make sure all the input is valid 
			if(runChecks(name,username,address,password1,password2,phone, register_log) == false){
				return false;
			}
			else{
				// shows the user a tick as verification they are registered
				tick.setVisible(true);
				
				//creates a client saved in newClient
				Client newClient = new Client(nameText.getText(), addressText.getText(), usernameText.getText(),passwordText2.getText(),phoneText.getText());
				newClient.setID(database.createID());
				if(database.addClientDatabase(newClient) == true)
				{
						return true;
				}	

			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/* allows the user to navigate pages with the back button*/
	public void back(ActionEvent event)
	{
		administrator.changeScene(event, Traversal.LOGIN);
	}
	
	
	/*check if any fields are left empty */
	public boolean checkIfEmpty(String name, String username, String address, String password1, String phone, Log register_log)
	{
		//validation for sumbitting all empty fields 
		if(name.isEmpty() && address.isEmpty() && username.isEmpty() && password1.isEmpty() && phone.isEmpty()){
			alertAndLog(register_log, "empty fields", invalidName, "Invalid entries");
			return false;
		}
		return true;
	}

	
	/* to check if the name is legal by seeing that the length doesnt exceed 20  and it does not contain a space, and is all characters  */
	public boolean verifyName(String name, Log register_log)
	{
		if(name.isEmpty() == true || name.contains(" ") || name.matches(".*\\d+.*") == true)
		{
			alertAndLog(register_log, "empty name", invalidName, "Invalid name");
			return false;
		}
		if(name.length() > 20){
			alertAndLog(register_log, "name too long", invalidName, "Name must be below 20 characters");
			return false;
		}
		return true;
		
	}
	
	/*checks that the username is legal and does not duplictaes in the database, checks it doesnt exceed 20 */
	public boolean verifyUsername(String username, Log register_log){
		
		//checks that username field is not empty, checks that username is not integers 
		if(username.isEmpty() == true || username.matches(".*\\d+.*") == true)
		{
			alertAndLog(register_log, "invalid username", invalidUsername, "Invalid Username");
			return false;
		}
		//checks for duplicates in the username field 
		if(database.checkForDuplicate("accounts", "username", username) == false)
		{
			alertAndLog(register_log, "duplicate username", invalidUsername, "This Username is taken");
			return false;
		}
		if(username.contains(" "))
		{
			alertAndLog(register_log, "username contains space", invalidUsername, "Username can not contain a space");
			return false;
		}
		if(username.length() > 20){
			alertAndLog(register_log, "username too long", invalidUsername, "Username must be below 20 characters long");
			return false;
		}
		return true;
		
	}
	
	/*checks the password is valid, by its length (greater than 6) that the passwords match, and contain no spaces*/
	public boolean verifyPassword(String password1, String password2, Log register_log){
		
		//checks that the passwords match 
		if(password1.matches(password2) == false || password1.isEmpty() == true || password2.isEmpty() == true){
			alertAndLog(register_log, "Passwords do not match", invalidPassword, "Passwords do not match");
			return false;
		}
	
		//checks that the password length is >= 6, checks that neither of the passwords fields are empty 
		if(password1.length()  <= 6|| password2.length() <= 6){
			alertAndLog(register_log, "password too short", invalidPassword, "Invalid password length");
			return false;
		}
		
		//checks the password contains no spaces 
		if(password1.contains(" ")|| password2.contains(" ")){
			alertAndLog(register_log, "invalid password", invalidPassword, "Invalid entries, cannot contain a space");
			return false;
		}
		
		return true;
	}
	
	/*ensure s the pone number is valid by checking length (greater than 8)  and checking that each digit is a number not character*/
	public boolean verifyPhone(String phone, Log register_log){
		
		//checks that each digit of the phone number is an integer 
		for(int i=0; i < phone.length()-1; i++)
		{
			if(Character.isLetter(phone.charAt(i))){
				alertAndLog(register_log, "invalid phone number", invalidNo, "Contains invalid characters");
				return false;
			}
		}
		if(phone.matches(".*\\d+.*") == false || phone.length() < 8)
		{
			alertAndLog(register_log, "invalid phone number", invalidNo, "Invalid phone number length");
			return false;
		}
		
		return true;
	}
	
	/*check the address field is valid by checking the length is greater than 10 and it contains at least one space*/
	public boolean verifyAddress(String address, Log register_log)
	{
		if(address.contains(" ") == false  && address.length() < 10){
			alertAndLog(register_log, "invalid address", invalidAddress, "Invalid address");
			return false;
		}
		return true;
			
	}
	
	/* a method to run the checking and enusre all methods return the correct value */
	public boolean runChecks(String name, String username, String address, String password1, String password2, String phone, Log register_log){
		boolean flag = true;
		if(verifyName(name,register_log) == false){
			flag = false;
		}
		if(verifyUsername(username, register_log) == false){
			flag = false;
		}
		if(verifyPassword(password1, password2, register_log) == false){
			flag = false;
		}
		if(verifyPhone(phone, register_log) == false){
			flag = false;
		}
		if(verifyAddress(address, register_log) == false){
			flag = false;
		}
		if(flag = false)
		{
			return false;
		}
		return true;
	}
	
	/*creates alerts on the GUI and logs them to log.txt*/
	public void alertAndLog(Log Logger, String messageLog, Label alert, String messageAlert ){
		
		alert.setText(messageAlert + ", Please Try Again.");
		
		Logger.logger.info("clicked register: " + messageLog);
		Logger.closeHandler();
	}
}

