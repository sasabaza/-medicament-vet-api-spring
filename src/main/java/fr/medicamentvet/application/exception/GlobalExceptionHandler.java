package fr.medicamentvet.application.exception;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.medicamentvet.application.utils.Utils;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException exception) {

		String message = exception.getMessage();

		Map<String, String> messageMap = Utils.messageMap(message);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageMap);
	}

	@ExceptionHandler(UnavailableException.class)
	public ResponseEntity<Map<String, String>> handleUnavailableException(UnavailableException exception) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
	}

	@ExceptionHandler(UnvalidException.class)
	public ResponseEntity<Map<String, String>> handleUnvalidException(UnvalidException exception) {

		String message = exception.getMessage();

		Map<String, String> messageMap = Utils.messageMap(message);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageMap);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		Map<String, String> messageMap = Utils.messageMap(exception.getMessage());

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(messageMap);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> messageMap = Utils.messageMap(exception.getMessage());

		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(messageMap);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> messageMap = Utils.messageMap(exception.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(messageMap);
	}
}
