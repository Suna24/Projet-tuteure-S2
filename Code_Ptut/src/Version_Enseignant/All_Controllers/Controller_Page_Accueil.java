package Version_Enseignant.All_Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Page_Accueil implements Initializable {

	@FXML
	Text RecupScene;

	// M�thode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	// Bouton Quitter qui permet � l'enseignant de quitter l'application (disponible
	// sur toutes les pages)
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	// Bouton Ouvrir qui permet � l'enseignant d'ouvrir un exercice qu'il � d�j�
	// cr�� auparavant
	@FXML
	public void ouvrir(ActionEvent event) throws IOException {
		
		FileChooser fileChooser = new FileChooser();
		File selectedFile = new File("");
		fileChooser.setTitle("Ouvrez votre exercice");
		
		// Appel de la fonction d�crypte pour la fichier s�lectionn�
		selectedFile = fileChooser.showOpenDialog(null);
		decrypte(selectedFile);
		
		//On met le nom du fichier dans le TextField associ�
		Controller_Nouvel_Exo.contenuNomExo = stripExtension(selectedFile);
		
		//On met le r�pertoire du fichier dans le TextField associ�
		Controller_Nouvel_Exo.contenuRepertoire = stripPath(selectedFile);
		
		//On load la page d'apr�s
		pageNouvelExo();
	}

	//M�thode qui va enlever l'extension du fichier
	public static String stripExtension(File file) {
        if (file == null) {
            return null;
        }
        String name = file.getName();

        int posPoint = name.lastIndexOf(".");

        if (posPoint == -1) {
            return name;
        }

        return name.substring(0, posPoint);
    }
	
	public static String stripPath(File file) {
        if (file == null) {
            return null;
        }
        String name = file.getAbsolutePath();

        int posPoint = name.lastIndexOf("\\");

        if (posPoint == -1) {
            return name;
        }

        return name.substring(0, posPoint);
    }
	
	// Fonction qui va load les informations du fichier s�lectionn� dans les
	// diff�rents TextField...
	public void decrypte(File file) throws IOException {

		// Variables pour r�cup�rer les informations du fichier
		String consigne, aide, transcription, caraOccul, nbMin;
		int nombreOctetALire, sensiCasse, mode, solution, motsDecouverts, motsIncomplets, lettre, extension;
		File tmpFile;

		// On ouvre le fichier en lecture
		FileInputStream fin = new FileInputStream(file);

		// On r�cup�re la longueur de la consigne + la consigne
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		consigne = chaine(fin.readNBytes(nombreOctetALire));
		// On met la consigne dans la textField associ�
		Controller_Page_Apercu.contenuConsigne = consigne;

		// On r�cup�re la longueur de la transcription + la transcription
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		transcription = chaine(fin.readNBytes(nombreOctetALire));
		// On met la transcription dans le textField associ�
		Controller_Page_Apercu.contenuTranscription = transcription;

		// On r�cup�re la longueur de l'aide + l'aide
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		aide = chaine(fin.readNBytes(nombreOctetALire));
		// On met les aides dans le textField associ�
		Controller_Page_Apercu.contenuAide = aide;

		// On r�cup�re le caract�re d'occultation
		caraOccul = chaine(fin.readNBytes(1));
		// On met le caract�re dans le texField associ�
		Controller_Page_Des_Options.caraOccul = caraOccul;

		// On r�cup�re la reponse de sensiCasse 0 = false, 1 = true
		sensiCasse = ByteBuffer.wrap(fin.readNBytes(1)).get();

		// On met la variable associ�e en fonction de la r�ponse
		if (sensiCasse == 1) {
			Controller_Page_Des_Options.sensiCasse = true;
		} else {
			Controller_Page_Des_Options.sensiCasse = false;
		}

		// On r�cup�re le mode choisi par l'enseignant 0 = entrainement, 1 = evaluation
		mode = ByteBuffer.wrap(fin.readNBytes(1)).get();

		// On met la variable associ�e en fonction de la r�ponse
		// Mode Evaluation
		if (mode == 1) {
			Controller_Page_Des_Options.evaluation = true;
			Controller_Page_Des_Options.entrainement = false;

			nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
			nbMin = chaine(fin.readNBytes(nombreOctetALire));

			Controller_Page_Des_Options.nbMin = nbMin;

			// Mode Entrainement
		} else {
			Controller_Page_Des_Options.evaluation = false;
			Controller_Page_Des_Options.entrainement = true;

			// On r�cup�re la reponse de l'affiche de la solution 0 = false, 1 = true
			solution = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associ�e en fonction de la r�ponse
			if (solution == 1) {
				Controller_Page_Des_Options.solution = true;
			} else {
				Controller_Page_Des_Options.solution = false;
			}

			// On r�cup�re la reponse de l'affiche du nombre de mots d�couverts en temps
			// r�el 0 = false, 1 = true
			motsDecouverts = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associ�e en fonction de la r�ponse
			if (motsDecouverts == 1) {
				Controller_Page_Des_Options.motDecouverts = true;
			} else {
				Controller_Page_Des_Options.motDecouverts = false;
			}

			// On r�cup�re la reponse de l'autorisation du nb min de lettre pour d�couvrir
			// le mot 0 = false, 1 = true
			motsIncomplets = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associ�e en fonction de la r�ponse
			if (motsIncomplets == 1) {
				Controller_Page_Des_Options.motIncomplet = true;

				// On r�cup�re la reponse du nb min de lettre pour d�couvrir le mot 2 = 2
				// lettres, 3 = 3 lettres
				lettre = ByteBuffer.wrap(fin.readNBytes(1)).get();

				// On met la variable associ�e en fonction de la r�ponse
				if (lettre == 2) {
					Controller_Page_Des_Options.lettres_2 = true;
					Controller_Page_Des_Options.lettres_3 = false;
				} else {
					Controller_Page_Des_Options.lettres_2 = false;
					Controller_Page_Des_Options.lettres_3 = true;
				}

			} else {
				Controller_Page_Des_Options.motIncomplet = false;
				Controller_Page_Des_Options.lettres_2 = false;
				Controller_Page_Des_Options.lettres_3 = false;
			}
		}
		
		//On regarde l'extension du media
		extension = ByteBuffer.wrap(fin.readNBytes(1)).get();
		
		//Si c'est un mp3, on doit d�chiffrer l'image
		if(extension == 0) {
			
			nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(8)).getInt();
			
			File tmpFileImage = File.createTempFile("data", ".png");
			FileOutputStream ecritureFileImage = new FileOutputStream(tmpFileImage);
			ecritureFileImage.write(fin.readNBytes(nombreOctetALire));
			ecritureFileImage.close();
			
			Controller_Importer_Ressource.contenuImage = new Image(tmpFileImage.toURI().toString());
			
			//On efface le fichier temporaire
			tmpFileImage.deleteOnExit();
			
			//On lit le mp3
			nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(8)).getInt();
			
			tmpFile = File.createTempFile("data", ".mp3");

		} 
		//Sinon c'est un mp4
		else {
			
			//On r�cup�re ensuite le media
			nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(8)).getInt();
			
			tmpFile = File.createTempFile("data", ".mp4");

		}
		
		FileOutputStream ecritureFile = new FileOutputStream(tmpFile);
		ecritureFile.write(fin.readAllBytes());
		ecritureFile.close();
		
		Controller_Importer_Ressource.contenuMedia = new Media(tmpFile.toURI().toString());
		
		//On efface le fichier temporaire
		tmpFile.deleteOnExit();
		
		// Fermeture du fichier
		fin.close();
	}

	// Fonction qui converti des bytes en String
	public String chaine(byte[] bytes) {
		// Variable qui contiendra la chaine
		String chaine = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
		return chaine;
	}

	// Bouton Nouveau qui permet de cr�er un nouvel exercice
	@FXML
	public void pageNouvelExo() throws IOException {
		Stage primaryStage = (Stage) RecupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setMaximized(true);
		primaryStage.show();

	}

	// Bouton Pr�f�rences qui emm�ne sur la page des param�tres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) RecupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	// Bouton DarkMode qui met en darkMode l'application
	@FXML
	public void darkMode() {
		// TODO faire le DarkMode
	}

}
