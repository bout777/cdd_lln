package GameLogic;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CardChecker {

    //单实例
    private static volatile CardChecker instance;
    // 私有构造函数，防止外部创建实例
    private CardChecker() {}


    //返回唯一单实例，若无则创建，若有直接返回
    public static CardChecker getInstance() {
        if (instance == null) {
            synchronized (CardChecker.class) {
                if (instance == null) {
                    instance = new CardChecker();
                }
            }
        }
        return instance;
    }

    //设置牌组的类型
    public void SetTypeAndKey(CardsSet set){
        if(isSingle(set))
            set.setType(CardsType.Single);
        else if(isPair(set))
            set.setType(CardsType.Pair);
        else if(isTriplet(set))
            set.setType(CardsType.Triplet);
        else if(isBomb(set))
            set.setType(CardsType.Bomb);
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
            else
                set.setType(CardsType.error);
        }
    }

    //下面都是牌组的类型判断
    public boolean isSingle(CardsSet set) {
        if(set.size() == 1){
            set.setKey(set.getCard(0));
            return true;
        }
        return false;
    }

    public boolean isPair(CardsSet set) {
        if(set.size() == 2 && set.getCard(0).getRank().equals(set.getCard(1).getRank())){
            set.setKey(set.getCard(1));
            return true;
        }
        return false;
    }

    public boolean isTriplet(CardsSet set) {
        if(set.size() == 3 && set.getCard(0).getRank().equals(set.getCard(1).getRank()) &&
                set.getCard(1).getRank().equals(set.getCard(2).getRank())){
            set.setKey(set.getCard(2));
            return true;
        }
        return false;
    }

    public boolean isBomb(CardsSet set){
        if(set.size() == 4 && set.getCard(0).getRank().equals(set.getCard(1).getRank()) &&
                set.getCard(1).getRank().equals(set.getCard(2).getRank())&&
                set.getCard(2).getRank().equals(set.getCard(3).getRank())){
            set.setKey(set.getCard(3));
            return true;
        }
        return false;
    }

    public boolean isTongHua(CardsSet set) {
        if (set.size() < 5) return false;
        String curSuit = set.getCard(0).getSuit();

        for(int i = 1;i< set.size();i++)
            if (!set.getCard(i).getSuit().equals(curSuit))
                return false;
        set.setKey(set.getCard(set.size()-1)); //设置key为最大的牌
        return true;
    }

    public boolean isShun(CardsSet set) {
        if (set.size() < 5) return false;

        List<Integer> values = new ArrayList<>();
        for (int i = 0;i< set.size();i++) {

            //将牌面大小转化成整数
            int Value = rankToValue(set.getCard(i).getRank());

            //2在顺子中的牌面比较小，重新设置
            if(Value==15)
                Value = 2;

            //添加
            values.add(Value);
        }

        //排序
        Collections.sort(values);

        //处理A在顺子中的复用，有2和A同时出现时，A排在2的前面，否则A排在K的后面
        if(values.get(0)==2&&values.get(values.size()-1)==14){
            values.remove(values.size()-1);
            values.add(0,1);
        }

        //判断是否为顺子的逻辑
        for (int i = 1; i < values.size(); i++)
            if (values.get(i) != values.get(i - 1) + 1)
                return false;

        //循环成功跳出，判断卡组是否需要重新排序，两种情况，34562，345A2
        if(set.getCard(3).getRank().equals("6")&&set.getCard(4).getRank().equals("2")){
            Card cardOf2 = set.getCards().remove(4);
            set.getCards().add(0,cardOf2);
        }else if(set.getCard(4).getRank().equals("2")){
            Card cardOf2 = set.getCards().remove(4);
            Card cardOfA = set.getCards().remove(3);
            set.getCards().add(0,cardOf2);
            set.getCards().add(0,cardOfA);
        }

        //设置key为最末尾的牌
        set.setKey(set.getCard(set.size()-1));
        return true;
    }

    public boolean isTieZhi(final CardsSet set){
        if (set.size() != 5) return false;
        List<Integer> values = new ArrayList<>();
        for (int i = 0;i< set.size();i++) {
            values.add(rankToValue(set.getCard(i).getRank()));
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
            set.setKey(set.getCard(2));//设置key为第三张的牌
            return true;
        }
        return false;
    }

    public boolean isHuLu(final CardsSet set){
        if (set.size() != 5) return false;
        List<Integer> values = new ArrayList<>();
        for (int i = 0;i< set.size();i++) {
            values.add(rankToValue(set.getCard(i).getRank()));
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
            set.setKey(set.getCard(2));//设置key为第三张的牌
            return true;
        }
        return false;
    }


    public int rankToValue(String rank) {
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
            case "A": return 14;
            default:  return 0;
        }
    }

    public int suitToValue(String suit) {
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

