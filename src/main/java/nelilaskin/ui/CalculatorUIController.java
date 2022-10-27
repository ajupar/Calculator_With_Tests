package nelilaskin.ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nelilaskin.calcModules.DivisionCalc;
import nelilaskin.calcModules.ProductCalc;
import nelilaskin.calcModules.SqrtCalc;
import nelilaskin.calcModules.SquareCalc;
import nelilaskin.calcModules.SubtractCalc;
import nelilaskin.calcModules.SumCalc;
import nelilaskin.calcModules.calcEnums;

/**
 * Käyttöliittymänäkymän kontrolleriluokka
 *
 */
public class CalculatorUIController {

	private Stage stage;
	private MasterController masterController; // yläkontrolleri

	private SumCalc sumCalc = new SumCalc();
	private SubtractCalc subtractCalc = new SubtractCalc();
	private ProductCalc productCalc = new ProductCalc();
	private DivisionCalc divisionCalc = new DivisionCalc();

	private SquareCalc squareCalc = new SquareCalc();
	private SqrtCalc sqrtCalc = new SqrtCalc();

	Object[] argumentArray = new Object[3];

	StringBuilder currentNumber = new StringBuilder();

	SimpleStringProperty formulaProperty = new SimpleStringProperty(); // UI:ssa näkyvä pyöristetty arvo
	SimpleStringProperty resultProperty = new SimpleStringProperty(); // UI:hin tulostuva pyöristetty arvo

	private double result; // todellinen pyöristämätön arvo

	// "Jatkolaskenta" 6+3=9, jonka jälkeen käyttäjä yrittää syöttää numeron -> 9.03
	// Mikäli boolean arvo on false, ei pysty syöttämään numeroita tai "." tai "+/-"
	// -arvoja.
	private boolean resultCleared = true;

	// Tyhjä konstruktori - käytetään testaamisessa.
	public CalculatorUIController() {

	}

	public CalculatorUIController(Stage stageIN, MasterController masterControllerIN) {
		this.stage = stageIN;
		this.masterController = masterControllerIN;
	}

	/**
	 * Käynnistetään kontrollerin elementtejä
	 */
	@FXML
	private void initialize() {
		resultLabel.textProperty().bind(resultProperty);
		formulaLabel.textProperty().bind(formulaProperty);

		topRowButton1.setText(squareCalc.getSymbol());
		topRowButton2.setText(sqrtCalc.getSymbol());

		VBox.setVgrow(allButtonsVBox, Priority.ALWAYS);

	}

	/**
	 * Tyhjennetään nykyinen lukuarvo
	 */
	private void clearCurrentNumber() {
		currentNumber.delete(0, currentNumber.length());
	}

	/**
	 * Muunnetaan nykyinen lukuarvo double-muotoon
	 * 
	 * @param argumentArrayIndex
	 */
	private void parseCurrentNumber(int argumentArrayIndex) {

//		if ((Double.parseDouble(currentNumber.toString()) % 1) == 0) {
//			System.out.println("(debug) Number is an integer number.");
//		} else {
//			System.out.println("(debug) Number is a double number.");
//		}	

		if (argumentArrayIndex == 0) {

			argumentArray[argumentArrayIndex] = Double.parseDouble(currentNumber.toString());

		} else if (argumentArrayIndex == 2) {

			argumentArray[argumentArrayIndex] = Double.parseDouble(currentNumber.toString());

		} else {

			System.out.println("parseCurrentNumber invalid index number");
		}
	}

	/**
	 * Päivitetään rakentumassa oleva laskukaava
	 */
	private void updateFormulaProperty() {

		StringBuilder formulaBuilder = new StringBuilder();

		if (argumentArray[0] != null) {

			// System.out.println("(debug) argumentArray[0]: " + argumentArray[0]);

			formulaBuilder.append(integerDoubleCheck(argumentArray[0], false));

		} else {
			formulaBuilder.append(currentNumber.toString());
		}

		if (argumentArray[1] != null) {
			formulaBuilder.append(" " + argumentArray[1] + " ");
		}

		if (argumentArray[2] != null) {

			// System.out.println("(debug) argumentArray[2]: " + argumentArray[2]);

			formulaBuilder.append(integerDoubleCheck(argumentArray[2], false) + " = ");

		} else if (argumentArray[0] != null && argumentArray[1] != null) {
			formulaBuilder.append(currentNumber.toString());
		}

		formulaProperty.setValue(formulaBuilder.toString());
	}

	/**
	 * Tutkitaan onko luku kokonaisluku. (Mikäli kokonaisluku, niin ei lisätä
	 * pilkkua+nollaa luvun perään.)
	 * 
	 * @param number
	 * @return String
	 */
	public String integerDoubleCheck(Object number, boolean numberIsResult) {

		// System.out.println("integerDoubleCheck number.toString: " +
		// number.toString());

		Double doubleNumber = Double.parseDouble(number.toString());

		Double doubleNumberRound = (double) Math.round(doubleNumber);
		
//		System.out.println("(debug) doubleNumberRound value: " + doubleNumberRound);
//		System.out.println("(debug) integerDoubleCheck elseif booleans: " + numberIsResult + " " + (doubleNumber % 1 != 0) + " "
//				+ (Math.abs(doubleNumber - doubleNumberRound) < 0.00001));

		// System.out.println("doubleNumberRound.toString: " +
		// doubleNumberRound.toString());

		// Tarkastetaanko onko kokonaisluku
		if (doubleNumber % 1 == 0) { // Integer arvo
			System.out.println("(debug) Number is an integer.");

			return removePointZero(doubleNumber);

		}
		// Laskun tulos on double, mutta niin lähellä kokonaislukua,
		// että se pyöristetään kokonaisluvuksi
		else if (numberIsResult && doubleNumber % 1 != 0 && Math.abs(doubleNumber - doubleNumberRound) < 0.00001) {
			System.out.println("(debug) Number is a double -> integer.");

			return removePointZero(doubleNumberRound);

		} else { // Double arvo
			System.out.println("(debug) Number is a double.");
			return cleanDecimals(doubleNumber);
		}
	}

	/**
	 * Poistetaan Double (esim. 8.0 tai 6.0 "kokonaisluku") tyyppisestä muuttajasta
	 * pilkku ja sen jälkeiset luvut (= nolla).
	 * 
	 * @param number
	 * @return String, jossa ei ole pilkkuaJAnollaa
	 */
	public String removePointZero(Double number) {

		String numString = String.valueOf(number);

		if (numString.contains("E")) {

			StringBuilder cleanerBuilder = new StringBuilder();

			String beforeDotString = numString.substring(0, numString.indexOf("."));
			String pastDotString = numString.substring(numString.indexOf("."), numString.length());

			String decimalsBeforeE = pastDotString.substring(0, pastDotString.indexOf("E"));
			String eString = numString.substring(numString.indexOf("E"), numString.length());

			if (decimalsBeforeE.length() > 5) {
				decimalsBeforeE = decimalsBeforeE.substring(0, 6);
			}

			cleanerBuilder.append(beforeDotString);
			cleanerBuilder.append(decimalsBeforeE);
			cleanerBuilder.append(eString);

			numString = cleanerBuilder.toString();

			return numString;

		} else {

			return String.valueOf(number).substring(0, String.valueOf(number).indexOf("."));

		}
	}

	/**
	 * Siivotaan tuloksena olevan lukuarvon desimaaleja niin, että sallitaan viisi
	 * desimaalinumeroa
	 * 
	 * @param number
	 * @return
	 */
	public String cleanDecimals(Double number) {

		StringBuilder cleanerBuilder = new StringBuilder();

		String numString = String.valueOf(number);

		String beforeDotString = numString.substring(0, numString.indexOf("."));
		String pastDotString = numString.substring(numString.indexOf(".") + 1, numString.length());

		if (pastDotString.contains("E")) {

			System.out.println("(debug) beforeDotString: " + beforeDotString + " pastDotString: " + pastDotString);

			String decimalsBeforeE = pastDotString.substring(0, pastDotString.indexOf("E"));
			String eString = pastDotString.substring(pastDotString.indexOf("E"), pastDotString.length());

			if (decimalsBeforeE.length() > 5) {
				decimalsBeforeE = decimalsBeforeE.substring(0, 5);
			}

			cleanerBuilder.append(beforeDotString);
			cleanerBuilder.append(".");
			cleanerBuilder.append(decimalsBeforeE);
			cleanerBuilder.append(eString);

			numString = cleanerBuilder.toString();

			return numString;

			// ei sisällä E-merkintää eli tavallinen luku
		} else {

			try {
				return numString.substring(0, numString.indexOf(".") + 6);
			} catch (Exception e) {
				return numString;
			}

		}

	}

	/**
	 * Päivitetään laskun tulos
	 * 
	 * @param result
	 */
	private void updateResultProperty(double result) {

		String resultString = Double.toString(result);

		// resultProperty.setValue(resultString);

		resultProperty.setValue(integerDoubleCheck(resultString, true));

		System.out
				.println("(debug) Result accurate value: " + result + ", ResultProperty: " + resultProperty.getValue());

	}

	/**
	 * Tyhjennetään laskun lopputulos.
	 */
	private void clearResultProperty() {

		resultProperty.setValue("");
	}

	// FXML-ELEMENTIT
	@FXML
	private Menu menuMenu;

	@FXML
	private MenuItem menuQuit;

	@FXML
	private HBox allButtonsHBox;

	@FXML
	private VBox allButtonsVBox;

	@FXML
	private HBox buttonRow1HBox;

	@FXML
	private Button topRowButton1;

	@FXML
	private Button topRowButton2;

	@FXML
	private Button clearButton;

	@FXML
	private Button divideButton;

	@FXML
	private HBox buttonRow2HBox;

	@FXML
	private Button number7Button;

	@FXML
	private Button number8Button;

	@FXML
	private Button number9Button;

	@FXML
	private Button productButton;

	@FXML
	private HBox buttonRow3HBox;

	@FXML
	private Button number4Button;

	@FXML
	private Button number5Button;

	@FXML
	private Button number6Button;

	@FXML
	private Button minusButton;

	@FXML
	private HBox buttonRow4HBox;

	@FXML
	private Button number1Button;

	@FXML
	private Button number2Button;

	@FXML
	private Button number3Button;

	@FXML
	private Button plusButton;

	@FXML
	private HBox buttonRow5HBox;

	@FXML
	private Button plusMinusButton;

	@FXML
	private Button number0Button;

	@FXML
	private Button decimalButton;

	@FXML
	private Button resultButton;

	@FXML
	private VBox resultVBox;

	@FXML
	private Label formulaLabel;

	@FXML
	private Label resultLabel;

	// FXML-TAPAHTUMAMETODIT

	/**
	 * Ylävalikon tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void menuQuitOnAction(ActionEvent event) {
		System.out.println("(debug) menu quit");

		Platform.exit();
	}

	/**
	 * C-nappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void clearButtonOnAction(ActionEvent event) {

		clearCurrentNumber();

		argumentArray = new Object[3];

		updateFormulaProperty();

		clearResultProperty();

		resultCleared = true;
	}

	/**
	 * Desimaalinappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void decimalButtonOnAction(ActionEvent event) {

		if (resultCleared && currentNumber.indexOf(".") == -1) {
			currentNumber.append(".");
		}
	}

	/**
	 * Jakolaskunappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void divideButtonOnAction(ActionEvent event) {

		clearResultProperty(); // Tyhjennys, tarve kun edellisen laskun tulos tallennetaan seuraavan laskun
								// parametriksi
		resultCleared = true;

		if (argumentArray[0] == null) {

			parseCurrentNumber(0);

			System.out.println(
					"(debug) divideButton argumentArray[0] set to " + Double.parseDouble(currentNumber.toString()));

			clearCurrentNumber();

			argumentArray[1] = calcEnums.DIVIDE;

			updateFormulaProperty();
		}
	}

	/**
	 * Miinusnappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void minusButtonOnAction(ActionEvent event) {

		clearResultProperty(); // Tyhjennys, tarve kun edellisen laskun tulos tallennetaan seuraavan laskun
								// parametriksi
		resultCleared = true;

		if (argumentArray[0] == null) {

			parseCurrentNumber(0);

			System.out.println(
					"(debug) minusButton argumentArray[0] set to " + Double.parseDouble(currentNumber.toString()));

			clearCurrentNumber();

			argumentArray[1] = calcEnums.MINUS;

			updateFormulaProperty();
		}
	}

	/**
	 * Yhteenlaskunappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void plusButtonOnAction(ActionEvent event) {

		clearResultProperty(); // Tyhjennys, tarve kun edellisen laskun tulos tallennetaan seuraavan laskun
								// parametriksi
		resultCleared = true;

		if (argumentArray[0] == null) {

			parseCurrentNumber(0);

			System.out.println(
					"(debug) plusButton argumentArray[0] set to " + Double.parseDouble(currentNumber.toString()));

			clearCurrentNumber();

			argumentArray[1] = calcEnums.PLUS;

			updateFormulaProperty();
		}
	}

	/**
	 * Kertolaskunappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void productButtonOnAction(ActionEvent event) {

		clearResultProperty(); // Tyhjennys, tarve kun edellisen laskun tulos tallennetaan seuraavan laskun
								// parametriksi
		resultCleared = true;

		if (argumentArray[0] == null) {

			parseCurrentNumber(0);

			System.out.println(
					"(debug) productButton argumentArray[0] set to " + Double.parseDouble(currentNumber.toString()));

			clearCurrentNumber();

			argumentArray[1] = calcEnums.PRODUCT;

			updateFormulaProperty();
		}
	}

	/**
	 * Etumerkkinappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void plusMinusButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			// Poistetaan miinus merkki mikäli sellainen on jo muuttujassa
			if (currentNumber.charAt(0) == '-') {
				currentNumber.deleteCharAt(0); // Ensimmäisen merkin poisto
			} else {
				currentNumber.insert(0, '-'); // Lisätään miinus merkki
			}

			updateFormulaProperty();
		}
	}

	/**
	 * Lopputulos-nappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void resultButtonOnAction(ActionEvent event) {

		if (argumentArray[0] != null && argumentArray[1] != null) {

			parseCurrentNumber(2);

// 			double result = -9999999.0; // Alkuarvo tulokselle

			if (argumentArray[1] == calcEnums.PLUS) {
				result = sumCalc.calc(argumentArray);
				computeResult();
			}

			if (argumentArray[1] == calcEnums.MINUS) {
				result = subtractCalc.calc(argumentArray);
				computeResult();
			}

			if (argumentArray[1] == calcEnums.PRODUCT) {
				result = productCalc.calc(argumentArray);
				computeResult();
			}

			if (argumentArray[1] == calcEnums.DIVIDE) {
				try {
					result = divisionCalc.calc(argumentArray);
					computeResult();
				} catch (Exception e) {
					System.out.println(e.getMessage());

					resultProperty.setValue("DIV BY ZERO");

					clearCurrentNumber();
					argumentArray = new Object[3];

				}
			}

		}
	}

	private void computeResult() {

		updateResultProperty(result);
		updateFormulaProperty();

		clearCurrentNumber();
		argumentArray = new Object[3];

		// Tallennetaan laskun tulos uuden laskun ensimmäiseksi muuttujaksi
		currentNumber.append(result);

		resultCleared = false;

	}

	/**
	 * 0-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number0ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(0);
			updateFormulaProperty();
		}
	}

	/**
	 * 1-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number1ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(1);
			updateFormulaProperty();
		}
	}

	/**
	 * 2-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number2ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(2);
			updateFormulaProperty();
		}
	}

	/**
	 * 3-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number3ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(3);
			updateFormulaProperty();
		}
	}

	/**
	 * 4-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number4ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(4);
			updateFormulaProperty();
		}
	}

	/**
	 * 5-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number5ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(5);
			updateFormulaProperty();
		}
	}

	/**
	 * 6-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number6ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(6);
			updateFormulaProperty();
		}
	}

	/**
	 * 7-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number7ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(7);
			updateFormulaProperty();
		}
	}

	/**
	 * 8-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number8ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(8);
			updateFormulaProperty();
		}
	}

	/**
	 * 9-numeronappulan tapahtumametodi
	 * 
	 * @param event
	 */
	@FXML
	void number9ButtonOnAction(ActionEvent event) {

		if (resultCleared) {
			currentNumber.append(9);
			updateFormulaProperty();
		}
	}

	/**
	 * Toinen potenssi
	 * 
	 * @param event
	 */
	@FXML
	void topRowButton1OnAction(ActionEvent event) {

		if (argumentArray[0] == null && argumentArray[1] == null && argumentArray[2] == null) {

			StringBuilder formulaBuilder = new StringBuilder();

			argumentArray[0] = Double.parseDouble(currentNumber.toString());

			formulaBuilder.append(integerDoubleCheck((Object) Double.parseDouble(currentNumber.toString()), true));
			formulaBuilder.append(squareCalc.getSymbol().charAt(1));
			formulaBuilder.append(" =");

			formulaProperty.setValue(formulaBuilder.toString());

			try {
				result = squareCalc.calc(argumentArray);

				currentNumber.delete(0, currentNumber.length());
				// currentNumber.append(removePointZero(result));
				currentNumber.append(result);

				argumentArray = new Object[3];

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			updateResultProperty(result);

			resultCleared = false;

		}

	}

	/**
	 * Neliöjuuri
	 * 
	 * @param event
	 */
	@FXML
	void topRowButton2OnAction(ActionEvent event) {

		if (argumentArray[0] == null && argumentArray[1] == null && argumentArray[2] == null) {

			StringBuilder formulaBuilder = new StringBuilder();

			argumentArray[0] = Double.parseDouble(currentNumber.toString());

			formulaBuilder.append(sqrtCalc.getSymbol());

			formulaBuilder.append(integerDoubleCheck((Object) Double.parseDouble(currentNumber.toString()), true));

//			formulaBuilder.append(removePointZero(Double.parseDouble(currentNumber.toString())));
			formulaBuilder.append(" =");

			formulaProperty.setValue(formulaBuilder.toString());

			try {
				result = sqrtCalc.calc(argumentArray);

				currentNumber.delete(0, currentNumber.length());
				// currentNumber.append(removePointZero(result));
				currentNumber.append(result);

				argumentArray = new Object[3];

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			updateResultProperty(result);

			resultCleared = false;

		}

	}
}
