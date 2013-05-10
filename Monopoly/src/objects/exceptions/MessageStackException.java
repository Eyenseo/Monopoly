package objects.exceptions;

//JAVADOC
public abstract class MessageStackException extends Exception {

	//JAVADOC
	public MessageStackException(String message) {
		super(message);
	}

	//JAVADOC
	public MessageStackException(String message, Throwable cause) {
		super(message, cause);
	}

	//JAVADOC
	public String getMessageStack() {
		String message = getMessage() + '\n';
		if(this.getCause() != null) {
			message += getCauseMessage();
		}
		return message;
	}

	//JAVADOC
	private String getCauseMessage() {
		if(this.getCause() instanceof MessageStackException) {
			return getCauseMessage((MessageStackException) this.getCause());
		} else {
			return this.getCause().getMessage();
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
