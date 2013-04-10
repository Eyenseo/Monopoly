package objects.value.map;

/**
 * Created with IntelliJ IDEA.
 * User: Eyenseo
 * Date: 26.03.13
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class Tax extends NotPurchasable {
	private final int bill;
	// TODO:   Everything

	public Tax(String name, int bill) {
		super(name);
		this.bill = bill;
	}
}
