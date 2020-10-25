package engine;

public class IllegalPositionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2720342202072604534L;

	private String errorMessage;
	
	public IllegalPositionException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
	
	public String toString() {
		return("Illegal Position Exception: " + errorMessage + " is an illegal position");
	}
}
