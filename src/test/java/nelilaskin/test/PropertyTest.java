package nelilaskin.test;

import nelilaskin.calcModules.DivisionCalc;
import nelilaskin.calcModules.ProductCalc;
import nelilaskin.calcModules.SqrtCalc;
import nelilaskin.calcModules.SquareCalc;
import nelilaskin.calcModules.SubtractCalc;
import nelilaskin.calcModules.SumCalc;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

/**
 * Ominaisuustestit
 * https://sttp.site/chapters/testing-techniques/property-based-testing.html
 */
public class PropertyTest {

	// ENSIMMÄINEN testiosuus, testataan rajoittamalla lukujoukolla.

	@Property
	boolean testSum1(@ForAll double a, @ForAll double b) {

		SumCalc sumCalc = new SumCalc();
		Object[] argumentArray = { a, "+", b };

		return sumCalc.calc(argumentArray) == a + b;
	}

	@Property
	boolean testSubtract1(@ForAll double a, @ForAll double b) {

		SubtractCalc subtractCalc = new SubtractCalc();
		Object[] argumentArray = { a, "-", b };

		return subtractCalc.calc(argumentArray) == a - b;
	}

	@Property
	boolean testProduct1(@ForAll double a, @ForAll double b) {

		ProductCalc productCalc = new ProductCalc();
		Object[] argumentArray = { a, "*", b };

		return productCalc.calc(argumentArray) == a * b;
	}

	// Muodostetaan mielivaltaisia lukuja, filteröidään nollat pois.
	// (Nolla ei voi olla jakolaskussa jakanana.)
	@Provide
	Arbitrary<Double> valueNotZero() {
		return Arbitraries.doubles().filter(value -> value != 0.0);
	}

	@Property
	boolean testDivision1(@ForAll double a, @ForAll("valueNotZero") double b) throws Exception {

		DivisionCalc divisionCalc = new DivisionCalc();
		Object[] argumentArray = { a, "/", b };

		return divisionCalc.calc(argumentArray) == a / b;
	}

	@Provide
	Arbitrary<Double> nonNegativeDoubles() {
		return Arbitraries.doubles().filter(value -> value >= 0.0);
	}

	@Property
	boolean testSqrt1(@ForAll("values0to1000000") double a) throws Exception {

		SqrtCalc sqrtCalc = new SqrtCalc();
		Object[] argumentArray = { a, null, null };

		return Math.abs(sqrtCalc.calc(argumentArray) - Math.sqrt(a)) < 0.00001;
	}

	@Provide
	Arbitrary<Double> values0to1000000() {
		return Arbitraries.doubles()
				.greaterOrEqual(0.0)
				.lessOrEqual(1000000.0);
	}

	@Property
	boolean testSquare1(@ForAll("values0to1000000") double a) throws Exception {

		SquareCalc squareCalc = new SquareCalc();
		Object[] argumentArray = { a, null, null };

		return Math.abs(squareCalc.calc(argumentArray) - Math.pow(a, 2.0)) < 0.00001;

	}
	
	@Provide
	Arbitrary<Double> values2to20() {
		return Arbitraries.doubles()
				.greaterOrEqual(2.0)
				.lessOrEqual(20.0);
	}
	
	/**
	 * Tarkistetaan, toimivatko toinen potenssi ja neliöjuuri toistensa
	 * vastakkaisoperaatioina kolmannen toiston syvyyteen asti
	 * lukualueella 2-20. Testataan tulosten yhtäläisyyttä kolmen
	 * desimaalin tarkkuuteen asti, koska suurilla luvuilla laskettaessa
	 * menetetään tarkkuutta joka tapauksessa.
	 * 
	 * @param a
	 * @return
	 * @throws Exception
	 */
	@Property
	boolean testSquareAndSqrt(@ForAll("values2to20") double a) throws Exception {

		SquareCalc squareCalc = new SquareCalc();
		SqrtCalc sqrtCalc = new SqrtCalc();
		
		Object[] argumentArray = { a, null, null };
		
		// ensimmäinen potenssiin korotus
		// tallennetaan apumuuttujaan myöhempää tarkistusta varten
		double apuri1 = squareCalc.calc(argumentArray);
		a = squareCalc.calc(argumentArray);
		argumentArray[0] = a;
		
		// toinen potenssiin korotus
		a = squareCalc.calc(argumentArray);
		argumentArray[0] = a;
		
		// kolmas potenssiin korotus
		a = squareCalc.calc(argumentArray);
		argumentArray[0] = a;
		
		// eka neliöjuuri
		a = sqrtCalc.calc(argumentArray);
		argumentArray[0] = a;
		
		// toinen neliöjuuri
		a = sqrtCalc.calc(argumentArray);
		argumentArray[0] = a;
		
		return Math.abs(a - apuri1) < 0.001;

	} 
	

	// TOINEN testiosuus, testataan rajatulla lukujoukolla.

	// Muodostetaan mielivaltaisia lukuja välille 0...20000,
	// jotka ovat jaollisia 3:lla ja 5:llä.
	@Provide
	Arbitrary<Double> values0to20000divisibleBy3and5() {
		return Arbitraries.doubles().greaterThan(0.0).lessThan(20000.0).filter(value -> value % 3.0 == 0.0)
				.filter(value -> value % 5.0 == 0.0);
	}

	@Property
	boolean testSum2(@ForAll("values0to20000divisibleBy3and5") double a,
			@ForAll("values0to20000divisibleBy3and5") double b) {

		SumCalc sumCalc = new SumCalc();
		Object[] argumentArray = { a, "+", b };

		return sumCalc.calc(argumentArray) == a + b;
	}

	@Property
	boolean testSubtract2(@ForAll("values0to20000divisibleBy3and5") double a,
			@ForAll("values0to20000divisibleBy3and5") double b) {

		SubtractCalc subtractCalc = new SubtractCalc();
		Object[] argumentArray = { a, "-", b };

		return subtractCalc.calc(argumentArray) == a - b;
	}

	@Property
	boolean testProduct2(@ForAll("values0to20000divisibleBy3and5") double a,
			@ForAll("values0to20000divisibleBy3and5") double b) {

		ProductCalc productCalc = new ProductCalc();
		Object[] argumentArray = { a, "*", b };

		return productCalc.calc(argumentArray) == a * b;
	}

	@Property
	boolean testDivision2(@ForAll("values0to20000divisibleBy3and5") double a, @ForAll("valueNotZero") double b)
			throws Exception {

		DivisionCalc divisionCalc = new DivisionCalc();
		Object[] argumentArray = { a, "/", b };

		return divisionCalc.calc(argumentArray) == a / b;
	}

	@Property
	boolean testSqrt2(@ForAll("values0to20000divisibleBy3and5") double a) throws Exception {

		SqrtCalc sqrtCalc = new SqrtCalc();
		Object[] argumentArray = { a, null, null };

		return Math.abs(sqrtCalc.calc(argumentArray) - Math.sqrt(a)) < 0.00001;

	}

	@Property
	boolean testSquare2(@ForAll("values0to20000divisibleBy3and5") double a) throws Exception {

		SquareCalc squareCalc = new SquareCalc();
		Object[] argumentArray = { a, null, null };

		return Math.abs(squareCalc.calc(argumentArray) - Math.pow(a, 2.0)) < 0.00001;

	}

//    @Property
//    boolean testAddArray(@ForAll int[] input) {
//        return TestableRoutines.arrayAdd(input) == IntStream.of(input).sum();
//    }
//
//    @Property
//    boolean testAddArrayWithNulls(@WithNull @ForAll Integer[] input) {
//        if (input == null) return TestableRoutines.arrayAdd2(input) == 0;
//
//        return TestableRoutines.arrayAdd2(input) == Arrays.asList(input).stream().mapToInt(i -> i == null ? 0 : i).sum();
//    }
//
//    @Property
//    boolean testAdd(@ForAll int a, @ForAll int b) {
//        return TestableRoutines.add(a, b) == a + b;
//    }
//
//    @Property
//    boolean testPosAdd(@ForAll @Positive int a, @ForAll @Positive int b) {
//        return TestableRoutines.posAdd(a, b) == a + b;
//    }

}
