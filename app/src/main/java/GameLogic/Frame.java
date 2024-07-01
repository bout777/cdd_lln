package GameLogic;


import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class Frame{
    private CardChecker cardChecker;//牌出法检查和控制

    public static Player[] players = new Player[4];//桌子上的四个玩家
    private Deck deck;//发给玩家的牌库
    private int currentScore = 10; //当前分数
    private int currentID = 0; //当前操作的人的id
    private int currentRound = 0; //本次游戏轮数
    public static CardsSet cardsOnDesktop = null; //最新的一手牌
    private int timeLimit = 300;//倒计时
    private int result[] = new int[4]; //胜负得分信息
    private int op = -1; //游戏进度，-1重新开始,0游戏中，1本局游戏结束
    public static int boss = 0; //第一个出牌的人

    //还差一些pass确认变量得仔细考虑就暂时没加上去

    //各种ui的位置，记得调整一下
    private int[][] timeLimitPosition = {{130, 190}, {80, 80}, {360, 80}, {150, 80}}; //剩余时间View的位置
    private int[][] passPosition = {{130, 190}, {80, 80}, {360, 80}, {150, 80}};
    private int[][] playerLatestCardsPosition = {{130, 140}, {80, 60}, {360, 60}, {250, 60}};
    private int[][] playerCardsPosition = {{30, 210}, {30, 60}, {410, 60}, {200, 60}};
    private int[][] scorePosition = {{70, 290}, {70, 30}, {340, 30}, {240, 30}};
    private int[][] iconPosition = {{30, 270}, {30, 10}, {410, 10}, {200, 10}};
    private int buttonPositionX = 240, buttonPositionY = 160;


    public Frame(){

    }

    //当前游戏状态
    public void gameLogic() {
        switch (op) {
            //重开，初始化
            case -1:
                init();
                op = 0;
                break;
            case 0:
                //确认胜利玩家，计算分数
                GameOver();
                break;
            case 1:
                break;
        }
    }

    //控制界面
    public void controlPaint(Canvas canvas) {
        switch (op) {
            case -1:
                break;
            case 0:
                //游戏中界面
                paintGaming(canvas);
                break;
            case 1:
                //结算界面
                paintResult(canvas);
                break;
        }
    }

    //游戏结束的操作
    private void GameOver() {
        //找到赢家
        //重置op
        //计算分数
    }

    //初始化游戏
    public void init(){
        //变量初始化
        //发牌
        //将每个牌发给到每一位玩家
        //调用函数找到拿到方片三的玩家的Id

        //初始化玩家
        //初始化出牌顺序
    }

    //找到持有方片三的玩家
    public int fapai(int[] cards) {

    }

    //玩家每打出一次牌，重新设置上家的牌
    public void setCard(){

    }

    private void pass() {
        //清空当前不要牌的人的最后一手牌
    }

    //确认下一出牌的玩家
    private void nextPerson() {
        currentID = currentID == 3 ? 0 : currentID + 1;
        currentRound++;//轮数++
        timeLimit=300;
    }

    //获取上一家出的牌组
    public CardsSet getTopCardSet() {
        return cardsOnDesktop;
    }

    //ui，轮到玩家出牌时的界面
    private void paintGaming(Canvas canvas) {

    }

    //画出牌倒计时
    private void paintTimeLimit(Canvas canvas){

    }

    //画跳过出牌
    private void paintPass(Canvas canvas,int id){

    }

    //画结算结果
    private void paintResult(Canvas canvas){

    }

    //触摸事件
    private void onTouch(int x,int y){

    }

    //重新开始
    public void restart(){
        op=1;
    }

}


