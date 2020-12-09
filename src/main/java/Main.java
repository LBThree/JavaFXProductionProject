

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The driver class for the Production Project.
 *
 * @author Logan Bahr
 */
public class Main extends Application {

  /**
   * The main method for the driver class.
   *
   * @param args the arguments provided to the main method.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Sets up the GUI.
   *
   * @param primaryStage GUI
   * @throws Exception GUI crashing
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
    Scene scene = new Scene(root, 500, 500);
    primaryStage.setTitle("Inventory System");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

}
