package Version_Enseignant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainEnseignant extends Application {
	
	public static Parent root;
	
	//Param�tres de taille d'�cran
	public static double width;
	public static double height;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
         root = FXMLLoader.load(getClass().getResource("FXML_Files/PageAccueil.fxml"));
         primaryStage.setTitle("Reconstitution - Version Enseignante");
         
         //On affiche le plein �cran
         primaryStage.setMaximized(true);

         //On r�cup�re la largeur et la hauteur de l'�cran
         Rectangle2D screenBounds = Screen.getPrimary().getBounds();
         width=screenBounds.getWidth();
         height=screenBounds.getHeight();
         
         primaryStage.setScene(new Scene(root, width, height));
         primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
 
}
