package objects.exceptions;

/**
 * The MessageStackException is the superclass of all custom exceptions
 */
public abstract class MessageStackException extends Exception {
	/**
	 * @param message the value determines the message regarding the error
	 */
	protected MessageStackException(String message) {
		super(message);
	}

	/**
	 * @param message the value determines the message to regarding the error
	 * @param cause   the value determines the cause of the error
	 */
	protected MessageStackException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @return the return value is is a paragraph of all messages from all exception with their curses
	 */
	public String getMessageStack() {
		String message = getMessage() + '\n';
		if(getCause() != null) {
			message += getCauseMessage();
		}
		return message;
	}

	/**
	 * The method will call it's overloaded method with its cause if it has one
	 *
	 * @return The return value is is a paragraph of all messages from all exception with their curses
	 */
	private String getCauseMessage() {
		if(getCause() instanceof MessageStackException) {
			return getCauseMessage((MessageStackException) getCause());
		} else {
			return getCause().getMessage();
		}
	}

	/**
	 * The method will call it self with its cause if it has one
	 *
	 * @return The return value is is a paragraph of all messages from all exception with their curses
	 */
	private String getCauseMessage(MessageStackException cause) {
		String message = null;
		if(cause.getCause() != null) {
			message = cause.getMessage() + '\n';
			message += cause.getCauseMessage((MessageStackException) cause.getCause());
		}
		return message + "\n";
	}
}
