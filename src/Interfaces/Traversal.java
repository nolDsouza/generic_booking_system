package Interfaces;

import javafx.event.ActionEvent;

public interface Traversal {
	
	String[] views = {
			"/NWC/view/RootLayout.fxml",
			"/NWC/view/LoginInView.fxml",
			"/NWC/view/RegView.fxml",
			"/NWC/view/MenuView.fxml",
			"/NWC/view/CheckAvailabilityView.fxml",
			"/NWC/view/AddEmployeeView.fxml",
			"/NWC/view/AvailEmployees.fxml",
			"/NWC/view/AddEmployeeTimesIndiV.fxml",
			"/NWC/view/EmployeeAvailView.fxml",
			"/NWC/view/NewBookingView.fxml",
			"/NWC/view/BookingSummaryView.fxml",
			"/NWC/view/AddActivityView.fxml",
			"/NWC/view/ClientBookingView.fxml",
			"/NWC/view/ConfirmBookingView.fxml"
	};
	
	int ROOT = 0,
		LOGIN = 1,
		REGISTER = 2,
		MENU = 3,
		CHECK_AVAILABILITY = 4,
		ADD_EMPLOYEE = 5,
		CHOOSE_EMPLOYEE = 6,
		ROSTER = 7,
		VIEW_ROSTER = 8,
		CALL_BOOK = 9,
		SUMMARISE = 10,
		ACTIVITY = 11,
		BOOK = 12,
		CONFIRM = 13;
	
	void changeScene(ActionEvent ae, int pageNo);
}
