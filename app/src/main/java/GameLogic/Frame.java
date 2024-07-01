package GameLogic;


import android.graphics.Canvas;

import java.util.ArrayList;

public class Frame{
    private CardChecker cardChecker; //牌出法检查和控制
    public ArrayList<Player> players = new ArrayList<Player>(); //桌子上的四个玩家
    public Player HostPlayer;
    private Deck deck; //发给玩家的牌库
    private int currentID = 0; //当前操作的人的id
    private int hostID;

    public CardsSet setOnDesktop = new CardsSet(); //最新的一手牌
    public boolean GameOver;
    public ArrayList<Integer> score;


    public Frame(){
        setPlayers("林连南");//玩家上桌
        startGame();//游戏开始
    }



    //找到持有方片三的玩家
    public int findFirstPlayer() {
        for(int i = 0;i<4;i++)
            if(players.get(i).getHand().getCard(0).getSuit().equals("Diamond")&&players.get(i).getHand().getCard(0).getRank().equals("3"))
                return i;
        return -1;
    }

    //玩家每打出一次牌，重新设置桌上的牌
    public void CoverCard(CardsSet newSet){
        updateSetOnDesktop(newSet);
        //TODO:更新ui
    }


    //更新确认下一出牌的玩家
    private void updateRound() {
        this.currentID++;
        this.currentID%=4;
    }

    //画出牌倒计时
    private void paintTimeLimit(Canvas canvas){

    }

    //设置玩家
    public void setPlayers(String hostname){
        this.HostPlayer = new HumanPlayer(hostname);
        players.add(HostPlayer);

        Player bot1 = new RobotPlayer("林连西");
        players.add(bot1);
        Player bot2 = new RobotPlayer("林连北");
        players.add(bot2);
        Player bot3 = new RobotPlayer("林连东");
        players.add(bot3);
    }

    //分发卡牌
    public void dealCards() {
        int numPlayers = players.size();
        while (deck.size() > 0) {
            for (Player player : players) {
                if (deck.size() > 0) {
                    player.addCard(deck.deal());
                }
            }
        }
    }

    //设置主机玩家ID
    public void setHostID(){this.hostID = 0;}

    //绘制玩家的模型
    public void paintPlayers(){
        /*TODO：
            展示bot形象
         */
    }

    //绘制玩家手牌
    public void paintHand(){
        /*TODO:
            关联玩家的手牌Player.getHand()<->ui控件
            绑定点击和双击事件，分别对应选取和放下，或者其他易于实现的方式
            维护一个List<Card>表示玩家已经选取的牌,保证List是有序的(已经实现了比较的方法）
          TODO：
            添加出牌和Pass的ui控件
          TODO：
            出牌UI的点击事件<->if(PlayCardSet(ArrayList<Card>))方法
            条件分支
            如果方法返回false玩家需要重新操作，即重绘UI
            如果返回true，玩家出牌成功，调用CoverCard
         */
    }

    //绘制结算界面
    private void paintResult(){
        /*TODO:
            创建一个新的窗口
            调用calScore方法获取每个玩家的分数并展示
            展示退出和新游戏的按钮
         */
    }

    //出牌按钮点击事件的响应函数
    public boolean PlayCardSet(ArrayList<Card> cards){

        //接受一个牌数组，整合成CardSet
        CardsSet setToPlay= new CardsSet();
        setToPlay.setCards(cards);
        CardChecker.SetTypeAndKey(setToPlay);

        //如果识别不出种类返回false
        if(setToPlay.getType()==CardsType.error)
            return false;

        //如果比桌上的牌小返回false
        if(CardChecker.CompareTo(setToPlay,this.setOnDesktop)<=0)
            return false;

        //符合条件，可以打出
        return true;
    }

    //开始游戏按钮的响应函数
    public void startGame(){

        deck = new Deck();
        dealCards();//发牌
        this.currentID = findFirstPlayer(); //获取首先出牌的玩家

        //本机ID，由于实现的是单机，其实就是真人玩家的ID
        setHostID();

        //绘制游戏界面
        paintPlayers();
        paintHand();

        while (true){
            if(!GameOver) {
                if (this.hostID == this.currentID) {
                /*
                    TODO:
                        开放玩家对手牌（HAND）的使用权限，即绑定点击事件
                        玩家有出牌，Pass两个选项
                */
                    if(players.get(this.currentID).getHand().isEmpty())
                        GameOver = true;
                    updateRound();
                    continue;
                } else {
                    //获取bot实例
                    Player bot = players.get(this.currentID);

                    //获取bot的出牌列表
                    ArrayList<Card> cardsToPlay = bot.playCards(this.setOnDesktop);

                    //如果非空，把bot打出的牌整合成牌组，放入桌面
                    if(cardsToPlay!=null) {
                        bot.playCard(cardsToPlay);
                        this.setOnDesktop.setCards(cardsToPlay);
                        CardChecker.SetTypeAndKey(this.setOnDesktop);
                    }
                    //
                    /*
                        TODO:
                              用players.get(this.currentID)获取机器人实例
                              用Robot的方法更新this.setOnDesktop
                              调用CoverCard
                              Robot方法的返回值作为参数传入CoverCard
                     */
                    if(players.get(this. currentID).getHand().isEmpty())
                        GameOver = true;
                    updateRound();
                    continue;
                }
            }else{
                overGame();
                break;
            }
        }
    }

    //游戏结束
    public void overGame(){
        paintResult();
    }

    //计算分数的函数，返回一个含有每个玩家分数的数组，用于结算界面的绘制
    public ArrayList<Integer> calScore(Player player){
        ArrayList<Integer> baseScore = new ArrayList<>(); //保存底分
        ArrayList<Integer> finalScore = new ArrayList<>(); //保存最终分数
        for(Player p:players){
            Hand hand = p.getHand();
            int n = hand.size();
            if(hand.getCard(hand.size()-1).getRank().equals("2"))//如果有2，底分翻倍
                n*=2;
            switch (n){
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    baseScore.add(n); // n < 8 时，牌分为 n
                    break;
                case 8:
                case 9:
                    baseScore.add(2*n); // 8 ≤ n < 10 时，牌分为 2n
                    break;
                case 10:
                case 11:
                case 12:
                    baseScore.add(3*n);  // 10 ≤ n < 13 时，牌分为 3n
                    break;
                case 13:
                    baseScore.add(4*n);  // n = 13 时，牌分为 4n
                    break;
            }
        }

        for (int i = 0;i<baseScore.size();i++){
            //获取其他玩家的底分
            int SumOfOtherBaseScore = 0;
            for (int j = 0;j<baseScore.size();j++)
                if(j!=i)
                    SumOfOtherBaseScore+=baseScore.get(j);

            //自己的分数等于其他玩家的底分总和减去三倍自己的底分
            finalScore.add(SumOfOtherBaseScore-3*baseScore.get(i));
        }
        return finalScore;
    }

    //更新桌上牌组
    public void updateSetOnDesktop(CardsSet set){
        this.setOnDesktop.setCards(set.getCards());
        CardChecker.SetTypeAndKey(this.setOnDesktop);
    }

}


