package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void SortCard() {

    }
    public void playCards(List<Card> cardsToPlay) {
        cards.removeAll(cardsToPlay);
    }

    public List<Card> getCards() {
        return cards;
    }

}