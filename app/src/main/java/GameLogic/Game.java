package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Deck deck;
    private List<Card> curPile;
    private Frame;

    public Game() {
        players = new ArrayList<>();
        deck = new Deck();
        curPile = new ArrayList<>();
    }

    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void dealCards() { //分发卡牌
        int numPlayers = players.size();
        while (deck.size() > 0) {
            for (Player player : players) {
                if (deck.size() > 0) {
                    player.addCard(deck.deal());
                }
            }
        }
    }
}
