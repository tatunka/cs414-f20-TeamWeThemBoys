 package engine;

public class IllegalMoveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5845949091552704569L;
	
private String errorMessage;
	
	public IllegalMoveException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
	
	public String toString() {
		return("Illegal Move Exception: " + errorMessage);
	}

}
