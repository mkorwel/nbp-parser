package pl.parser.nbp.core.validate.exception;

/**
 * Created by mateusz on 31/01/16.
 */
public class ValidateException extends RuntimeException {
	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}
}
