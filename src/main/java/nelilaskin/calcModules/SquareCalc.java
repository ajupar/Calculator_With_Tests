package nelilaskin.calcModules;

import java.util.List;

/**
 * Toinen potenssi
 *
 */
public class SquareCalc implements Calc {

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

	@Override
	public double calc(Object[] argumentArray) throws Exception {
		// TODO Auto-generated method stub
		
		double num = (double) argumentArray[0];	
		
		return num*num;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return "x²";
	}

}
