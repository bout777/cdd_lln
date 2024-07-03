package GameLogic;

import java.util.ArrayList;
import java.lang.Override;
import java.util.Scanner;

public interface Strategy{
    ArrayList<Card> playCard(Hand hands,final CardsSet setOnDesktop);

}

//两种机器人出牌策略
//最小出牌策略


//全力出牌
