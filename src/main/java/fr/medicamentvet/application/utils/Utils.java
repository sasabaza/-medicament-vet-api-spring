package fr.medicamentvet.application.utils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * The class contains various methods employed in the others classes.
 */
public final class Utils {

	private static final String MESSAGE_TEXT = "message";

	private static final int MAX_DEPTH = 2;

	private Utils() {
		super();
	}

	/**
	 * The method receives a String message and returns a
	 * {@code HashMap<String, String>}.
	 * 
	 * @param message String
	 * @return {@code HashMap<String, String>} where the key is String "message" and
	 *         the value the message parameter
	 */
	public static Map<String, String> messageMap(String message) {
		Map<String, String> messageMap = new HashMap<>();
		messageMap.put(MESSAGE_TEXT, message);

		return messageMap;
	}

	/**
	 * The method deletes the directory and all the content.
	 * 
	 * @param directory Path of the directory
	 * @return Empty response if the status is OK or an error message when there is
	 *         an IOException
	 */
	public static void deleteDirectory(Path directory) {

		DeleteFileVisitor deleteFileVisitor = new DeleteFileVisitor();
		EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

		try {
			Files.walkFileTree(directory, opts, MAX_DEPTH, deleteFileVisitor);
		} catch (IOException e) {
			ApplicationLogger.throwableLog(e);
		}
	}
}
