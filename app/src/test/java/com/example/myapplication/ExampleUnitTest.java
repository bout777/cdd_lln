package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;


import GameLogic.Card;
import GameLogic.CardsSet;
import GameLogic.GameController;
import GameLogic.Hand;
import GameLogic.RobotPlayer;
import GameLogic.StrategyMinPlayCard;
import GameLogic.StrategyTryBest;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        //RobotPlayer robotPlayer=new RobotPlayer("P");

        //robotPlayer.setStrategy(new StrategyMinPlayCard());
        StrategyTryBest strategyTryBest=new StrategyTryBest();
        Hand hands=new Hand();

        Card card1=new Card("Diamond","3");
        Card card2=new Card("Club","3");
        Card card3=new Card("Heart","Q");

        Card card4=new Card("Spade","Q");
        Card card5=new Card("Spade","K");
        Card card6=new Card("Spade","J");
        Card card7=new Card("Spade","10");
        Card card8=new Card("Spade","9");

        Card card9=new Card("Heart","3");
        Card card10=new Card("Heart","4");
        Card card11=new Card("Heart","5");
        Card card12=new Card("Heart","6");
        Card card13=new Card("Heart","7");
        hands.addCard(card1);
        hands.addCard(card2);
        hands.addCard(card3);
        hands.addCard(card4);
        hands.addCard(card5);
        hands.addCard(card6);
        hands.addCard(card7);
        hands.addCard(card8);
        hands.addCard(card9);
        hands.addCard(card10);
        hands.addCard(card11);
        hands.addCard(card12);
        hands.addCard(card13);
        hands.SortCard();

        ArrayList<Card> set=new ArrayList<>();
        set.add(card9);
        set.add(card10);
        set.add(card12);
        set.add(card11);
        set.add(card13);
        CardsSet setOnDesktop=new CardsSet();
        setOnDesktop.setCards(set);
        setOnDesktop.setType(9);
        setOnDesktop.setKey(card9);

        ArrayList<Card> result=strategyTryBest.playCard(hands,setOnDesktop);

        System.out.println("\n开始测试");

        //System.out.println("手牌数"+result.size());
        if(result==null){
            System.out.println("没有找到哦");
            return;
        }
        for(int i=0;i< result.size();i++)
            System.out.println(result.get(i).getIntRank());

        System.out.println("\n");
    }

    private void test(){
        GameController controller = new GameController();
        controller.startGame();
    }
}