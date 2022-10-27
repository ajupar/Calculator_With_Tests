package nelilaskin.calcModules;

import java.util.List;

/**
 * Neliöjuuri
 *
 */
public class SqrtCalc implements Calc {

	@Override
	public List<Object> getArgumentList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(double data) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * Laskee neliöjuuren iteraatiolla
	 * 
	 * https://fi.wikipedia.org/wiki/Neli%C3%B6juuri#Miten_lasketaan_neli%C3%B6juuren_arvo?#Iteraatio
	 */
	@Override
	public double calc(Object[] argumentArray) throws Exception {
		// TODO Auto-generated method stub

		double num = (double) argumentArray[0];
		
		// System.out.println("Sqrt calc input number: " + num);
		
		if (num >= 0.0) {
			
			if (num == 1.0) {
				return 1.0;
			}
			
			if (num == 0.0) {
				return 0.0;
			}

			double aZero = num * 0.99;
			double c = num;
			
			// tehdään 20 iteraatiokierrosta, yritetään laskea hyvin tarkka arvo
			for (int i = 0; i < 20; i++) {
				c = (aZero + (num/aZero))/2;
				aZero = c;
			}
			
			return c;

		} else {
			throw new Exception("Sqrt from negative number");
		}

	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return "√";
	}

}
