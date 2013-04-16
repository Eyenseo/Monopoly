package objects.value.card;

/**
 * Payment is a transaction card (gain or loss).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class Payment extends Card {
    /**
     * @param name Name of the card
     * @param text Text of the card
     */
    public Payment(String name, String text, int dm) {
        super(name, text, dm);
    }

}
