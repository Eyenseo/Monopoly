package objects.exceptions.core;

import objects.exceptions.MessageStackException;

public class LoaderException extends MessageStackException {
	public LoaderException(String message) {
		super(message);
	}

	public LoaderException(String message, Throwable cause) {
		super(message, cause);
	}
}
