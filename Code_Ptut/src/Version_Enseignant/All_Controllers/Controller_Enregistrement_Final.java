package Version_Enseignant.All_Controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class Controller_Enregistrement_Final implements Initializable {

	@FXML
	private Text recupScene;

	// M�thode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Conteneurs en octets de la consigne
		byte[] contenuConsigne = Controller_Page_Apercu.contenuConsigne.getBytes();
		byte[] longueurConsigne = getLongueur(Controller_Page_Apercu.contenuConsigne);

		// Conteneurs en octets de la transcription
		byte[] contenuTranscription = Controller_Page_Apercu.contenuTranscription.getBytes();
		byte[] longueurTranscription = getLongueur(Controller_Page_Apercu.contenuTranscription);

		// Conteneurs en octets de l'aide
		byte[] contenuAide = Controller_Page_Apercu.contenuAide.getBytes();
		byte[] longueurAide = getLongueur(Controller_Page_Apercu.contenuAide);

		// Caract�re d'occultation
		byte[] caraOccul = Controller_Page_Des_Options.caraOccul.getBytes();

		// Conteneurs du media
		byte[] contenuMedia = null;
		byte[] longueurMedia = null;
		
		//Conteneur de l'image (pour mp3)
		byte[] contenuImage = null;
		byte[] longueurImage = null;

		// On r�cup�re le media / image et on lui demande sa taille
		try {
			contenuMedia = URI.create(Controller_Importer_Ressource.contenuMedia.getSource()).toURL().openStream().readAllBytes();
			longueurMedia = ByteBuffer.allocate(8).putInt(contenuMedia.length).array();
		
		if(Controller_Importer_Ressource.contenuImage != null) {
			contenuImage = URI.create(Controller_Importer_Ressource.contenuImage.getUrl()).toURL().openStream().readAllBytes();
			longueurImage = ByteBuffer.allocate(8).putInt(contenuImage.length).array();
		}

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			// On ouvre un fichier o� on va enregistrer les informations
			// On lui donne l'endroit o� il doit �tre enregistr� et le nom
			File file = new File(Controller_Nouvel_Exo.contenuRepertoire, Controller_Nouvel_Exo.contenuNomExo + ".bin");
			FileOutputStream out = new FileOutputStream(file);

			// On y �crit la consigne
			out.write(longueurConsigne);
			out.write(contenuConsigne);

			// On y �crit la transcription
			out.write(longueurTranscription);
			out.write(contenuTranscription);

			// On y �crit les aides
			out.write(longueurAide);
			out.write(contenuAide);

			// On y �crit le caract�re d'occultation
			out.write(caraOccul);

			// Si la sensibilit� � la casse est activ�e ou non
			if (Controller_Page_Des_Options.sensiCasse == true) {
				out.write(1);
			} else {
				out.write(0);
			}

			// On y �crit le mode (sur 1 octet)
			// Si c'est le mode entrainement
			if (Controller_Page_Des_Options.entrainement == true) {
				out.write(0);

				// Si l'affichage de la solution est autoris�
				if (Controller_Page_Des_Options.solution == true) {
					out.write(1);
				} else {
					out.write(0);
				}

				// Si l'affiche du nombre de mots d�couverts en temps r�el est autoris�
				if (Controller_Page_Des_Options.motDecouverts == true) {
					out.write(1);
				} else {
					out.write(0);
				}

				// Si les mots incomplets sont autoris�s
				if (Controller_Page_Des_Options.motIncomplet == true) {
					out.write(1);
					
					//On pr�cise le nombre de lettres autoris�es
					if (Controller_Page_Des_Options.lettres_2 == true) {
						out.write(2);
					} else {
						out.write(3);
					}
					
				} else {
					out.write(0);
				}
			}

			// Sinon il s'agit du mode evaluation
			else {
				
				// Limite de temps (pour le mode Evaluation)
				byte[] nbMin = Controller_Page_Des_Options.nbMin.getBytes();
				byte[] longueurNbMin = getLongueur(Controller_Page_Des_Options.nbMin);
				
				out.write(1);
				// On �crit la limite de temps
				out.write(longueurNbMin);
				out.write(nbMin);
			}
			
			//S'il s'agit d'une extension mp3
			if(getExtension(Controller_Importer_Ressource.contenuMedia.getSource()).compareTo(".mp3") == 0) {
				out.write(0);
				out.write(longueurImage);
				out.write(contenuImage);
			}
			//S'il s'agit d'une extension mp4
			else {
				out.write(1);
			}

			// On y �crit les donn�es du media
			out.write(longueurMedia);
			out.write(contenuMedia);

			// Fermeture du fichier
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//On remet toutes les variables statiques � null
		Controller_Nouvel_Exo.contenuNomExo = null;
		Controller_Nouvel_Exo.contenuRepertoire = null;
		Controller_Importer_Ressource.contenuMedia = null;
		Controller_Importer_Ressource.contenuImage = null;
		Controller_Page_Apercu.contenuAide = null;
		Controller_Page_Apercu.contenuConsigne = null;
		Controller_Page_Apercu.contenuTranscription = null;
		Controller_Page_Des_Options.caraOccul = null;
		Controller_Page_Des_Options.sensiCasse = false;
		Controller_Page_Des_Options.entrainement = false;
		Controller_Page_Des_Options.evaluation = false;
		Controller_Page_Des_Options.lettres_2 = false;
		Controller_Page_Des_Options.lettres_3 = false;
		Controller_Page_Des_Options.motDecouverts = false;
		Controller_Page_Des_Options.motIncomplet = false;
		Controller_Page_Des_Options.solution = false;
		Controller_Page_Des_Options.nbMin = null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////       METHODES GENERALES         /////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

	// Bouton Quitter qui permet � l'enseignant de quitter l'application (disponible
	// sur toutes les pages)
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}
	
	@FXML
	public void okay() {
		recupScene.getScene().getWindow().hide();
	}

	//M�thode qui r�cup�re la lonngueur en byte d'une chaine de caract�re
	private byte[] getLongueur(String chaine) {

		int nbCara = 0;

		for (int i = 0; i < chaine.length(); i++) {
			nbCara++;
		}
		return ByteBuffer.allocate(4).putInt(nbCara).array();
	}
	
	private String getExtension(String filePath) {
        if (filePath == null) {
            return null;
        }
        int posPoint = filePath.lastIndexOf(".");

        if (posPoint == -1) {
            return null;
        }

        return filePath.substring(posPoint);
    }

}
