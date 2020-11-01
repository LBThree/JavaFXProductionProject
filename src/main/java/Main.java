/**************************************************************************************************
 *  Author:   Logan Bahr
 *  Created:  9/19/20
 *  Purpose:  Main method to start the initial GUI applet which will be ran through 'Controller'
 **************************************************************************************************/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
    Scene scene = new Scene(root, 500, 500);
    primaryStage.setTitle("Inventory System");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
