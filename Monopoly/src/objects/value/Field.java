package objects.value;

/**
 * Created with IntelliJ IDEA.
 * User: Eyenseo
 * Date: 26.03.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public abstract class Field {
	private int position;

	void Field(int position) {
		this.position = position;
	}

	int getPosition() {
		return this.position;
	}
}
