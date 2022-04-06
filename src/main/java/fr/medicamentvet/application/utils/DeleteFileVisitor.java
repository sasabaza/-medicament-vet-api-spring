package fr.medicamentvet.application.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * The class represents a visitor of files. The aim of the class is to delete
 * file(s) and finally delete the directories.
 */
public final class DeleteFileVisitor implements FileVisitor<Path> {

	@Override
	public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		delete(file);

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path directory, IOException exc) throws IOException {
		delete(directory);

		return FileVisitResult.CONTINUE;
	}

	private static boolean delete(Path file) throws IOException {
		return Files.deleteIfExists(file);
	}
}
