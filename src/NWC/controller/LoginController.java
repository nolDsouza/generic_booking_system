package NWC.controller;

import java.io.IOException;

import Interfaces.Traversal;
import Logs.Log;
import NWC.model.Origin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController 
extends Controller {
	
	@FXML private TextField usernameTextField;
	@FXML private TextField passwordTextField;
	@FXML private CheckBox rememberMe = new CheckBox();
	@FXML private Label errorLabel;
	
	@Override
	void initialize() 
	{
		try
		{
			String[] cacheData = Origin.getInstance().db.retrieveCache();
			errorLabel.setText("");
			usernameTextField.setText(cacheData[0]);
			passwordTextField.setText(cacheData[1]);
			rememberMe.setSelected(true);
			
		} 
		catch (NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@FXML
	public void loginClicked(ActionEvent event) throws IOException
	{		
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String ID = null;
		try 
		{
			ID = database.login(username,  password);
			ID.getBytes();
			
		}
		catch(NullPointerException e)
		{
			
			errorLabel.setText("Invalid username or password, please try again");
			if(username.length() == 0)
			{
				errorLabel.setText("Invalid entry username field is empty, please try again");
			}
			if(password.length() == 0)
			{
				errorLabel.setText("Invalid entry password field is empty, please try again");
			}
			if(username.length() ==0 && password.length() == 0)
			{
				errorLabel.setText("Invalid entry username and password are both empty, please try again");
			}
			Log login_log = new Log();
			doLog("login failed", login_log);
			return;
		}
		
		Origin.getInstance().db.clearCache();
		if(rememberMe.isSelected())
		{
			
			Origin.getInstance().db.storeCache(username, password);

		}
		administrator.setKey(ID);
		administrator.changeScene(event, Traversal.MENU);
	}
	
	@FXML
	public void signUpClicked(ActionEvent event)
	{
		administrator.changeScene(event, Traversal.REGISTER);
	}
	
	public void doLog(String message, Log log)
	{
		log.logger.info(message);
		log.closeHandler();
	}
	
}

