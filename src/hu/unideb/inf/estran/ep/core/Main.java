package hu.unideb.inf.estran.ep.core;

import hu.unideb.inf.estran.ep.view.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {

		launch(args);
	}

	 @Override
	    public void start(Stage stage) throws Exception {

		    Parent root = FXMLLoader.load(getClass().getResource("/hu/unideb/inf/estran/ep/view/View.fxml"));
	        Scene scene = new Scene(root);

	        stage.setResizable(false);
	        stage.setTitle("Evolution Programming - Demo @ Bereczki László 2016");
	        stage.setScene(scene);
	        stage.show();
	    }

	 @Override
     public void stop(){Controller.onExit();}
}