package objects.card;
//JAVADOC

/**
 * StreetWork is the street work card (Strassen arbeiten).
 *
 * @version 1
 */
public class StreetWork extends Card {
	private int dmHouse;
	private int dmHotel;

	/**
	 * @param name    The value determines the name of the Card.
	 * @param text    The value determines the text of the Card.
	 * @param dmHouse The value determines the amount that has to be payed for a house.
	 * @param dmHotel The value determines the mout that has to be payed for a hotel.
	 */
	public StreetWork(String name, String text, int dmHouse, int dmHotel) {
		super(name, text);
		this.dmHouse = dmHouse;
		this.dmHotel = dmHotel;
	}
}
