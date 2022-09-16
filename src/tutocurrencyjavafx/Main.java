package tutocurrencyjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author amine gasa
 */
public class Main extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        launch(args);
       }

    @Override
    public void start(Stage stage) throws Exception {
      Parent root=FXMLLoader.load(getClass()
              .getResource("/tutocurrencyjavafx/views/Dashboard.fxml"));
      String css=this.getClass().getResource(
               "/tutocurrencyjavafx/views/css/styling.css").toExternalForm();
        Scene scene=new  Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle("Currency Changing");
        stage.show();
    }

   
  
    
}
