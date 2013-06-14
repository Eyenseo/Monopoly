package ui.gui.layout_manager;

/**
 * PurchasableCardLayoutConstraints provides information for the PurchasableCardLayout
 * Probably it's not needed anymore
 */
public class PurchasableCardLayoutConstraints {
	public int                                 position;
	public PurchasableCardLayoutConstraintType type;

	/**
	 * <ul>
	 * <li>STREET: The Component is a Street</li>
	 * <li>STATION: The Component is a Station</li>
	 * <li>FACILITY: The Component is a Facility</li>
	 * </ul>
	 */
	public enum PurchasableCardLayoutConstraintType {
		STREET, STATION, FACILITY
	}

	public PurchasableCardLayoutConstraints() {
		position = -1;
		type = null;
	}
}
