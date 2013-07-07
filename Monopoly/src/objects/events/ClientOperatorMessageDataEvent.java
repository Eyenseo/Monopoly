package objects.events;

import objects.value.MessageData;

import java.util.EventObject;

/**
 * The ClientOperatorMessageDataEvent is thrown at the client if there is new MessageData
 */
public class ClientOperatorMessageDataEvent extends EventObject {
	private final MessageData messageData;

	/**
	 * @param source      the value determines the source of the event
	 * @param messageData the value determines the MessageData of the event
	 */
	public ClientOperatorMessageDataEvent(Object source, MessageData messageData) {
		super(source);
		this.messageData = messageData;
	}

	/**
	 * @return the return value is the MessageData of the event
	 */
	public MessageData getMessageData() {
		return messageData;
	}
}
