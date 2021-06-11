package Version_Enseignant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainEnseignant extends Application {
	
	public static Parent root;
	
	//Param�tres de taille d'�cran
	public static double width = 1200;
	public static double height = 800;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
         root = FXMLLoader.load(getClass().getResource("FXML_Files/Menu.fxml"));
         primaryStage.setTitle("Reconstitution - Version Enseignante");
         primaryStage.getIcons().add(new Image("/Image/Logo_Reconstitution.png"));
         
         //On affiche le plein �cran
   
         primaryStage.setScene(new Scene(root, 1200, 800));
         primaryStage.setMaximized(true);
         primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
 
}
