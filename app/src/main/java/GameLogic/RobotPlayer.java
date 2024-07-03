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
        return strategy.playCard(getHand(),setOnDesktop);
    }
}
