package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Hand hand;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public void playCard(List<Card> cards) {
        hand.playCards(cards);
    }
}
