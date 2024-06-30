package GameLogic;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;
public class CardsSet {
    public List<Card> cards;
    public int type;
    public Card key;

    public void setCards(List<Card> cards){
        this.cards = cards;
        Collections.sort(cards);
    }

    public void setType(int type){
        this.type = type;
    }

    public void setKey(Card key){
        this.key = key;
    }

    public int size(){return cards.size();}

    public Card get(int i){return cards.get(i);}
}
