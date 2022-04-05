package fr.medicamentvet.application.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class contains one method that can log a Throwable.
 */
public final class ApplicationLogger {

	private static final Logger ApplicationLogger = Logger.getLogger(ApplicationLogger.class.getName());

	private ApplicationLogger() {
		super();
	}

	public static void throwableLog(Throwable throwable) {
		ApplicationLogger.log(Level.SEVERE, throwable.getMessage(), throwable);
	}
}
