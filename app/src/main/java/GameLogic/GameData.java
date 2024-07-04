package GameLogic;

import java.util.ArrayList;

public class GameData {
    private ArrayList<Player> players = new ArrayList<Player>(); //桌子上的四个玩家
    private ArrayList<RobotPlayer> robotPlayers = new ArrayList<RobotPlayer>();
    private ArrayList<Integer> ResultScore;
    private Player HostPlayer;
    private final Deck deck = new Deck(); //发给玩家的牌库
    private int currentID = 0; //当前操作的人的id
    private final int hostID = 0;
    private int GameRound = 0;


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<RobotPlayer> getRobotPlayers() {
        return robotPlayers;
    }

    public Player getHostPlayer() {
        return HostPlayer;
    }

    public int getHostID() {
        return hostID;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getCurrentID() {
        return currentID;
    }

    public ArrayList<Integer> getResultScore(){
        return ResultScore;
    }

    public int getGameRound() {
        return GameRound;
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }

    public void setHostPlayer(Player hostPlayer) {
        HostPlayer = hostPlayer;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setRobotPlayers(ArrayList<RobotPlayer> robotPlayers) {
        this.robotPlayers = robotPlayers;
    }

    public void setResultScore(ArrayList<Integer> resultScore) {
        ResultScore = resultScore;
    }

    public void setGameRound(int gameRound) {
        GameRound = gameRound;
    }
}
