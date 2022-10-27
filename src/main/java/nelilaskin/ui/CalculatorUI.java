package nelilaskin.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import nelilaskin.MainApp;

/**
 * Laskimen käyttöliittymää hallinnoiva luokka
 *
 */
public class CalculatorUI {

	private Stage stage;

	private Parent parent;

	public CalculatorUI(Stage stage, MasterController masterControllerIN) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nelilaskin/CalculatorUI.fxml"));

		System.out.println("debug (CalculatorUI.java): Ladataan CalculatorUI.fxml");

		fxmlLoader.setController(new CalculatorUIController(stage, masterControllerIN));

		stage.setTitle("Laskin");

		try {
			parent = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml));
		return fxmlLoader.load();
	}

	public Parent getPa() {
		return this.parent;
	}

}
