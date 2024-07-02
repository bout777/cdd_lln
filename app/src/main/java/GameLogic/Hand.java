package GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

//玩家的所有手牌
public class Hand {
    private ArrayList<Card> cards;
    private CardChecker cardChecker = CardChecker.getInstance();

    public Hand() {
        cards = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    //手牌排序
    public void SortCard() {
        Collections.sort(cards);
    }

    //传递出牌，而不是出牌！记得这里前面得写判断出牌是否符合牌型，不然这里白白移除手牌了！！！！

//    public CardsSet playCards(ArrayList<Card> cardsToPlay) {
//        //cards.removeAll(cardsToPlay);从手牌中移除这些牌,ljt你注意，移除玩家的牌应该是控制器做的
//        //因为玩家只负责把这组牌组传给GameController,再由它调用CardCheck看看牌是否合适，合适后再由控制器删除掉玩家的手牌
//        CardsSet cardsSet=new CardsSet();
//        cardsSet.setCards(cards);
//        cardChecker.SetTypeAndKey(cardsSet);
//        //返回牌组
//        return cardsSet;
//    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    public ArrayList<Card> getCards(){return this.cards;}

    public boolean isEmpty(){
        return this.cards.isEmpty();
    }

    public int size(){return this.cards.size();}

}