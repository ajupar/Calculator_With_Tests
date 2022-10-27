package nelilaskin.calcModules;

import java.util.List;

/**
 * Yhteenlasku
 *
 */
public class SumCalc implements Calc {

	@Override
	public List getArgumentList() {
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
	public double calc(Object[] argumentArray) {
		// TODO Auto-generated method stub
		
		double sum = (double)argumentArray[0] + (double)argumentArray[2];
		
		return sum;
		
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return "+";
	}

}
