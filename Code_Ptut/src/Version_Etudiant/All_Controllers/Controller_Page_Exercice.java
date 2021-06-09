package Version_Etudiant.All_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Version_Etudiant.DeplacementFenetre;
import Version_Etudiant.MainEtudiant;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Controller_Page_Exercice implements Initializable{

	//Variables qui vont contenir les diff�rentes informations sur l'exercice
	//Informations textuelles
	public static String contenuTranscription;
	public static String contenuConsigne;
	public static Media contenuMedia;
	public static String caractereOccul;

	//Options de l'exercice
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

	//Ce qui concerne le media
	@FXML private ImageView firstPlay;
	@FXML private ImageView playOrPause;
	MediaPlayer mediaPlayer = new MediaPlayer(contenuMedia);
	Image play = new Image("file:./src/Image/Play.png");
	Image pause = new Image("file:./src/Image/Pause.png");
	Image sonCoupe = new Image("file:./src/Image/VolumeCoupe.png");
	Image sonPasCoupe = new Image("file:./src/Image/Volume.png");
	Image reload = new Image("file:./src/Image/Reload.png");
	@FXML private Slider sliderSon;
	@FXML private Slider sliderVideo;
	@FXML private ImageView son;

	//Gestion du timer
	private Timeline timer;
	private Integer sec = 0;
	private Integer min;
	private boolean timerEstDeclenche = false;

	//Autres boutons
	@FXML private Button ButtonAide;
	@FXML private Button ButtonSolution;

	//Listes des mots pour l'�tudiant
	private ArrayList<String> lesMots = new ArrayList<>();
	private ArrayList<String> lesMotsSensiCasse = new ArrayList<>();
	private ArrayList<String> lesMotsEtudiant = new ArrayList<>();

	//Tout ce qui concerne la barre de progression
	@FXML private ProgressBar progressBar;
	@FXML private Label pourcentageMots;
	@FXML private Label labelMotsDecouverts;
	private float nbMotsDecouverts = 0;
	private float nbMotsTotal;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		int i;
		String mot = "", motCrypte = "";

		//On fait en sorte � ce que le texte ne d�passe pas du cadre
		transcription.setWrapText(true);

		//On affiche le texte crypt� dans le TextField
		if(contenuTranscription != null) {

			//Pour la transcription, on utilise le caractere d'occultation
			for(i = 0; i < contenuTranscription.length(); i++) {

				//Si cela correspond bien � la regex, on occulte le mot
				if(okRegex(contenuTranscription.charAt(i)) == true) {
					transcription.setText(transcription.getText() + caractereOccul);
					mot += contenuTranscription.charAt(i);
					motCrypte += caractereOccul;
				} else {
					transcription.setText(transcription.getText() + contenuTranscription.charAt(i));

					if(mot != "") {
						lesMots.add(mot);
						lesMotsEtudiant.add(motCrypte);
					} else {
						lesMots.add(contenuTranscription.charAt(i - 1) + "");
						lesMotsEtudiant.add(contenuTranscription.charAt(i - 1) + "");
					}

					mot = "";
					motCrypte = "";
				}
			}

			//Si on arrive � la fin de la boucle, on ajoute quand meme le dernier mot
			lesMots.add(mot);
			lesMotsEtudiant.add(motCrypte);
		}

		//On passe les mots comparatifs en minuscule dans une autre liste

		for(String word : lesMots) {
			lesMotsSensiCasse.add(word.toLowerCase());

			if(!regexPoint(word)) {
				nbMotsTotal++;
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
			min = Integer.parseInt(nbMin);
			time.setText(min + ":" + sec);

			//On masque les boutons qui ne sont pr�sent que ne mode entrainement
			ButtonAide.setVisible(false);
			ButtonSolution.setVisible(false);
			//Si l'enseignant n'a pas souhait� l'affichage de mots d�couverts en temps r�el
			progressBar.setVisible(false);
			pourcentageMots.setVisible(false);
			labelMotsDecouverts.setVisible(false);

		} 
		//Sinon cela veut dire que l'on est en mode Entrainement
		else {

			titleTime.setText("Temps Ecoul�");
			min = 00;
			time.setText("00:00");

			//Si l'enseignant n'a pas souhait� autoriser l'affichage de la solution
			if(solution == false) {
				ButtonSolution.setVisible(false);
			}

			//Si l'enseignant n'a pas souhait� l'affichage de mots d�couverts en temps r�el
			if(motDecouverts == false) {
				progressBar.setVisible(false);
				pourcentageMots.setVisible(false);
				labelMotsDecouverts.setVisible(false);
			}

		}

		//On fait appara�tre une fen�tre pour que l'�tudiant rentre son nom et pr�nom en vue du futur enregistrement
		try {
			popUpEnregistrement();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sliderSonChange();
		sliderVideoChange();
	}

	public void sliderSonChange() {
		// Change le volume sonore selon la valeur du slider
		sliderSon.valueProperty().addListener((o -> {
			mediaPlayer.setVolume(sliderSon.getValue() / 100.0); 

			if(sliderSon.getValue() == 0) {
				son.setImage(sonCoupe);
			} else {
				son.setImage(sonPasCoupe);
			}
		}));
	}

	//Fonction qui fait avancer le slider en fonction de la video
	public void sliderVideoChange() {

		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {
				sliderVideo.setMax(mediaPlayer.getTotalDuration().toSeconds());
			}
		});

		// Ecoute sur le slider. Quand il est modifi�, modifie le temps du media player.
		InvalidationListener sliderChangeListener = o -> {
			Duration seekTo = Duration.seconds(sliderVideo.getValue());
			mediaPlayer.seek(seekTo);
		};
		sliderVideo.valueProperty().addListener(sliderChangeListener);

		// Lie le temps du media player au slider
		mediaPlayer.currentTimeProperty().addListener(l -> {

			sliderVideo.valueProperty().removeListener(sliderChangeListener);

			// Met a jour la valeur de temps du m�dia avec la position du slider.
			Duration currentTime = mediaPlayer.getCurrentTime();
			sliderVideo.setValue(currentTime.toSeconds());

			// R�activation de l'�coute du slider
			sliderVideo.valueProperty().addListener(sliderChangeListener);

		});
	}

	//Fonction qui permet de mute le son
	@FXML
	public void sonCoupe(MouseEvent event) {

		if(mediaPlayer.getVolume() != 0) {
			son.setImage(sonCoupe);
			mediaPlayer.setVolume(0);
		} else {
			son.setImage(sonPasCoupe);
			mediaPlayer.setVolume(sliderSon.getValue() / 100);
		}

	}

	//Fonction qui lance le media pour la premiere fois 
	@FXML
	public void firstPlay(MouseEvent event) {

		mediaPlayer.play();

		if(timerEstDeclenche == false) {
			gestionTimer();
			timerEstDeclenche = true;
		}

		firstPlay.setVisible(false);
	}

	//Fonction qui play / pause le media
	@FXML
	public void playOrPause(MouseEvent event) {

		if(mediaPlayer.getStatus() == Status.PLAYING) {
			mediaPlayer.pause();
			playOrPause.setImage(play);
		}

		if(mediaPlayer.getStatus() == Status.PAUSED) {
			mediaPlayer.play();
			playOrPause.setImage(pause);
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

	//Fonction qui regarde si le mot contient un caract�re de ponctuation
	private boolean regexPoint(String mot) {

		for(int i = 0; i < mot.length(); i++) {
			if((mot.charAt(i) + "").matches("[.,;!?]")) {
				return true;
			}
		}
		return false;
	}


	//M�thode qui fait appara�tre la popUp pour que l'�tudiant rentre ses infos pour l'enregistrement
	public void popUpEnregistrement() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/EnregistrementApresouverture.fxml"));
		Stage stage = new Stage();
		Rectangle rect = new Rectangle(800,500);
		rect.setArcHeight(20.0);
		rect.setArcWidth(20.0);
		root.setClip(rect);

		//On bloque sur cette fen�tre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root, 800, 500);
		scene.setFill(Color.TRANSPARENT);

		//On bloque le resize
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		DeplacementFenetre.deplacementFenetre((Pane) root, stage);
	}

	//M�thode pur afficher l'aide propos�e par l'enseignant
	@FXML
	public void affichageAide() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/Aides.fxml"));
		Stage stage = new Stage();
		Rectangle rect = new Rectangle(400,600);
		rect.setArcHeight(20.0);
		rect.setArcWidth(20.0);
		root.setClip(rect);

		//On bloque sur cette fen�tre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root, 400, 600);
		scene.setFill(Color.TRANSPARENT);

		stage.setScene(scene);
		stage.show();
		DeplacementFenetre.deplacementFenetre((Pane) root, stage);
	}

	//M�thode pour afficher la solution
	@FXML
	public void affichageSolution() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/Solution.fxml"));
		Stage stage = new Stage();
		Rectangle rect = new Rectangle(600,400);
		rect.setArcHeight(20.0);
		rect.setArcWidth(20.0);
		root.setClip(rect);

		//On bloque sur cette fen�tre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root, 600, 400);
		scene.setFill(Color.TRANSPARENT);

		stage.setScene(scene);
		stage.show();
		DeplacementFenetre.deplacementFenetre((Pane) root, stage);
	}

	//M�thode pour quitter l'application
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	//M�thode qui permet � l'�tudiant de proposer un mot, et affichage ou non dans le texte occult� si le mot est pr�sent
	@FXML
	public void propositionMot(ActionEvent event) throws IOException {

		String mot = motPropose.getText();
		int cpt = 0, remplacementPartiel = 0;

		//On set la variable en fonction du nombre de lettres autoris�es
		if(lettres_2 == true) {
			remplacementPartiel = 1;
		} else if (lettres_3 == true) {
			remplacementPartiel = 2;
		}

		//Si la sensibilit� � la casse n'est pas activ�e
		if(sensiCasse == false) {
			for(int i = 0; i < lesMots.size(); i++) {

				if(lesMots.get(i).compareTo(mot) == 0) {
					lesMotsEtudiant.set(i, mot);
					nbMotsDecouverts++;

					//Gestion de la progressBar
					progressBar.setProgress(nbMotsDecouverts / nbMotsTotal);
					pourcentageMots.setText(Math.round((nbMotsDecouverts / nbMotsTotal) * 100)  + "%");
				}

				//Si le remplacement partiel est autoris�
				if(motIncomplet == true) {

					//Il faut que le mot de l'�tudiant ait une certaine longueur
					if(mot.length() > remplacementPartiel) {

						//Si le mot est plus petit que le mot � d�couvrir
						if(mot.length() < lesMots.get(i).length()) {
							for(int j = 0; j < mot.length(); j++) {
								if(mot.charAt(j) == lesMots.get(i).charAt(j)) {
									cpt ++;
								}
							}
						}

						//Si le mot est plus grand que le mot � d�couvrir
						else {
							for(int j = 0; j > lesMots.get(i).length(); j++) {
								if(mot.charAt(j) == lesMots.get(i).charAt(j)) {
									cpt ++;
								}
							}
						}

						//Si les premi�res lettres sont les m�mes
						if(cpt == mot.length()) {

							//On "crypte" le mot
							String word = mot;

							for(int z = 0; z < lesMots.get(i).length() - mot.length(); z++) {
								word += caractereOccul;
							}
							lesMotsEtudiant.set(i, word);
							//On r�initialise le compteur
							cpt = 0;
						}
					}
				}

			}

		}
		//Si la sensibilit� � la casse est activ�e, on enl�ve les majuscules
		else {
			mot = mot.toLowerCase();
			for(int i = 0; i < lesMotsSensiCasse.size(); i++) {

				if(lesMotsSensiCasse.get(i).compareTo(mot) == 0) {
					lesMotsEtudiant.set(i, lesMots.get(i));
					nbMotsDecouverts++;

					//Gestion de la progressBar
					progressBar.setProgress(nbMotsDecouverts / nbMotsTotal);
					pourcentageMots.setText(Math.round((nbMotsDecouverts / nbMotsTotal) * 100)  + "%");
				}

				//Si le remplacement partiel est autoris�
				if(motIncomplet == true) {

					//Il faut que le mot de l'�tudiant ait une certaine longueur
					if(mot.length() > remplacementPartiel) {

						//Si le mot est plus petit que le mot � d�couvrir
						if(mot.length() < lesMotsSensiCasse.get(i).length()) {
							for(int j = 0; j < mot.length(); j++) {
								if(mot.charAt(j) == lesMotsSensiCasse.get(i).charAt(j)) {
									cpt ++;
								}
							}
						}

						//Si le mot est plus grand que le mot � d�couvrir
						else {
							for(int j = 0; j > lesMotsSensiCasse.get(i).length(); j++) {
								if(mot.charAt(j) == lesMotsSensiCasse.get(i).charAt(j)) {
									cpt ++;
								}
							}
						}

						//Si les premi�res lettres sont les m�mes
						if(cpt == mot.length()) {

							//On "crypte" le mot
							String word = mot;

							for(int z = 0; z < lesMotsSensiCasse.get(i).length() - mot.length(); z++) {
								word += caractereOccul;
							}

							lesMotsEtudiant.set(i, word);
							//On r�initialise le compteur
							cpt = 0;
						}
					}
				}
			}
		}

		//Si c'est la premi�re fois que l'�tudiant propose un mot, le timer se d�clenche
		if(timerEstDeclenche == false) {
			gestionTimer();
			timerEstDeclenche = true;
		}

		//On r�initialise le TextField et la transcription
		motPropose.setText("");
		transcription.setText("");

		//On met � jour la transcription gr�ce � la liste des mots de l'�tudiant
		for(String word : lesMotsEtudiant) {	
			if(regexPoint(word)){
				transcription.setText(transcription.getText() + word);
			}
			else {
				transcription.setText(transcription.getText() + " " + word);
			}
		}

		//On regarde si l'�tudiant a termin� l'exercice
		if(estTermine()) {
			timeFinish();
		}
	}

	//M�thode qui regarde si l'�tudiant a fini l'exercice
	public boolean estTermine() {

		if(Math.round((nbMotsDecouverts / nbMotsTotal) * 100) == 100){
			return true;
		} else {
			return false;
		}

	}

	//M�thode qui va load le temps �coul� pour le mode �valuation
	public void timeFinish() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/TempsEcoule.fxml"));
		//On bloque sur cette fen�tre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(new Scene(root,  400, 400));
		stage.show();
	}


	//M�thode permettant de cr�er un timer pour que l'�tudiant voit le temps qui d�file en mode Evaluation
	public void gestionTimer() {
		timer = new Timeline();
		timer.setCycleCount(Timeline.INDEFINITE);

		if(evaluation == true) {
			timer.getKeyFrames().add(
					new KeyFrame(Duration.seconds(1),
							new EventHandler<ActionEvent>() {
						// KeyFrame event handler
						@Override    
						public void handle(ActionEvent arg0) {
							sec--;
							if (sec < 0) {
								min--;
								sec=59;
							}
							// update timerLabel
							time.setText(min +":"+ sec +"s");

							//S'il ne reste plus de temps, on load la fenetre d'enregistrement
							if (sec <= 0 && min<=0) {
								timer.stop();
								try {
									loadEnregistrement();
								} catch (IOException e) {
									e.printStackTrace();
								}
								return;
							}

						}
					}));
			timer.playFromStart();
		}

		if(entrainement == true) {
			timer.getKeyFrames().add(
					new KeyFrame(Duration.seconds(1),
							new EventHandler<ActionEvent>() {
						// KeyFrame event handler
						@Override    
						public void handle(ActionEvent arg0) {
							sec++;
							if (sec > 59) {
								min++;
								sec = 00;
							}

							// update timerLabel
							time.setText(min +":"+ sec +"s");

						}
					}));
			timer.playFromStart();
		}
	}

	//M�thode qui survient lorsque le timer est �coul� en mode Evaluation
	public void loadEnregistrement() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/TempsEcoule.fxml"));
		Stage stage = new Stage();
		//On bloque sur cette fen�tre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		//On bloque le resize
		stage.setResizable(false);
		stage.setScene(new Scene(root, 500, 300));
		stage.show();
	}

}
