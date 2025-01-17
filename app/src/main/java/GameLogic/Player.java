package GameLogic;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private final String name;
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

    //成功出牌
//    public CardsSet playCard(ArrayList<Card> cards) {
//        return hand.playCards(cards);
//    }

    public abstract ArrayList<Card> playCards(final CardsSet setOnDesktop);
}
