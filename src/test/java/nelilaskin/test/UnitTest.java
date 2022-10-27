package nelilaskin.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import nelilaskin.calcModules.DivisionCalc;
import nelilaskin.calcModules.ProductCalc;
import nelilaskin.calcModules.SqrtCalc;
import nelilaskin.calcModules.SquareCalc;
import nelilaskin.calcModules.SubtractCalc;
import nelilaskin.calcModules.SumCalc;
import nelilaskin.ui.CalculatorUIController;

public class UnitTest {
	
	
	@BeforeAll
	public static void init() {
		
		
	}
	
	@AfterAll
	public static void done() {
		
	}
	
	/**
	 * Testataan yhteenlasku
	 */
	@Test
	void testSumCalcOnePlusThree() {
		SumCalc sumCalc = new SumCalc();
		
		Object[] argumentArray = {1.0, "+", 3.0};
		
		Assertions.assertEquals(4, sumCalc.calc(argumentArray));
		
	}
	
	/**
	 * Testataan vähennyslasku
	 */
	@Test
	void testSubtractCalcFiveMinusSix() {
		
		SubtractCalc subtractCalc = new SubtractCalc();
		
		Object[] argumentArray = {5.0, "-", 6.0};
		
		Assertions.assertEquals(-1, subtractCalc.calc(argumentArray));
		
	}
	
	/**
	 * Testataan kertolasku
	 */
	@Test
	void testProductCalcMinusEightTimesThirtyFive() {
		
		ProductCalc productCalc = new ProductCalc();
		
		Object[] argumentArray = {-8.0, "*", 35.0};
		
		Assertions.assertEquals(-280, productCalc.calc(argumentArray));
				
	}
	
	/**
	 * Testataan jakolasku
	 */
	@Test
	void testDivisionCalcTwentyFiveDividedByTwo() {
		
		DivisionCalc divisionCalc = new DivisionCalc();
		
		Object[] argumentArray = {25.0, "/", 2.0};
		
		try {
			Assertions.assertEquals(12.5, divisionCalc.calc(argumentArray));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Testataan toinen potenssi
	 */
	@Test
	void testSquareCalcSquareOfThirtyThree() {
		
		SquareCalc squareCalc = new SquareCalc();
		
		Object[] argumentArray = {33.0, null, null};
		
		try {
			Assertions.assertEquals(Math.pow(33.0, 2.0), squareCalc.calc(argumentArray));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Testataan neliöjuuri
	 */
	@Test
	void testSqrtCalcSqrtOfHundredAndTen() {
		
		SqrtCalc sqrtCalc = new SqrtCalc();
		
		Object[] argumentArray = {110.0, null, null};
		
		// tarkistetaan yhtäsuuruus viiden desimaalin tarkkuudella: assert kolmas parametri
		try {
			Assertions.assertEquals(Math.sqrt((double)argumentArray[0]), sqrtCalc.calc(argumentArray), 0.00001);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testataan metodin pilkun ja sen jälkeisten nollien poisto.
	 * Syötettävä luku on double (esim. 5.0 tai 25.00 eli kokonaisluku).
	 */
	@Test
	void testRemovePointZero20() {
				
		CalculatorUIController calcUIContoller = new CalculatorUIController();
		
		// tarkistetaan yhtäsuuruus viiden desimaalin tarkkuudella: assert kolmas parametri
		try {
			Assertions.assertEquals("20", calcUIContoller.removePointZero(20.000000000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Testataan metodin palautusarvoa
	 */
	@Test
	void testCleanDecimalsTest1() {
				
		CalculatorUIController calcUIContoller = new CalculatorUIController();
		
		// tarkistetaan yhtäsuuruus viiden desimaalin tarkkuudella: assert kolmas parametri
		try {
			Assertions.assertEquals("25.12345", calcUIContoller.cleanDecimals(25.1234567897777777));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testataan metodin palautusarvoa
	 */
	@Test
	void testCleanDecimalsTest2() {
				
		CalculatorUIController calcUIContoller = new CalculatorUIController();
		
		// tarkistetaan yhtäsuuruus viiden desimaalin tarkkuudella: assert kolmas parametri
		try {
			Assertions.assertEquals("2.51234E11", calcUIContoller.cleanDecimals(25.123456789E10));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Testataan luvun käsittelyä - palauttaa (double) kokonaisluvun ilman pistettä ja nollaa.
	 */
	@Test
	void testIntegerDoubleCheckInputInteger() {
				
		CalculatorUIController calcUIContoller = new CalculatorUIController();
		
		// tarkistetaan yhtäsuuruus viiden desimaalin tarkkuudella: assert kolmas parametri
		try {
			Assertions.assertEquals("2",calcUIContoller.integerDoubleCheck(2.000000,false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testataan luvun käsittelyä - ollaan lähellä kokonaislukua
	 * Palautetaan luku ilman pistettä ja desimaalija
	 */
	@Test
	void testIntegerDoubleCheckInputNearInteger() {
				
		CalculatorUIController calcUIContoller = new CalculatorUIController();
		
		// tarkistetaan yhtäsuuruus viiden desimaalin tarkkuudella: assert kolmas parametri
		try {
			Assertions.assertEquals("5",calcUIContoller.integerDoubleCheck(5.0000099,true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testataan luvun käsittelyä - desimaalien määrä 5
	 */
	@Test
	void testIntegerDoubleCheckInputDouble() {
				
		CalculatorUIController calcUIContoller = new CalculatorUIController();
		
		// tarkistetaan yhtäsuuruus viiden desimaalin tarkkuudella: assert kolmas parametri
		try {
			Assertions.assertEquals("7.00003",calcUIContoller.integerDoubleCheck(7.0000333333,false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testataan luvun käsittelyä - desimaalien määrä 5
	 */
	@Test
	void testIntegerDoubleCheckInputDoubleE() {
				
		CalculatorUIController calcUIContoller = new CalculatorUIController();
		
		// tarkistetaan yhtäsuuruus viiden desimaalin tarkkuudella: assert kolmas parametri
		try {
			Assertions.assertEquals("9.00003E10",calcUIContoller.integerDoubleCheck(9.0000333333E10,false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// vastaavat testit muille laskuoperaatioille -->
	
	// tehdään tähän omia yksikkötestejä, ks. mallikoodia alla
	// huom. oikeiden tagien käyttö
	
//    @BeforeAll
//    public static void init() {
//        System.out.println("Starting unit tests!");
//    }
//
//    @AfterAll
//    public static void done() {
//        System.out.println("Unit tests done!");
//    }
//
//    @Test
//    void testOnePlusTwoEqualsThree() {
//        Assertions.assertEquals(3, TestableRoutines.add(1, 2));
//    }
    
    
}
