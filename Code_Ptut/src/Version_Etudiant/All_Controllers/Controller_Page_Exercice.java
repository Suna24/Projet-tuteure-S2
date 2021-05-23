package Version_Etudiant.All_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller_Page_Exercice implements Initializable{

	//Variables qui vont contenir les diff�rentes informations sur l'exercice
	//Informations textuelles
	public static String contenuTranscription;
	public static String contenuConsigne;
	public static Media contenuMedia;
	public static String caractereOccul;

	//Options
	public static boolean sensiCasse;
	public static boolean entrainement;
	public static boolean evaluation;
	public static String nbMin;
	public static boolean solution;
	public static boolean motDecouverts;
	public static boolean motIncomplet;
	public static boolean lettres_2;
	public static boolean lettres_3;
	public static Image contenuImage;

	//TextFields et autre composants qui contiennent les informations de l'exercice
	@FXML private TextArea transcription;
	@FXML private TextArea consigne;
	@FXML private ImageView imageView;
	@FXML private MediaView mediaView;
	@FXML private Text time;
	@FXML private Label titleTime;
	@FXML private TextField motPropose;

	@FXML private Button playPause;
	MediaPlayer mediaPlayer = new MediaPlayer(contenuMedia);
	
	private ArrayList<String> lesMots = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		int i;
		String mot = "";

		transcription.setWrapText(true);

		//On load les informations de l'exercice dans les composants
		if(contenuTranscription != null) {

			//Pour la transcription, on utilise le caract�re d'occultation
			for(i = 0; i < contenuTranscription.length(); i++) {
				
				//Si cela correspond bien � la regex, on occulte le mot
				if(okRegex(contenuTranscription.charAt(i)) == true) {
					transcription.setText(transcription.getText() + caractereOccul);
					mot += contenuTranscription.charAt(i);
				} else {
					if(mot != "") {
						lesMots.add(mot);
					}
					transcription.setText(transcription.getText() + contenuTranscription.charAt(i));
					mot = "";
				}
			}	
		}


		//On load la consigne
		if(contenuConsigne != null) {
			consigne.setText(contenuConsigne);
		}

		//On load le media
		if(contenuMedia != null) {
			mediaView.setMediaPlayer(mediaPlayer);
		}

		//On load l'image quand il s'agit d'un mp3
		if(contenuImage != null) {
			imageView.setImage(contenuImage);
		}
		
		//On load le temps n�cessaire si c'est en mode Evaluation
		if(evaluation == true) {
			time.setText(nbMin);
		} 
		//Sinon cela veut dire que l'on est en mode Entrainement
		else {
			titleTime.setText("Temps Ecoul�");
			time.setText("0");
		}


		//On fait appara�tre une fen�tre pour que l'�tudiant rentre son nom et pr�nom en vue du futur enregistrement
		try {
			popUpEnregistrement();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Fonction qui play/pause le media
	@FXML
	public void playMedia(ActionEvent event) {
		//Si on veut mettre en pause la vid�o
		if(playPause.getText().compareTo("Play") == 0) {
			playPause.setText("Pause");
			mediaPlayer.play();
		} else {
			playPause.setText("Play");
			mediaPlayer.pause();
		}

	}

	//Fonction qui regarde si le caract�re est compatible avec la regex (toutes les lettres et chiffres)
	private boolean okRegex(char caractere) {
		String s = "" + caractere;
		if(s.matches("[a-zA-Z0-9]")) {
			return true;
		} 
		return false;
	}

	//M�thode qui fait appara�tre la popUp pour que l'�tudiant rentre ses infos pour l'enregistrement
	public void popUpEnregistrement() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/EnregistrementApresouverture.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Enregistrement futur");
		//On bloque sur cette fen�tre
		stage.initModality(Modality.APPLICATION_MODAL);
		//On bloque le resize
		stage.setResizable(false);
		stage.setScene(new Scene(root, 500, 300));
		stage.show();
	}
	
	//M�thode pour quitter l'application
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}
	
	//M�thode qui permet � l'�tudiant de proposer un mot, et affichage ou non dans le texte occult� si le mot est pr�sent
	@FXML
	public void propositionMot(ActionEvent event) {
		
		String mot = motPropose.getText();
		int longueurMot = mot.length(), index = 0;
		
		for(String motCompa : lesMots) {
			if(motCompa.compareTo(mot) == 0) {
				System.out.println("Mot trouv� : " + mot);
			}
		}
		
		motPropose.setText(null);
		
		for(String word : lesMots) {
			System.out.print(word + " ");
		}
		
	}

}
