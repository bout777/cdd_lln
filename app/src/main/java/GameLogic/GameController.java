package GameLogic;

import android.util.Log;

import java.util.ArrayList;
public class GameController {

    private final CardChecker  cardChecker = CardChecker.getInstance();; //牌出法检查和控制

    private final Desk desk = new Desk();
    private final Frame frame;
    private GameData gameData = new GameData();
    private boolean ifPlayed = false;
    private boolean ifPass = false;

    public GameController(Frame frame){
        this.frame = frame;
        setPlayers("林连南");//玩家上桌

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
    private void updateCurrentID() {
        gameData.setCurrentID((gameData.getCurrentID()+1)%4);
    }

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


    //出牌按钮点击事件的响应函数
    public void PlayCardSet(ArrayList<Card> cards){

        //接受一个牌数组，整合成CardSet
        CardsSet setToPlay= new CardsSet();
        setToPlay.setCards(cards);
        cardChecker.SetTypeAndKey(setToPlay);

        if(gameData.getGameRound()!=gameData.getHostPlayer().getGameRound()){
            if(setToPlay.compareTo(desk.getSetOnDesktop())<0||setToPlay.getType()==CardsType.error){
                frame.unlockPlayerView();
                return;
            }
        }


        //符合条件,玩家成功打出
        //更新桌面牌
        frame.CoverCard(cards);

        //删除玩家已经打出的牌
        gameData.getHostPlayer().getHand().getCards().removeAll(cards);

        //展示已经更新的手牌
        frame.paintHand(gameData.getHostPlayer().getHand().getCards());

        //锁定玩家视角
        frame.lockPlayerView();

        //更新游戏轮数
        gameData.setGameRound(gameData.getGameRound()+1);
        gameData.getHostPlayer().setGameRound(gameData.getGameRound());

        //检测游戏是否结束
        checkIfGameOver();

        /*
            TODO:
                机器人出牌
                ui渲染
         */
//        for (int i = 1;i<4;i++){
//            ArrayList<Card> result;
//            result = gameData.getRobotPlayers().get(i).playCards2(desk.getSetOnDesktop(),gameData.getGameRound());
//            if(!result.isEmpty()){
//                frame.CoverCard(result);
//            }
//        }
        updateCurrentID();
        RobotsPlay();

        frame.unlockPlayerView();
    }


    //Pass按钮点击事件的相应函数
    public void PlayerPass(){
        frame.lockPlayerView();
        updateCurrentID();
        RobotsPlay();
        frame.unlockPlayerView();
    }

    //开始游戏按钮的响应函数
    public void startGame(){

        dealCards();//发牌

        gameData.setCurrentID(findFirstPlayer());//设置当前出牌玩家

        //绘制游戏界面
        frame.paintPlayers(gameData.getPlayers());

//        //绘制玩家手牌
        frame.paintHand(gameData.getHostPlayer().getHand().getCards());
//
//        如果不是玩家出牌，锁定玩家手牌，让机器人先出牌,然后解锁玩家手牌
        if (gameData.getCurrentID()!=gameData.getHostID()) {
            frame.lockPlayerView();
            this.RobotsPlay();
            Log.d("Contro","RobotDone!");
            frame.unlockPlayerView();
        }
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
            if(hand.isEmpty()){
                baseScore.add(0);
                continue;
            }
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

    public void RobotsPlay(){
        while (gameData.getCurrentID()!= gameData.getHostID()){
            ArrayList<Card> cards = gameData.getRobotPlayers().get(gameData.getCurrentID()).playCards2(desk.getSetOnDesktop(), gameData.getGameRound());

            //机器人胜利
            if(gameData.getRobotPlayers().get(gameData.getCurrentID()).getHand().isEmpty()){
                gameData.setResultScore(calScore());
                overGame();
                return;
            }

            if(cards!=null){
                frame.CoverCard(cards);
                gameData.setGameRound(gameData.getGameRound()+1);
                gameData.getRobotPlayers().get(gameData.getCurrentID()).setGameRound(gameData.getGameRound());
            }
            gameData.setCurrentID((gameData.getCurrentID()+1)%4);
        }
    }

    public void checkIfGameOver() {
        //检查游戏是否结束
        if (gameData.getHostPlayer().getHand().isEmpty()) {
            gameData.setResultScore(calScore());
            overGame();
        }

    }
}
