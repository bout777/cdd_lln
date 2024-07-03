package GameLogic;



public class Card implements Comparable<Card>{
    private final String suit;//花色
    private final String rank;//大小
    private CardChecker cardChecker;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    //返回牌的大小
    public int getIntRank(){
        this.cardChecker = CardChecker.getInstance();
        return cardChecker.rankToValue(rank);
    }

    public int getIntSuit(){
        this.cardChecker = CardChecker.getInstance();
        return cardChecker.suitToValue(suit);
    }

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

    //比较牌的大小是否一样
    public int rankCompareTo(Card card) {
        this.cardChecker = CardChecker.getInstance();
        int a = cardChecker.rankToValue(this.getRank());
        int b = cardChecker.rankToValue(card.getRank());
        return Integer.compare(a, b);
    }

}


