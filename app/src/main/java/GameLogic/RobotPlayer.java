package GameLogic;

import java.util.ArrayList;

public class RobotPlayer extends Player implements Strategy{

    public RobotPlayer(String name) {
        super(name);
    }

    @Override
    public ArrayList<Card> playCards1(CardsSet setOnDesktop) {
        return null;
    }

    @Override
    public ArrayList<Card> playCards2(CardsSet setOnDesktop) {
        return null;
    }


}
