package GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

//玩家的所有手牌
public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void SortCard() {
        Collections.sort(cards);
    }
    public void playCards(List<Card> cardsToPlay) {
        cards.removeAll(cardsToPlay);
    }

    public List<Card> getCards() {
        return cards;
    }

}