package objects.value;

/**
 * Created with IntelliJ IDEA.
 * User: Eyenseo
 * Date: 26.03.13
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class Ownable extends Field {
	private Player owner;

	Player getOwner() {
		return this.owner;
	}

	void setOwner(Player owner) {
		this.owner = owner;
	}
}
