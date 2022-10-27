package nelilaskin.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * Ylätason kontrolleri, joka lataa käyttöliittymänäkymiä
 *
 */
public class MasterController {
	
	private CalculatorUI calculatorUI;
	private Stage calculatorStage;
	
	
	/**
	 * MainApp kutsuu alussa tätä metodia, kun ohjelma käynnistetään
	 * 
	 * @param stage
	 */
	public void loadCalculatorUI(Stage stage) {
		
	calculatorStage = stage;

	calculatorUI = new CalculatorUI(stage, this);
	
	Scene calculatorScene = new Scene(calculatorUI.getPa());

	calculatorStage.setScene(calculatorScene);

	calculatorStage.show();
	
	calculatorStage.setMinWidth(calculatorStage.getWidth());
	calculatorStage.setMinHeight(calculatorStage.getHeight());
	

	}

}

