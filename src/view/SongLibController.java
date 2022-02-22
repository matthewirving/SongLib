/*
	Authors: Matt Irving & Will Knox


*/
package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ListEntry;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private TextField songField;

    @FXML
    private TextField yearField;
    
    @FXML
    private MenuItem helpButton;
    
    
    
    public void handleDebugButton(Event e) {
    	sortEntryList();
    	updateList();
    	
    }
    
    
    public void handleHelpButton(Event e) {
    	Alert a = new Alert(AlertType.INFORMATION, 
    			"The Song List is stored in a csv file, SongList.csv, which uses | as a delimiter between fields for each entry in the song list, and || as a delimiter between actual song entries. "
    			+ "If you want to directly write data to the csv file, please make sure that each entry has its own line, and there is whitespace between the text entered and the delimiters, or else it might not detect the songs properly. Here is an example of some entries as you would see it in the list: \n"
    			+ "Song Name | Artist Name | Album Name | Year Published || \n"
    			+ "FUN | Spongebob | The FUN Album | 2003 || ");
    	a.show();
    }
    
    public void handleListSelection(Event e) {
    	int currIndex = listView.getSelectionModel().selectedIndexProperty().getValue();
    	if(currIndex == -1)
    	{
    		songField.setText("");
        	artistField.setText("");
        	albumField.setText("");
        	yearField.setText("");
    	} else {
    	songField.setText(masterList.get(currIndex).getSongName().trim());
    	artistField.setText(masterList.get(currIndex).getArtistName().trim());
    	albumField.setText(masterList.get(currIndex).getAlbumName().trim());
    	yearField.setText(masterList.get(currIndex).getAlbumYear().trim());
    	}
    	
    }
    
    public void handleDeleteButton(Event e) {
    	Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this song entry? This cannot be undone.", ButtonType.YES, ButtonType.NO);
    	
    	ButtonType result = a.showAndWait().orElse(ButtonType.NO);
    	if(ButtonType.YES.equals(result)) {
    		int currIndex = listView.getSelectionModel().getSelectedIndex();
    		masterList.remove(currIndex);
    		updateList();
    		try {
    		listView.getSelectionModel().select(currIndex);
    		
    		} catch (IndexOutOfBoundsException ex) {
    			listView.getSelectionModel().select(currIndex + 1);
    		}
    		handleListSelection(null);
    	}
    }
    
    public void handleApplyButton(Event e) {
    	if (!artistField.getText().trim().isEmpty() && !songField.getText().trim().isEmpty()) {
    		String entry = songField.getText().trim() + " " + artistField.getText().trim() + albumField.getText().trim() + " " + yearField.getText().trim();
    		//(if observable list contains item, alert!)
    		
    		if (entry.contains("|")) {
    			Alert a = new Alert(AlertType.WARNING);
    			a.setContentText("Unrecognized Character.");
    			a.show();
    		} else if(!onlyDigits(yearField.getText().trim())){
    			
    			Alert a = new Alert(AlertType.ERROR, "Please enter a valid year; a valid entry contains only integers with no other characters");
    			a.show();
    			
    		} else {
    			
    			Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to make these edits to the selected song?", ButtonType.YES, ButtonType.NO);
    			
    			
    			
    			ButtonType result = a.showAndWait().orElse(ButtonType.NO);
    			if(ButtonType.YES.equals(result)) {
    				
    				int currIndex = listView.getSelectionModel().getSelectedIndex();
    				ListEntry temp = new ListEntry(songField.getText().trim(), artistField.getText().trim(), albumField.getText().trim(), yearField.getText().trim());
    				masterList.set(currIndex, temp);
    	    		updateList();
    	    		listView.getSelectionModel().select(masterList.indexOf(temp));
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
    		String entry = songField.getText().trim() + " " + artistField.getText().trim() + albumField.getText().trim() + " " + yearField.getText().trim();
    		//(if observable list contains item, alert!)
    		
    		if (entry.contains("|")) {
    			Alert a = new Alert(AlertType.WARNING);
    			a.setContentText("Unrecognized Character.");
    			a.show();
    		}
    		else if(isRepeat()) {
    			
    			Alert a = new Alert(AlertType.ERROR, "Songs cannot share the same song and artist name as one already on the list");
    			a.show();
    		} else if(!onlyDigits(yearField.getText().trim())){
    			
    			Alert a = new Alert(AlertType.ERROR, "Please enter a valid year; a valid entry contains only integers with no other characters");
    			a.show();
    			
    		} else {
    			
    			Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to add this song to your library?", ButtonType.YES, ButtonType.NO);
    			ButtonType result = a.showAndWait().orElse(ButtonType.NO);
    			if(ButtonType.YES.equals(result)) {
    				System.out.println(entry);
    	    		ListEntry temp = new ListEntry(songField.getText().trim(), artistField.getText().trim(), albumField.getText().trim(), yearField.getText().trim()); 
    				masterList.add(temp);
    	    		updateList();
    	    		
    	    		
    	    		listView.getSelectionModel().select(masterList.indexOf(temp));
    	    		handleListSelection(null);
    	    		
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
    	handleListSelection(null);
    	System.out.println(listView.getSelectionModel().selectedIndexProperty());
    	
    }
    
    private ObservableList<String> obsList = FXCollections.observableArrayList();
    
    
    private boolean isRepeat() {
    	for(ListEntry en : masterList)
    	{
    		if(en.getArtistName().trim().equalsIgnoreCase(artistField.getText().trim()) && en.getSongName().trim().equalsIgnoreCase(songField.getText().trim())) {
    			return true;
    		}
    	}
    	return false;
    }
    
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
	
	public static boolean onlyDigits(String str){
		
		String regex = "[0-9]+";
		
		Pattern p = Pattern.compile(regex);
		
		if(str.equals(null) || str.equals(""))
			return true;
		
		Matcher m = p.matcher(str);
		
		return m.matches();
	}
}

    