package fr.medicamentvet.application.exception;

/**
 * This class represents a NotFoundException object. It is mainly used to throw
 * an exception whenever id parameter does not exist or where the application
 * sends to the client a message error (status code 404)
 */
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
}
