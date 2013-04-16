package objects.value.card;

/**
 * PayFineTakeCard is special card (pay fine or draw a card).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class PayFineTakeCard extends Card {
    /**
     * @param name Name of the card
     * @param text Text of the card
     */
    public PayFineTakeCard(String name, String text) {
        super(name, text);
    }

}
