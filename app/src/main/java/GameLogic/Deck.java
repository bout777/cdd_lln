package GameLogic;

import java.util.ArrayList;
import java.util.Collections;

public class Deck { //一整副牌，用于分发到玩家
    private final ArrayList<Card> cards;
    public Deck() {
        cards = new ArrayList<Card>();
        String[] suits = {"Heart", "Diamond", "Club", "Spade"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits)
            for (String rank : ranks)
                cards.add(new Card(suit, rank));

        shuffle(); //洗牌
    }

    public Card deal() {
        return cards.remove(cards.size()-1);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public int size(){
        if(!cards.isEmpty())
            return cards.size();
        return 0;
    }

    public boolean isEmpty(){return cards.isEmpty();}

}
