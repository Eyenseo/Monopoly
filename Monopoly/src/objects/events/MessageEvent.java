package objects.events;

import objects.value.MessageData;

import java.util.EventObject;

/**
 * The MessageEvent is thrown at the server if there is either chat text or a card text (event or community) has to be
 * transferred
 */
public class MessageEvent extends EventObject {
	MessageData messageData;

	/**
	 * @param source      the value determines the source of the event
	 * @param messageData the value determines the MessageData of the event
	 */
	public MessageEvent(Object source, MessageData messageData) {
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
