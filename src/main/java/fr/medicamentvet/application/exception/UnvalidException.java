package fr.medicamentvet.application.exception;

/**
 * This class represents a UnvalidException object.
 */
public class UnvalidException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnvalidException(String message) {
		super(message);
	}
}