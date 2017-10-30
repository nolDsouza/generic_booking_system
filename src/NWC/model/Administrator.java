package NWC.model;

import java.io.IOException;

import Interfaces.SourceManipulation;
import Interfaces.Traversal;
import Logs.Log;
import NWC.controller.ChooseEmployeeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Administrator 
implements Traversal, SourceManipulation {
	
	private String next;
	Origin origin = Origin.getInstance();
	
	public Administrator() {}
	
	public Administrator(int resource) {
		next = views[resource];
	}
	
	public void setNext(int resource) {
		next = views[resource];
	}
	
	public void setKey(String iD) {
		key.append(iD);
	}
	
	@Override
	public void setUser(boolean authorised) {
		String id = key.toString();
		if (origin.isFrozen) {
			
			try{
				Log admin_log = new Log();
				doLog("origin is frozen", admin_log);
			}catch (IOException e) {
				e.printStackTrace();
				}
			return;
		}
		String name = origin.db.getName(key.toString());
		if (authorised) {
			Origin.getInstance().user = new Owner(name, null, null);
		} else {
			Origin.getInstance().user = new Client(origin.db.getName(id), null, null, null, null);
		}
	}
	
	public boolean authenticate(String iD) {
		if (iD.toString().charAt(0) == 'E') {
			setUser(true);
			origin.isFrozen = true;
			return true;
		}
		setUser(false);
		origin.isFrozen = true;
		return false;
	}
	
	public void changeScene(ActionEvent ae, int resource) {
		Parent menu_page_parent;
		try {
			//this logs the scene change 
			Log admin_log = new Log();
			doLog("scene change to " + resource, admin_log);
			
			//this makes the scene change
			menu_page_parent = FXMLLoader.load(getClass().getResource(views[resource]));
			Scene menu_page_scene = new Scene(menu_page_parent);
			Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
			stage.setScene(menu_page_scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void changeScene(ActionEvent ae, int resource, int next) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(views[resource]));     
	        Parent root = (Parent)fxmlLoader.load();          	        
	        Scene scene = new Scene(root); 					            
	        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        ChooseEmployeeController controller = fxmlLoader.<ChooseEmployeeController>getController();
	        controller.callInit(next);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void unFreeze() {
		origin.isFrozen = false;
	}
	
	public String getNext() {
		return this.next;
	}
	
	public void doLog(String message, Log log)
	{
		log.logger.info(message);
		log.closeHandler();
	}

}