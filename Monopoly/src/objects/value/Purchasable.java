package objects.value;

/**
 * The Purchasable class is the superclass of all fields that are purchasable.
 *
 * @author Eyenseo
 * @version 0.1
 */
abstract class Purchasable extends Field {
    final int price;
    final int[] income;
    final int stage;
    Player owner;

    /**
     * @param position The value determines the numeric position of the field.
     * @param name     The value determines the name of the field.
     * @param price    The value determines the price of the field that the player has to pay to buy it.
     * @param income   The values of the array determine the income of the street in the different stages.
     * @param stage    The value determines the stage the income is at.
     */
    Purchasable(int position, String name, int price, int[] income, int stage) {
        this(position, name, price, income, stage, null);
    }

    /**
     * @param position The value determines the numeric position of the field.
     * @param name     The value determines the name of the field.
     * @param price    The value determines the price of the field that the player has to pay to buy it.
     * @param income   The values of the array determine the the income of the street in the different stages.
     * @param stage    The value determines the stage the income is at.
     * @param owner    The value determines the owner of the field. (Default null)
     */
    Purchasable(int position, String name, int price, int[] income, int stage, Player owner) {
        super(position, name);
        this.price = price;
        this.income = income;
        this.stage = stage;
        this.owner = owner;
    }

    /**
     * @return The return value is the owner of the field.
     */
    Player getOwner() {
        return this.owner;
    }

    /**
     * @param owner The value determines the owner of the field.
     */
    void setOwner(Player owner) {
        this.owner = owner;
    }
    // TODO:   getter and setter
}
