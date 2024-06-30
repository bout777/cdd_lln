package GameLogic;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class CardChecker {
    public void SetTypeAndKey(CardsSet set){

    }

    public static boolean isSingle(CardsSet set) {
        return set.size() == 1;
    }

    public static boolean isPair(CardsSet set) {
        return set.size() == 2 && set.get(0).getRank().equals(set.get(1).getRank());
    }

    public static boolean isTriplet(CardsSet set) {
        return set.size() == 3 && set.get(0).getRank().equals(set.get(1).getRank()) &&
                set.get(1).getRank().equals(set.get(2).getRank());
    }

    public static boolean isTongHua(CardsSet set) {
        if (set.size() < 5) return false;
        String curSuit = set.get(0).getSuit();

        for(int i = 1;i< set.size();i++)
            if (!set.get(i).getSuit().equals(curSuit))
                return false;
        return true;
    }

    public static boolean isShun(CardsSet set) {
        if (set.size() < 5) return false;
        List<Integer> values = new ArrayList<>();
        for (int i = 0;i< set.size();i++) {
            values.add(rankToValue(set.get(i).getRank()));
        }
        Collections.sort(values);
        if(values.get(0)==1&&values.get(values.size()-1)==13){ //处理A在顺子中的复用
            values.remove(0);
            values.add(14);
        }
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) != values.get(i - 1) + 1) return false;
        }
        return true;
    }

    public static boolean isTieZhi(CardsSet cards){
        if (cards.size() < 5) return false;
        List<Integer> values = new ArrayList<>();
        for (Card card : cards) {
            values.add(rankToValue(card.getRank()));
        }
        Collections.sort(values);
        int rank1 = values.get(0);
        int rank2 = values.get(values.size()-1);
        int conut1 = 0;
        int count2 = 0;
        for (int val:values){
            if(val==rank1)
                conut1++;
            else if (val==rank2)
                count2++;
        }
        if(conut1*count2==4)
            return true;
        return false;
    }

    public static boolean isHuLu(List<Card> cards){

    }



    public static int rankToValue(String rank) {
        switch (rank) {
            case "K": return 13;
            case "Q": return 12;
            case "J": return 11;
            case "10": return 10;
            case "9": return 9;
            case "8": return 8;
            case "7": return 7;
            case "6": return 6;
            case "5": return 5;
            case "4": return 4;
            case "3": return 3;
            case "2": return 2;
            case "A": return 1;
            default:  return 0;
        }
    }

    public static int suitToValue(String suit) {
        switch (suit) {
            case "Diamond":
                return 1;
            case "Club":
                return 2;
            case "Heart":
                return 3;
            case "Spade":
                return 4;
            default:return 0;
        }
    }

}

