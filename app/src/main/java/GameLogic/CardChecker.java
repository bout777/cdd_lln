package GameLogic;

import java.util.List;
import java.util.ArrayList;

public class CardChecker {

    public int CompareTo(){ //待写
        return 0;
    }
    public void SetType(CardsSet set){
        if(isSingle(set))
            set.setType(CardsType.Single);
        else if(isPair(set))
            set.setType(CardsType.Pair);
        else if(isTriplet(set))
            set.setType(CardsType.Triplet);
        else if(isHuLu(set))
            set.setType(CardsType.HuLu);
        else if(isTieZhi(set))
            set.setType(CardsType.TieZhi);
        else {
            //同花和顺子的判断
            boolean isShun = isShun(set);
            boolean isTongHua = isTongHua(set);
            if(isShun&&isTongHua)
                set.setType(CardsType.TongHuaShun);
            else if (isShun)
                set.setType(CardsType.Shun);
            else if(isTongHua)
                set.setType(CardsType.TongHua);
        }
    }

    public static boolean isSingle(CardsSet set) {
        if(set.size() == 1){
            set.setKey(set.get(0));
            return true;
        }
        return false;
    }

    public static boolean isPair(CardsSet set) {
        if(set.size() == 2 && set.get(0).getRank().equals(set.get(1).getRank())){
            set.setKey(set.get(1));
            return true;
        }
        return false;
    }

    public static boolean isTriplet(CardsSet set) {
        if(set.size() == 3 && set.get(0).getRank().equals(set.get(1).getRank()) &&
                set.get(1).getRank().equals(set.get(2).getRank())){
            set.setKey(set.get(2));
            return true;
        }
        return false;
    }

    public static boolean isBomb(CardsSet set){
        if(set.size() == 4 && set.get(0).getRank().equals(set.get(1).getRank()) &&
                set.get(1).getRank().equals(set.get(2).getRank())&&
                set.get(2).getRank().equals(set.get(3).getRank())){
            set.setKey(set.get(3));
            return true;
        }
        return false;
    }

    public static boolean isTongHua(CardsSet set) {
        if (set.size() < 5) return false;
        String curSuit = set.get(0).getSuit();

        for(int i = 1;i< set.size();i++)
            if (!set.get(i).getSuit().equals(curSuit))
                return false;
        set.setKey(set.get(set.size()-1)); //设置key为最大的牌
        return true;
    }

    public static boolean isShun(CardsSet set) {
        if (set.size() < 5) return false;
        List<Integer> values = new ArrayList<>();
        for (int i = 0;i< set.size();i++) {
            values.add(rankToValue(set.get(i).getRank()));
        }
        if(values.get(0)==1&&values.get(values.size()-1)==13){ //处理A在顺子中的复用
            values.remove(0);
            values.add(14);
        }
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) != values.get(i - 1) + 1) return false;
        }
        set.setKey(set.get(set.size()-1)); //设置key为最大的牌
        return true;
    }

    public static boolean isTieZhi(CardsSet set){
        if (set.size() != 5) return false;
        List<Integer> values = new ArrayList<>();
        for (int i = 0;i< set.size();i++) {
            values.add(rankToValue(set.get(i).getRank()));
        }
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
        if(conut1*count2==4){
            set.setKey(set.get(2));//设置key为第三张的牌
            return true;
        }
        return false;
    }

    public static boolean isHuLu(CardsSet set){
        if (set.size() != 5) return false;
        List<Integer> values = new ArrayList<>();
        for (int i = 0;i< set.size();i++) {
            values.add(rankToValue(set.get(i).getRank()));
        }
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
        if(conut1*count2 == 6){
            set.setKey(set.get(2));//设置key为第三张的牌
            return true;
        }
        return false;
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

