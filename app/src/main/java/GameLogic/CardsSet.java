package GameLogic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

//牌组，玩家打出的牌
public class CardsSet implements Comparable<CardsSet> {
    private ArrayList<Card> cards;
    private int type;//牌组类型
    private Card key;
    private final CardChecker cardChecker = CardChecker.getInstance();

    public CardsSet(){
        //this.cards=new ArrayList<>(cards);
        //这里还得调用一下CardCheck验证一下是哪个Type,不过应该是GameController拿到牌组后才进行type赋值和key判断
        //CardChecker cardChecker = CardChecker.getinstance();
    }

    public void setCards(ArrayList<Card> cards){
        this.cards = new ArrayList<>(cards);
        Collections.sort(this.cards);
    }

    public void setType(int type){
        this.type = type;
    }

    public void setKey(Card key){
        this.key = key;
    }

    //比较牌组之间的大小,只需要返回关键牌的大小对比，不过要先保证牌组类型相同，由Gamecontroller保证


    public Card getKey(){return key;}

    public int getType() { return this.type;}

    public int size(){return cards.size();}

    public Card getCard(int i){return cards.get(i);}

    public ArrayList<Card> getCards(){return cards;}

    @Override
    public int compareTo(CardsSet set) {
        int typeA = this.getType();
        int typeB = set.getType();
        if(typeA==typeB){
            int keyA = cardChecker.rankToValue(this.getKey().getRank());
            int keyB = cardChecker.rankToValue(set.getKey().getRank());
            return Integer.compare(keyA,keyB);
        }else if(typeA>4&&typeB>4){
            return Integer.compare(typeA,typeB);
        }
        return 0;
    }
}
