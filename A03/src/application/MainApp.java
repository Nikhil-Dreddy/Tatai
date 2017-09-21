package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainApp extends Application {

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try{
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Tatai!");
			
			Parent root = FXMLLoader.load(getClass().getResource("view/Menu.fxml"));
			
			// Show the scene containing the root layout
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		launch(args);
	}
	// comment
}
