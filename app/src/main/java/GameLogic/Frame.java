package GameLogic;


import android.graphics.Canvas;

import java.util.ArrayList;

public class Frame{

    private CardsSet setOnDesktop; //最新的一手牌
    private boolean GameOver;
    private int RoundNumber;//轮数


    public Frame(){
        setOnDesktop= new CardsSet();
        GameOver=false;
        RoundNumber=0;
    }

    //更新轮数和上家牌
    public void RenewSetOnDesktop(CardsSet setOnDesktop) {
        this.setOnDesktop = setOnDesktop;
        RoundNumber++;
    }

    //判断游戏是否结束
    public boolean IfGameOver(){
        return GameOver;
    }

    public void setIfGameOver(boolean Val){this.GameOver = Val;}

    //获取上家牌和轮数
    public int getRoundNumber() {
        return RoundNumber;
    }

    public CardsSet getSetOnDesktop() {
        return setOnDesktop;
    }
}


