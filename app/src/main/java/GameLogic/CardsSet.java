package GameLogic;

import java.util.List;

public class CardsSet {
    public List<Card> cards;
    public int type;
    public Card key;

    public void setCards(List<Card> cards){
        this.cards = cards;
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
