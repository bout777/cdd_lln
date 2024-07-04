package GameLogic;

import java.util.ArrayList;

public interface Strategy{
    public abstract ArrayList<Card> playCards1(CardsSet setOnDesktop,int Round);
    public abstract ArrayList<Card> playCards2(CardsSet setOnDesktop,int Round);
}
