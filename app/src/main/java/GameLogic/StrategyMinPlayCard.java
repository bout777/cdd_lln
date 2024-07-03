package GameLogic;

import java.util.ArrayList;

public class StrategyMinPlayCard implements Strategy{
    @java.lang.Override
    public ArrayList<Card> playCard(Hand hands, final CardsSet setOnDesktop) {
        //拿到手牌
        ArrayList<Card> theCards=hands.getCards();
        //手牌长度
        int n=theCards.size();

        //最后剩的牌
        if(n==1)return theCards;
        if(n==2){
            if(theCards.get(0).getIntRank()==theCards.get(1).getIntRank())
                return theCards;
        }
        if(n==3){
            if(theCards.get(0).getIntRank()==theCards.get(1).getIntRank()&&
                    theCards.get(0).getIntRank()==theCards.get(2).getIntRank())
                return theCards;
        }
        if(n==4){
            if(theCards.get(0).getIntRank()==theCards.get(1).getIntRank()&&
                    theCards.get(0).getIntRank()==theCards.get(2).getIntRank()&&
                        theCards.get(0).getIntRank()==theCards.get(3).getIntRank())
                return theCards;
        }

        //优先出顺子
        ArrayList<Card> result=new ArrayList<>();
        if(n>5){
            for (int i = 0; i < n - 4; i++) {
                result.clear();//重置
                result.add(theCards.get(i));
                int j = 1;//第一张

                // 检查从当前位置开始的连续牌是否能组成顺子
                for (int k = i + 1; k < n && j < 5; k++) {
                    if (theCards.get(k).getIntRank() - result.get(j - 1).getIntRank() == 1) {
                        result.add(theCards.get(k));
                        j++;
                    } else if (theCards.get(k).getIntRank() - result.get(j - 1).getIntRank() > 1) {
                        break; // 如果当前牌和上一个组成不了顺子那么停止当前检查
                    }
                }

                // 如果找到了一个合适的顺子就返回
                if (j == 5) {
                    return result;
                }
            }
        }
        result.clear();
        //尝试出对子
        for (int i=0;i<n-1;i++)
            //保证当前手牌的下一张的大小是和当前牌一样的
            if(theCards.get(i).rankCompareTo(theCards.get(i+1))==0){
                result.add(theCards.get(i));
                result.add(theCards.get(i+1));
                return result;
            }
        result.clear();
        //出单牌
        for(int i=0;i<n;i++)
            if(i+1<n&&(!theCards.get(i).equals(theCards.get(i+1)))){
                result.add(theCards.get(i));
                return result;
            }

        return result;
    }

}