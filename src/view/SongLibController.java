package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SongLibController {

    @FXML
    private Button addButton;

    @FXML
    private TextField albumField;

    @FXML
    private Button applyButton;

    @FXML
    private TextField artistField;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<?> listView;

    @FXML
    private TextField searchField;

    @FXML
    private TextField songField;

    @FXML
    private TextField yearField;
    
    
    
    public void handleDeleteButton(MouseEvent e) {
    	System.out.println("Deleted!");
    }
    
    public void handleApplyButton(MouseEvent e) {
    	System.out.println("Applied!");
    }
    
    public void handleAddButton(MouseEvent e) {
    	System.out.println("Added!");
    	if (!artistField.getText().trim().isEmpty() && !songField.getText().trim().isEmpty()) {
    		String entry = songField.getText().trim() + " " + artistField.getText().trim();
    		//(if observable list contains item, alert!)
    		if (!albumField.getText().trim().isEmpty()) {
    			entry = entry + " " + albumField.getText().trim();
    		}
    		if (!yearField.getText().trim().isEmpty()) {
    			entry = entry + " " + yearField.getText().trim();
    		}
    		if (entry.contains("|")) {
    			Alert a = new Alert(AlertType.WARNING);
    			a.setContentText("Unrecognized Character.");
    			a.show();
    		}
    		else {
    			Alert a = new Alert(AlertType.CONFIRMATION);
    			a.setContentText("Are you sure you want to add this song to your library?");
    			a.show();
    		}
    		System.out.println(entry);
    	}
    	else {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Each song needs a name and artist.");
    		a.show();
    	}
    }
}
