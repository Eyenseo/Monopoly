package objects.value.action;

/**
 * The FreePlayerData class is used to send a request to the server that the player wants to be freed (money/jailbreak)
 */
public class FreePlayerData extends ActionData {
	private final boolean useJailbreak;

	/**
	 * @param userId       the value determines the player id the data is from
	 * @param useJailbreak the value determines if the player wants to use money(false) or if he wants to use a jailbreak
	 *                     card (true)
	 */
	public FreePlayerData(int userId, boolean useJailbreak) {
		super(userId);
		this.useJailbreak = useJailbreak;
	}

	/**
	 * @return the return value is true if the player wants to use a jailbreak card to come free
	 */
	public boolean isUseJailbreak() {
		return useJailbreak;
	}
}
