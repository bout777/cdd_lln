package GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

//牌组，玩家打出的牌
public class CardsSet {
    public ArrayList<Card> cards;
    public int type;
    public Card key;


    public void setCards(ArrayList<Card> cards){
        this.cards = new ArrayList<>(cards);
        Collections.sort(cards);
    }

    public void setType(int type){
        this.type = type;
    }

    public void setKey(Card key){
        this.key = key;
    }

    public int getType() { return this.type;}

    public int size(){return cards.size();}

    public Card getCard(int i){return cards.get(i);}

    public ArrayList<Card> getCards(){return cards;}
}
