package Version_Enseignant;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.*;

public class Controller {
	
	//Bouton Quitter qui permet � l'enseignant de quitter l'application
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}
	
	//Bouton Ouvrir qui permet � l'enseignant d'ouvrir un exercice qu'il � d�j� cr�� auparavant
	@FXML
	public void ouvrir(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage primaryStage = null;
        fileChooser.setTitle("Open Resource File");
		fileChooser.showOpenDialog(primaryStage);
		//TODO Chargez l'exercice dans la page
	}
	
	//Bouton Nouveau qui permet � l'enseignant de d�marrer la cr�ation d'un nouvel exercice
	@FXML
	public void nouvelExo(ActionEvent event) {
		
	}
	
}
