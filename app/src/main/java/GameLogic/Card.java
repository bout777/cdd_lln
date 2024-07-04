package GameLogic;


import java.util.Locale;

public class Card implements Comparable<Card>{
    private final String suit;//花色
    private final String rank;//大小
    private CardChecker cardChecker;
    private String ImageName;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        this.ImageName = suit.toLowerCase();
        this.ImageName += rank.toLowerCase();
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public String getImageName(){return this.ImageName;};
    @Override
    //重写牌的比较大小
    public int compareTo(Card card) {
        this.cardChecker = CardChecker.getInstance();
        int a = cardChecker.rankToValue(this.getRank());
        int b = cardChecker.rankToValue(card.getRank());
        if(a==b){
            a = cardChecker.suitToValue(this.getSuit());
            b = cardChecker.suitToValue(card.getSuit());
            return Integer.compare(a, b);
        }
        return Integer.compare(a, b);
    }
}


