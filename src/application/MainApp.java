package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainApp extends Application {

	private Stage primaryStage;
	// ysa

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Tatai!");

		Parent root = FXMLLoader.load(getClass().getResource("view/Username_Scene.fxml"));
		// Show the scene containing the root layout
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args){
		File file = new File("custom_equations.txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		new File("custom_questionaires").mkdir();
		File yourFile = new File("scores.txt");
		try {
			yourFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
	}
	

	// comment
}
