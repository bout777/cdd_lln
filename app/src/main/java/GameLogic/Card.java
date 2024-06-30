package GameLogic;



public class Card implements Comparable<Card>{
    private String suit;//花色
    private String rank;//大小

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


    @Override
    public int compareTo(Card card) {
        int a = CardChecker.rankToValue(this.getRank());
        int b = CardChecker.rankToValue(card.getRank());
        if(a==b){
            a = CardChecker.suitToValue(this.getSuit());
            b = CardChecker.suitToValue(card.getSuit());
            return a-b;
        }
        return a-b;
    }
}


