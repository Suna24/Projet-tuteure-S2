package Version_Enseignant.All_Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Page_Accueil implements Initializable{

	@FXML Label RecupScene;

	//M�thode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	//Bouton Quitter qui permet � l'enseignant de quitter l'application (disponible sur toutes les pages)
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	//Bouton Ouvrir qui permet � l'enseignant d'ouvrir un exercice qu'il � d�j� cr�� auparavant
	@FXML
	public void ouvrir(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Ouvrez votre exercice");
		//Appel de la fonction d�crypte pour la fichier s�lectionn�
		decrypte(fileChooser.showOpenDialog(null));
		//TODO Chargez l'exercice dans la page
	}

	//Fonction qui va load les informations du fichier s�lectionn� dans les diff�rents TextField...
	public void decrypte(File file) throws IOException {

		//Variables pour r�cup�rer les informations du fichier
		String consigne, aide, transcription, caraOccul, nbMin;
		int nombreOctetALire, sensiCasse, mode, solution, motsDecouverts;

		//On ouvre le fichier en lecture
		FileInputStream fin = new FileInputStream(file);

		//On r�cup�re la longueur de la consigne + la consigne
		nombreOctetALire = entier(ByteBuffer.wrap(fin.readNBytes(4)));
		consigne = chaine(fin.readNBytes(nombreOctetALire));
		//On met la consigne dans la textField associ�
		Controller_Page_Apercu.contenuConsigne = consigne;

		//On r�cup�re la longueur de la transcription + la transcription
		nombreOctetALire = entier(ByteBuffer.wrap(fin.readNBytes(4)));
		transcription = chaine(fin.readNBytes(nombreOctetALire));
		//On met la transcription dans le textField associ�
		Controller_Page_Apercu.contenuTranscription = transcription;

		//On r�cup�re la longueur de l'aide + l'aide
		nombreOctetALire = entier(ByteBuffer.wrap(fin.readNBytes(4)));
		aide = chaine(fin.readNBytes(nombreOctetALire));
		//On met les aides dans le textField associ�
		Controller_Page_Apercu.contenuAide = aide;

		//On r�cup�re le caract�re d'occultation
		caraOccul = chaine(fin.readNBytes(1));
		//On met le caract�re dans le texField associ�
		Controller_Page_Des_Options.caraOccul = caraOccul;

		//On r�cup�re la reponse de sensiCasse 0 = false, 1 = true
		sensiCasse = entier(ByteBuffer.wrap(fin.readNBytes(1)));
		//On met la variable associ�e en fonction de la r�ponse
		if(sensiCasse == 1) {
			Controller_Page_Des_Options.sensiCasse = true;
		} else {
			Controller_Page_Des_Options.sensiCasse = false;
		}

		//On r�cup�re le mode choisi par l'enseignant 0 = entrainement, 1 = evaluation
		mode = entier(ByteBuffer.wrap(fin.readNBytes(1)));

		//On met la variable associ�e en fonction de la r�ponse
		//Mode Evaluation
		if(mode == 1) {
			Controller_Page_Des_Options.evaluation = true;
			Controller_Page_Des_Options.entrainement = false;

			nombreOctetALire = entier(ByteBuffer.wrap(fin.readNBytes(4)));
			nbMin = chaine(fin.readNBytes(nombreOctetALire));

			Controller_Page_Des_Options.nbMin = nbMin;
			
			//Mode Entrainement
		} else {
			Controller_Page_Des_Options.evaluation = false;
			Controller_Page_Des_Options.entrainement = true;
			
			
		}




		//Fermeture du fichier
		fin.close();
	}

	//Fonction qui transforme un byteBuffer en Int
	public int entier(ByteBuffer bytes) {
		return bytes.getInt();
	}

	//Fonction qui converti des bytes en String
	public String chaine(byte[] bytes) {
		//Variable qui contiendra la chaine
		String chaine = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
		return chaine;
	}

	//Bouton Nouveau qui permet de cr�er un nouvel exercice
	@FXML
	public void pageNouvelExo() throws IOException {
		Stage primaryStage = (Stage) RecupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton Pr�f�rences qui emm�ne sur la page des param�tres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) RecupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton DarkMode qui met en darkMode l'application
	@FXML 
	public void darkMode() {
		//TODO faire le DarkMode
	}

}
