package GameLogic;

import java.util.ArrayList;

public class RobotPlayer extends Player{
    public Strategy strategy;//策略模式调用两种不同的出牌策略

    public RobotPlayer(String name) {
        super(name);
    }


    @Override
    public ArrayList<Card> playCards(final CardsSet setOnDesktop){
        //TODO：记得new一个Arraylist用来返回,返回之前调用
        return null;
    }
}
