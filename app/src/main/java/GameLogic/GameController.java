package GameLogic;

import java.util.ArrayList;
public class GameController {

    private final CardChecker  cardChecker = CardChecker.getInstance();; //牌出法检查和控制
//    public ArrayList<Player> players = new ArrayList<Player>(); //桌子上的四个玩家
//    public ArrayList<RobotPlayer> robotPlayers = new ArrayList<RobotPlayer>();
//    public Player HostPlayer;
//    private Deck deck; //发给玩家的牌库
    private int currentID = 0; //当前操作的人的id
    private int hostID;
    private final Desk desk = new Desk();
    private Frame frame;
    private GameData gameData;
    private boolean ifPlayed = false;
    private boolean ifPass = false;

    public GameController(){
        setPlayers("林连南");//玩家上桌
        startGame();//游戏开始
        this.frame = new Frame(this);
    }

    //找到持有方片三的玩家
    public int findFirstPlayer() {
        for (int i = 0;i<gameData.getPlayers().size();i++)
                if(gameData.getPlayers().get(i).getHand().getCard(0).getSuit().equals("Diamond")&&gameData.getPlayers().get(i).getHand().getCard(0).getRank().equals("3"))
                    return i;
        //
        return -1;
    }


    //更新确认下一出牌的玩家
    private void updateRound() {
        gameData.setCurrentID((gameData.getCurrentID()+1)%4);
    }

//    //画出牌倒计时
//    private void paintTimeLimit(Canvas canvas){
//
//    }

    //设置玩家
    public void setPlayers(String hostname){
        ArrayList<Player> players = new ArrayList<Player>();
        ArrayList<RobotPlayer> robotPlayers = new ArrayList<RobotPlayer>();

        HumanPlayer host = new HumanPlayer(hostname);
        players.add(host);

        RobotPlayer bot1 = new RobotPlayer("林连西");
        players.add(bot1);
        robotPlayers.add(bot1);
        RobotPlayer bot2 = new RobotPlayer("林连北");
        players.add(bot2);
        robotPlayers.add(bot2);
        RobotPlayer bot3 = new RobotPlayer("林连东");
        players.add(bot3);
        robotPlayers.add(bot3);
        robotPlayers.add(0,null);

        gameData.setHostPlayer(host);
        gameData.setPlayers(players);
        gameData.setRobotPlayers(robotPlayers);
    }

    //分发卡牌
    public void dealCards() {
        while (!gameData.getDeck().isEmpty()) {
            for (Player player : gameData.getPlayers()) {
                if (!gameData.getDeck().isEmpty()) {
                    player.addCard(gameData.getDeck().deal());
                }
            }
        }

        //发完牌之后自动排序
        for (Player player:gameData.getPlayers())
            player.getHand().SortCard();
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
    public void PlayCardSet(ArrayList<Card> cards){

        //接受一个牌数组，整合成CardSet
        CardsSet setToPlay= new CardsSet();
        setToPlay.setCards(cards);
        cardChecker.SetTypeAndKey(setToPlay);

        //如果识别不出种类返回false
        if(setToPlay.getType()==CardsType.error){
            frame.unlockPlayerView();
            return;
        }

        //如果比桌上的牌小返回false
        if(setToPlay.compareTo(desk.getSetOnDesktop())<=0)
            return;

        //符合条件,玩家成功打出

        //更新桌面牌
        frame.CoverCard(cards);

        //删除玩家已经打出的牌
        gameData.getHostPlayer().getHand().getCards().removeAll(cards);

        //展示已经更新的手牌
        frame.paintHand(gameData.getHostPlayer().getHand().getCards());

        //锁定玩家视角
        frame.lockPlayerView();

        //检测游戏是否结束
        if(gameData.getHostPlayer().getHand().isEmpty()){
            gameData.setResultScore(calScore());
            frame.paintResult(gameData.getResultScore());
            return;
        }

        /*
            TODO:
                机器人出牌
                ui渲染
         */

        frame.unlockPlayerView();
    }

    //Pass按钮点击事件的相应函数
    public void PlayerPass(){
        frame.lockPlayerView();
        updateRound();
    }

    //开始游戏按钮的响应函数
    public void startGame(){

        dealCards();//发牌
        this.currentID = findFirstPlayer(); //获取首先出牌的玩家

        //绘制游戏界面
        frame.paintPlayers(gameData.getPlayers());
        frame.paintHand(gameData.getHostPlayer().getHand().getCards());

//        while (true){
//            if(!desk.IfGameOver()) {
//                if (this.hostID == this.currentID) {
//                /*
//                    TODO:
//                        开放玩家对手牌（HAND）的使用权限，即绑定点击事件
//                        玩家有出牌，Pass两个选项
//                */
//                    frame.unlockPlayerView();
//
//                    //等待玩家动作
//                    while (!(ifPass||ifPlayed)){}
//
//                    if(gameData.getPlayers().get(this.currentID).getHand().isEmpty())
//                        desk.setIfGameOver(true);
//                    updateRound();
//                    continue;
//                } else {
//                    //获取bot实例
//                    RobotPlayer bot = gameData.getRobotPlayers().get(this.currentID);
//
//                    //获取bot的出牌列表
//                    final ArrayList<Card> cardsToPlay = bot.playCards1(desk.getSetOnDesktop());
//
//                    //如果非空，把bot打出的牌整合成牌组，放入桌面
//                    if(cardsToPlay!=null) {
//                        //删除相应手牌
//                        bot.getHand().getCards().removeAll(cardsToPlay);
//
//                        //更新牌组
//                        frame.CoverCard(cardsToPlay);
//                    }
//                    //
//                    /*
//                        TODO:
//                              用players.get(this.currentID)获取机器人实例
//                              用Robot的方法更新this.setOnDesktop
//                              Robot方法的返回值作为参数传入CoverCard,调用CoverCard
//                     */
//                    if(gameData.getRobotPlayers().get(gameData.getCurrentID()).getHand().isEmpty())
//                        desk.setIfGameOver(true);
//                    updateRound();
//                    continue;
//                }
//            }else{
//                overGame();
//                break;
//            }
//        }
    }


    //游戏结束
    public void overGame(){
        frame.paintResult(gameData.getResultScore());
    }

    //计算分数的函数，返回一个含有每个玩家分数的数组，用于结算界面的绘制
    public ArrayList<Integer> calScore(){
        ArrayList<Integer> baseScore = new ArrayList<>(); //保存底分
        ArrayList<Integer> finalScore = new ArrayList<>(); //保存最终分数
        for(Player p: gameData.getPlayers()){
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
    public void updateSetOnDesktop(ArrayList<Card> cards){
        desk.getSetOnDesktop().setCards(cards);
        cardChecker.SetTypeAndKey(desk.getSetOnDesktop());
    }

    public void setIfPlayed(boolean ifPlayed){
        this.ifPlayed = ifPlayed;
    }

    public void setIfPass(boolean ifPass){
        this.ifPass = ifPass;
    }
}
