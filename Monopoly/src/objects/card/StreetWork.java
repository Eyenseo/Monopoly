package objects.card;

/**
 * StreetWork is the street work card (Strassen arbeiten).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class StreetWork extends Card {
	private int dmHouse;
	private int dmHotel;

	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public StreetWork(String name, String text, int dmHouse, int dmHotel) {
		super(name, text);
		this.dmHouse = dmHouse;
		this.dmHotel = dmHotel;
	}
}
