package GameLogic;

import java.util.ArrayList;

public class RobotPlayer extends Player{
    private Strategy strategy;//策略模式调用两种不同的出牌策略

    public RobotPlayer(String name) {
        super(name);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public ArrayList<Card> playCards(final CardsSet setOnDesktop){
        //TODO：记得new一个Arraylist用来返回,返回之前调用
        //setOnDesktop为空表示轮到自己出牌，采用最小牌策略
        if (setOnDesktop.getCards().isEmpty())
        {
            setStrategy(new StrategyMinPlayCard());
        }
        else{
            //如果有别人出牌，尽全力出牌
            setStrategy(new StrategyTryBest());
        }
        return strategy.playCard(getHand(),setOnDesktop);
    }
}
