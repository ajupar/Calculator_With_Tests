package nelilaskin.calcModules;

import java.util.List;

/**
 * Laskumoduulien yhteinen rajapinta.
 *
 */
public interface Calc {
	
	// String symbol = "";
	
	/**
	 * Haetaan argumenttilista
	 * @return
	 */
	public List getArgumentList();
	
	/**
	 * Haetaan toiminnot
	 * @return
	 */
	public List getCapabilities();
	
	/**
	 * Asetetaan data
	 * @param data
	 */
	public void setData (double data);
	
	/**
	 * Suoritetaan laskutoimitus
	 * @param argumentArray
	 * @return
	 */
	public double calc(Object[] argumentArray) throws Exception;
	
	/**
	 * Hae laskutoimituksen tulos
	 * @return
	 */
	public Object getResult();
	
	public String getSymbol();
	

}
