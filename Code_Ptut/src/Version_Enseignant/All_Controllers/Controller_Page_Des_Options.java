package Version_Enseignant.All_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Page_Des_Options implements Initializable {

	@FXML
	private RadioButton radioButtonEntrainement;
	@FXML
	private RadioButton radioButtonEvaluation;
	@FXML
	private RadioButton radioButton2Lettres;
	@FXML
	private RadioButton radioButton3Lettres;
	@FXML
	private TextField CaraOccul;
	@FXML
	private TextField nbMinute;
	@FXML
	private CheckBox checkBoxMotIncomplet;
	@FXML
	private CheckBox checkBoxSolution;
	@FXML
	private CheckBox checkBoxMotsDecouverts;
	@FXML
	private CheckBox sensibiliteCasse;
	@FXML
	private Button enregistrer;

	// Variables qui contiennent les informations sur l'exercice
	public static String caraOccul;
	public static String nbMin;
	public static boolean sensiCasse;
	public static boolean entrainement;
	public static boolean evaluation;
	public static boolean solution;
	public static boolean motDecouverts;
	public static boolean motIncomplet;
	public static boolean lettres_2;
	public static boolean lettres_3;

	// M�thode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//Pour la fonction ouvrir
		//On met le caract�re d'occultation 
		if(caraOccul != null) {
			CaraOccul.setText(caraOccul);
		}

		//On met la sensibilit� � la casse si celle-ci est activ�e
		if(sensiCasse == true) {
			sensibiliteCasse.setSelected(true);
		}

		//On met le bon mode
		//Entrainement
		if(entrainement == true) {
			radioButtonEntrainement.setSelected(true);

			//On met disable ce qui concerne le mode Evaluation
			nbMinute.setDisable(true);

			//Si l'affichage de la solution est autoris�
			if(solution == true) {
				checkBoxSolution.setSelected(true);
			}

			//Si l'affichage du nombre de mots d�couverts en temps r�el est autoris�
			if(motDecouverts == true) {
				checkBoxMotsDecouverts.setSelected(true);
			}

			//Si l'option mot incomplet est autoris�
			if(motIncomplet ==  true) {
				checkBoxMotIncomplet.setSelected(true);

				//Si c'est pour deux lettres
				if(lettres_2 == true) {
					radioButton2Lettres.setSelected(true);
				}

				//Si c'est pour trois lettres
				if(lettres_3 == true) {
					radioButton3Lettres.setSelected(true);
				}

			}
		}

		//Evaluation
		if(evaluation == true) {
			radioButtonEvaluation.setSelected(true);

			//On met disable ce qui concerne le mode Entrainement
			checkBoxMotsDecouverts.setDisable(true);
			checkBoxMotIncomplet.setDisable(true);
			checkBoxSolution.setDisable(true);
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);
			nbMinute.setText(nbMin);
		}

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
	public void ouvrir(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Ouvrez votre exercice");
		fileChooser.showOpenDialog(null);
		// TODO Chargez l'exercice dans la page
	}

	// Bouton Pr�f�rences qui emm�ne sur la page des param�tres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	// Bouton qui fait retourner l'enseignant � la page d'apercu (bouton retour)
	@FXML
	public void pageApercu(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageApercu.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	@FXML
	public void pageEnregistrementFinal(ActionEvent event) throws IOException {
		// Quand on passe � la page suivante, on m�morise les informations des options
		caraOccul = CaraOccul.getText();
		nbMin = nbMinute.getText();

		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/EnregistrementFinal.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	// Bouton DarkMode qui met en darkMode l'application
	@FXML
	public void darkMode() {
		// TODO faire le DarkMode
	}

	// Gestion de si je s�lectionne un mode, l'autre se d�coche
	@FXML
	public void selectionModeEvaluation(ActionEvent event) {
		// On fait appara�tre ce qui concerne le mode Evaluation
		nbMinute.setDisable(false);
		evaluation = true;

		// On enl�ve les s�lections du mode entrainement et on passe les variables � false
		checkBoxMotIncomplet.setSelected(false);
		motIncomplet = false;
		checkBoxSolution.setSelected(false);
		solution = false;
		checkBoxMotsDecouverts.setSelected(false);
		motDecouverts = false;
		radioButton2Lettres.setSelected(false);
		lettres_2 = false;
		radioButton3Lettres.setSelected(false);
		lettres_3 = false;

		//On met disable ce qui concerne le mode Entrainement
		checkBoxMotsDecouverts.setDisable(true);
		checkBoxMotIncomplet.setDisable(true);
		checkBoxSolution.setDisable(true);
		radioButton2Lettres.setDisable(true);
		radioButton3Lettres.setDisable(true);

		// On regarde si l'autre bouton est s�lectionn�, si c'est le cas on le
		// d�selectionne
		if (radioButtonEntrainement.isSelected()) {
			radioButtonEntrainement.setSelected(false);
			entrainement = false;
		}

		// Dans le cas d'une d�selection du bouton, on retire ce qui concerne le mode
		// Evaluation
		if (!radioButtonEvaluation.isSelected()) {
			evaluation = false;

			//on vide le textField
			nbMinute.setText(null);
		}

	}

	@FXML
	public void selectionModeEntrainement(ActionEvent event) {
		// On fait appara�tre ce qui concerne le mode Entrainement
		//On met disable ce qui concerne le mode Entrainement
		checkBoxMotsDecouverts.setDisable(false);
		checkBoxMotIncomplet.setDisable(false);
		checkBoxSolution.setDisable(false);
		radioButton2Lettres.setDisable(false);
		radioButton3Lettres.setDisable(false);

		// On cache ce qui concerne le mode Evaluation
		nbMinute.setDisable(true);
		entrainement = true;

		// On r�initialise le nombre de minutes
		nbMinute.setText(null);

		// On regarde si l'autre bouton est s�lectionn�, si c'est le cas on le
		// d�selectionne
		if (radioButtonEvaluation.isSelected()) {
			radioButtonEvaluation.setSelected(false);
			evaluation = false;
		}

		// Dans le cas d'une d�selection du bouton, on retire ce qui concerne le mode
		// Entrainement
		if (!radioButtonEntrainement.isSelected()) {
			
			checkBoxMotsDecouverts.setDisable(true);
			checkBoxMotIncomplet.setDisable(true);
			checkBoxSolution.setDisable(true);
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);

			// On enl�ve les s�lections du mode entrainement et on passe les variables � false
			checkBoxMotIncomplet.setSelected(false);
			motIncomplet = false;
			checkBoxSolution.setSelected(false);
			solution = false;
			checkBoxMotsDecouverts.setSelected(false);
			motDecouverts = false;
			radioButton2Lettres.setSelected(false);
			lettres_2 = false;
			radioButton3Lettres.setSelected(false);
			lettres_3 = false;

			entrainement = false;
		}

	}

	// Gestion de si je s�lectionne une nombre de lettres minimum autoris�, l'autre
	// se d�coche
	@FXML
	public void selection2Lettres(ActionEvent event) {
		if (radioButton3Lettres.isSelected()) {
			radioButton3Lettres.setSelected(false);

			lettres_2 = true;
			lettres_3 = false;
		} else {
			lettres_2 = true;
		}
	}

	@FXML
	public void selection3Lettres(ActionEvent event) {
		if (radioButton2Lettres.isSelected()) {
			radioButton2Lettres.setSelected(false);

			lettres_3 = true;
			lettres_2 = false;
		} 
		else {
			lettres_3 = true;
		}
	}

	// M�thode qui restreint � un caract�re, la saisie du caract�re d'occultation
	@FXML
	public void RestrictionOne(KeyEvent event) {
		if (CaraOccul.getText().length() > 1) {
			CaraOccul.deletePreviousChar();
		}
	}

	// M�thode qui restreint � la saisie de chiffres uniquement pour la saisie du
	// temps
	@FXML
	public void RestrictionChiffre(KeyEvent event) {
		if (nbMinute.getText().length() > 0) {
			if (!nbMinute.getText().matches("[0-9]*")) {
				nbMinute.deletePreviousChar();
			}
		}
	}

	// M�thode qui enl�ve ou fait appara�tre le choix du nombre de lettre si mot
	// incomplet est coch�
	@FXML
	public void motIncomplet(ActionEvent event) {

		// Si on coche le radioButton
		if (checkBoxMotIncomplet.isSelected()) {

			radioButton2Lettres.setDisable(false);
			radioButton3Lettres.setDisable(false);

			// on passe � true
			motIncomplet = true;
		}
		// Si on le d�coche
		if (!checkBoxMotIncomplet.isSelected()) {
			// on le cache
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);
			// on les d�selectionne et on repasse les variables � false
			radioButton2Lettres.setSelected(false);
			radioButton3Lettres.setSelected(false);

			lettres_2 = false;
			lettres_3 = false;

			// on passe � false
			motIncomplet = false;
		}

	}

	// M�thode qui passe � true ou false la variable sensiCasse
	@FXML
	public void sensiCasse(ActionEvent event) {
		// Si la case est coch�e
		if (sensibiliteCasse.isSelected()) {
			sensiCasse = true;
		}
		// Dans le cas contraire
		else {
			sensiCasse = false;
		}
	}

	// M�thode qui passe � true ou false la variable solution
	@FXML
	public void affichageSolution(ActionEvent event) {
		// Si la case est coch�e
		if (checkBoxSolution.isSelected()) {
			solution = true;
		}
		// Dans le cas contraire
		else {
			solution = false;
		}
	}

	// M�thode qui passe � true ou false la variable motsD�couverts
	@FXML
	public void motDecouverts(ActionEvent event) {
		// Si la case est coch�e
		if (checkBoxMotsDecouverts.isSelected()) {
			motDecouverts = true;
		}
		// Dans le cas contraire
		else {
			motDecouverts = false;
		}
	}

}
