package nelilaskin.calcModules;

import java.util.List;

/**
 * Jakolasku
 *
 */
public class DivisionCalc implements Calc {

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
	public double calc(Object[] argumentArray) throws Exception {
		// TODO Auto-generated method stub
		
		if ((double)argumentArray[2] != 0) {
			double result = (double)argumentArray[0] / (double)argumentArray[2];
			return result;
		} else {
			throw new Exception("div by zero");			
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
		return "/";
	}

}
