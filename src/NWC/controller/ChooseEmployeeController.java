package NWC.controller;

import java.io.IOException;
import java.util.ArrayList;

import Interfaces.Traversal;
import NWC.model.Administrator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ChooseEmployeeController 
extends Controller {
	
	private Administrator administrator;
	private AddEmployeeTimesIVController iVController = null;
	private EmployeeViewController eVController = null;

	
	@FXML Pane pane;
	
	public void callInit(int next) {
		administrator = new Administrator(next);
	}
	
	public void initialize() {
			
			ArrayList<String> staff = database.getAllEmployees();
			ArrayList<Button> buttons = new ArrayList<>();;
			
			for (int i = 0; i < staff.size(); i++)
			{
				Button tempButton = new Button();
				tempButton.setId(staff.get(i));
				
				int left = 0;
				if (i > 13)
				{
					left = 500;
					tempButton.setLayoutX(100+left);
					tempButton.setLayoutY(300+(i-14)*60);
				} else if (i > 6)
				{
					left = 200;
					tempButton.setLayoutX(100+left);
					tempButton.setLayoutY(300+(i-7)*60);
					
				} else {
					tempButton.setLayoutX(100);
					tempButton.setLayoutY(300+i*60);
				}
				
				tempButton.setPrefSize(100, 50);
				tempButton.setText(staff.get(i));
				tempButton.setStyle("-fx-font: 14 Monsterrat; -fx-background-color: #479E7D; -fx-background-radius: 20");
				tempButton.setTextFill(javafx.scene.paint.Color.WHITE);
				tempButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						for (int j = 0; j < buttons.size(); j++)
						{
							if (event.getSource().toString().equals(buttons.get(j).toString()))
							{
								try {
						            // Load person overview.
							
				
						            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(administrator.getNext()));     
						            Parent root = null;           
						            
						            if (administrator.getNext().equals(Traversal.views[Traversal.VIEW_ROSTER])) {
						            	eVController = new EmployeeViewController();
										fxmlLoader.setController(eVController);
										root = (Parent)fxmlLoader.load();
										eVController.callInit(staff.get(j));
									} else {
										iVController = new AddEmployeeTimesIVController();
										fxmlLoader.setController(iVController);
										root = (Parent)fxmlLoader.load();
										iVController.setTrainerName(staff.get(j));
							            iVController.callInit();
									}
						            
						            Scene scene = new Scene(root); 					            
						            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						            stage.setScene(scene);
						            stage.show();
						            
						            
						        } catch (IOException e) {
						            e.printStackTrace();
						        }
								break;
							}
						}
						
					}
					
					
				});
				
				buttons.add(tempButton);
				
			}
			pane.getChildren().addAll(buttons);
				
	}
	@FXML	
	public void back(ActionEvent event)	
	{
		administrator.changeScene(event, Traversal.MENU);
	}

}
