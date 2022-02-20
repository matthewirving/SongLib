package view;


import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ListEntry;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
    private ListView<String> listView;

    @FXML
    private TextField searchField;

    @FXML
    private TextField songField;

    @FXML
    private TextField yearField;
    
    
    public void handleListSelection(Event e) {
    	int currIndex = listView.getSelectionModel().selectedIndexProperty().getValue();
    	songField.setText(masterList.get(currIndex).getSongName().trim());
    	artistField.setText(masterList.get(currIndex).getArtistName().trim());
    	albumField.setText(masterList.get(currIndex).getAlbumName().trim());
    	yearField.setText(masterList.get(currIndex).getAlbumYear().trim());
    	
    }
    
    public void handleDeleteButton(Event e) {
    	System.out.println("Deleted!");
    }
    
    public void handleApplyButton(Event e) {
    	if (!artistField.getText().trim().isEmpty() && !songField.getText().trim().isEmpty()) {
    		String entry = songField.getText().trim() + " " + artistField.getText().trim();
    		//(if observable list contains item, alert!)
    		
    		if (entry.contains("|")) {
    			Alert a = new Alert(AlertType.WARNING);
    			a.setContentText("Unrecognized Character.");
    			a.show();
    		}
    		else {
    			Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to make these edits to the selected song?", ButtonType.YES, ButtonType.NO);
    			
    			ButtonType result = a.showAndWait().orElse(ButtonType.NO);
    			if(ButtonType.YES.equals(result)) {
    				System.out.println(entry);
    	    		masterList.add(new ListEntry(songField.getText().trim(), artistField.getText().trim(), albumField.getText().trim(), yearField.getText().trim()));
    	    		updateList();
    			}
    		}
    		
    	}
    	else {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Each song needs a name and artist.");
    		a.show();
    	}
    }
    
    public void handleAddButton(Event e) {
    	System.out.println("Added!");
    	if (!artistField.getText().trim().isEmpty() && !songField.getText().trim().isEmpty()) {
    		String entry = songField.getText().trim() + " " + artistField.getText().trim();
    		//(if observable list contains item, alert!)
    		
    		if (entry.contains("|")) {
    			Alert a = new Alert(AlertType.WARNING);
    			a.setContentText("Unrecognized Character.");
    			a.show();
    		}
    		else {
    			Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to add this song to your library?", ButtonType.YES, ButtonType.NO);
    			
    			ButtonType result = a.showAndWait().orElse(ButtonType.NO);
    			if(ButtonType.YES.equals(result)) {
    				System.out.println(entry);
    	    		masterList.add(new ListEntry(songField.getText().trim(), artistField.getText().trim(), albumField.getText().trim(), yearField.getText().trim()));
    	    		updateList();
    			}
    		}
    		
    	}
    	else {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Each song needs a name and artist.");
    		a.show();
    	}
    }

    ArrayList<ListEntry> masterList = new ArrayList<>();
    
    public void start(Stage mainStage) {
    	//create ObservableList from ArrayList
    	readCSV();
    	updateList();
    	
    	listView.getSelectionModel().select(0);
    	System.out.println(listView.getSelectionModel().selectedIndexProperty());
    	
    }
    
    private ObservableList<String> obsList = FXCollections.observableArrayList();
    
    
    
    
    private void updateList()
    {
    	obsList.clear();
    	
    	sortEntryList();
    	
    	for(ListEntry en : masterList)
    	{
    		obsList.add(en.toString());
    	}
    	
    	listView.setItems(obsList);
    	writeCSV();
    	
    }
    
    
    private void sortEntryList() {
    	
    	Collections.sort(masterList);
    	
    }
    
    
    
    private void readCSV(){
    	String FieldDelimiter = "\\|\\|";
    	
    	BufferedReader br;
    	
    	try {
    		br = new BufferedReader(new FileReader("SongList.csv"));
    		String line;
    		try {
				String[] fields = null;
    			while((line = br.readLine()) != null) {
					//System.out.println(line);
					fields = line.split(FieldDelimiter);
					//obsList.addAll(fields);
					masterList.add(new ListEntry(fields[0]));
					
				}
				br.close();
    		
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    		
    	} 
    	catch (FileNotFoundException ex) {
    			Alert a = new Alert(AlertType.INFORMATION);
    			a.setContentText("No Songs file found, creating a new one.");
    			a.show();
    			
    			File file = new File("SongList.csv");
    			try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    

	private void writeCSV(){
	
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter("SongList.csv"));
			for(ListEntry en : masterList)
			{
				bw.write(en.getSongName().trim() + " | " + en.getArtistName().trim() + " | " + en.getAlbumName().trim() + " | " + en.getAlbumYear().trim() + " ||" + "\n");
				
			}
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}

    