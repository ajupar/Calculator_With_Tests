package nelilaskin.calcModules;

/**
 * Eri laskuoperaatioiden luetteloarvot
 *
 */
public enum calcEnums {
	
	PLUS("+"), MINUS("-"), DIVIDE("/"), PRODUCT("*");
	
	private String symbol;
	
	calcEnums(String symbol) {
		this.symbol = symbol;
		
	}
	
	public String toString() {
		return symbol;
	}
	
}
