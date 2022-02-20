package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.SongLibController;

public class SongLib extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create FXML loader
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SongLibFXML.fxml"));
		
		// load fmxl, root layout manager in fxml file is GridPane
		VBox root = (VBox)loader.load();

		
		
		SongLibController songlibController = loader.getController();
		songlibController.start(primaryStage);
		
		// set scene to root
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Song Library Application");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
