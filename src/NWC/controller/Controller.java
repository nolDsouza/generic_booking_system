package NWC.controller;

import Interfaces.Traversal;
import NWC.model.Administrator;
import NWC.model.Database;
import NWC.model.Origin;
import NWC.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public abstract class Controller 
implements Traversal {
	protected User user = Origin.getInstance().user;
	protected Database database = Origin.getInstance().db;
	protected Administrator administrator = new Administrator();
	
	@Override
	public void changeScene(ActionEvent ae, int pageNo) {
		administrator.changeScene(ae, pageNo);
	}
	
	@FXML abstract void initialize();
}
