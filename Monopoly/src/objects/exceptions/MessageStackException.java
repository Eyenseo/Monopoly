package objects.exceptions;

//JAVADOC
public abstract class MessageStackException extends Exception {
	//JAVADOC
	protected MessageStackException(String message) {
		super(message);
	}

	//JAVADOC
	protected MessageStackException(String message, Throwable cause) {
		super(message, cause);
	}

	//JAVADOC
	public String getMessageStack() {
		String message = getMessage() + '\n';
		if(getCause() != null) {
			message += getCauseMessage();
		}
		return message;
	}

	//JAVADOC
	private String getCauseMessage() {
		if(getCause() instanceof MessageStackException) {
			return getCauseMessage((MessageStackException) getCause());
		} else {
			return getCause().getMessage();
		}
	}

	//JAVADOC
	private String getCauseMessage(MessageStackException cause) {
		String message = null;
		if(cause.getCause() != null) {
			message = cause.getMessage() + '\n';
			message += cause.getCauseMessage((MessageStackException) cause.getCause());
		}
		return message + "\n";
	}
}
