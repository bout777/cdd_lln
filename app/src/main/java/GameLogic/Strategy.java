package GameLogic;

import java.util.ArrayList;
import java.lang.Override;
import java.util.Scanner;

public interface Strategy{
    ArrayList<Card> playCard(Hand hands,final CardsSet setOnDesktop);

    public static void main(String[] args) {
        //测试最小策略
        StrategyMinPlayCard strategyMinPlayCard=new StrategyMinPlayCard();

        //充数的上家牌
        CardsSet setOnDesktop=new CardsSet();

        Card card1=new Card("Diamond","K");
        Hand hands=new Hand();
        hands.addCard(card1);

        //结果
        ArrayList<Card> result=strategyMinPlayCard.playCard(hands,setOnDesktop);
        //输出打出的牌
        for (Card thecard:result)
            System.out.println(thecard.getIntRank());
    }
}

//两种机器人出牌策略
//最小出牌策略
class StrategyMinPlayCard implements Strategy{
    @java.lang.Override
    public ArrayList<Card> playCard(Hand hands,final CardsSet setOnDesktop) {
        //拿到手牌
        ArrayList<Card> theCards=hands.getCards();
        //手牌长度
        int n=theCards.size();

        //最后剩的牌
        if(n==1)return theCards;
        if(n==2){
            if(theCards.get(0).equals(theCards.get(1)))
                return theCards;
        }
        if(n==3){
            if(theCards.get(0).equals(theCards.get(1))&&
                    theCards.get(0).equals(theCards.get(2)))
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

        //对子
        for (int i=0;i<n-1;i++)
            //保证当前手牌的下一张的大小是和当前牌一样的
            if(theCards.get(i).rankCompareTo(theCards.get(i+1))==0){
                result.add(theCards.get(i));
                result.add(theCards.get(i+1));
                return result;
            }

        //单牌
        for(int i=0;i<n;i++)
            if(i+1<n&&(!theCards.get(i).equals(theCards.get(i+1)))){
                result.add(theCards.get(i));
                return result;
            }

        return result;
    }

}

//全力出牌
class StrategyTryBest implements Strategy{
    @Override
    public ArrayList<Card> playCard(Hand hands,final CardsSet setOnDesktop) {
        //当前牌的类型
        int a=setOnDesktop.getType();
        //拿到手牌
        ArrayList<Card> theCards=hands.getCards();
        //手牌长度
        int n=theCards.size();
        //关键牌
        Card key=setOnDesktop.getKey();

        switch (a)
        {
            case 1:{
                //比上家大的最小单牌查找
                for(int i=0;i<n;i++)
                    //找到比上家大的牌,
                    if(theCards.get(i).compareTo(key)>0){
                        ArrayList<Card> result = new ArrayList<>();
                        result.add(theCards.get(i));
                        return result;
                    }
                break;
            }
            case 2:{
                //比上家大的最小对子查找
                if(n<2)return null;

                for (int i=0;i<n-1;i++)
                    //保证当前手牌的下一张的大小是和当前牌一样的
                    if(theCards.get(i).compareTo(key)>0&&
                            theCards.get(i).rankCompareTo(theCards.get(i+1))==0){
                        ArrayList<Card> result = new ArrayList<>();
                        result.add(theCards.get(i));
                        result.add(theCards.get(i+1));
                        return result;
                    }
                //
                break;
            }
            case 3:{
                //比上家大的最小三牌查找
                if(n<3)return null;

                for (int i=0;i<n-2;i++)
                    //保证当前手牌的下一张和后一张的大小是和当前牌一样的
                    if(theCards.get(i).compareTo(key)>0&&
                            theCards.get(i).rankCompareTo(theCards.get(i+1))==0&&
                            theCards.get(i).rankCompareTo(theCards.get(i+2))==0){
                        ArrayList<Card> result = new ArrayList<>();
                        result.add(theCards.get(i));
                        result.add(theCards.get(i+1));
                        result.add(theCards.get(i+2));
                        return result;
                    }
                //
                break;
            }
            case 4:{
                if(n<4)return null;
                for (int i=0;i<n-3;i++)
                    //保证当前手牌是炸弹
                    if(theCards.get(i).compareTo(key)>0&&
                            theCards.get(i).rankCompareTo(theCards.get(i+1))==0&&
                            theCards.get(i).rankCompareTo(theCards.get(i+2))==0&&
                            theCards.get(i).rankCompareTo(theCards.get(i+3))==0){
                        ArrayList<Card> result = new ArrayList<>();
                        result.add(theCards.get(i));
                        result.add(theCards.get(i+1));
                        result.add(theCards.get(i+2));
                        result.add(theCards.get(i+3));
                        return result;
                    }
                break;
            }
            case 5: {
                // 查找杂顺
                ArrayList<Card> result = findShun(theCards,setOnDesktop);

                if (result==null){
                    //如果没有就查找有没有同花五 三带二，四带一
                    result=findTongHuaWu(theCards,setOnDesktop);
                    if(result==null)
                        result=findHuLu(theCards,setOnDesktop);
                    if (result==null)
                        result=findTieZhi(theCards,setOnDesktop);
                }
                if(result!=null)return result;
                break;
            }
            case 6: {
                // 查找同花五
                ArrayList<Card> result = findTongHuaWu(theCards,setOnDesktop);

                // 如果没有找到同花五，继续处理其他牌型
                // 三带二，四带一，同花顺
                if(result==null)
                    result=findHuLu(theCards,setOnDesktop);
                if (result==null)
                    result=findTieZhi(theCards,setOnDesktop);
                if(result==null)
                    result=findTongHuaShun(theCards,setOnDesktop);

                if(result!=null)return result;
                break;
            }
            case 7:{
                //三带二
                ArrayList<Card> result=findHuLu(theCards,setOnDesktop);

                //四带一和同花五
                if (result==null)
                    result=findTieZhi(theCards,setOnDesktop);
                if(result==null)
                    result=findTongHuaShun(theCards,setOnDesktop);

                if(result!=null)return result;
                break;
            }
            case 8:{
                //四带一
                ArrayList<Card> result=findTieZhi(theCards,setOnDesktop);

                //同花顺
                if(result==null)
                    result=findTongHuaShun(theCards,setOnDesktop);

                if(result!=null)return result;
                break;
            }
            case 9:{
                //同花顺
                ArrayList<Card> result=findTongHuaShun(theCards,setOnDesktop);

                if(result!=null)return result;
                break;
            }
            case 10:{
                //报错
            }
        }
        return null;
    }

    public ArrayList<Card> findShun(ArrayList<Card> theCards,final CardsSet setOnDesktop){
        //手牌长度
        int n=theCards.size();
        //关键牌
        Card key=setOnDesktop.getKey();

        // 查找杂顺
        if (n < 5) return null;
        ArrayList<Card> result = new ArrayList<>(); // 用来暂存

        for (int i = 0; i < n - 4; i++) {
            if (theCards.get(i).compareTo(key) > 0) {
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
        return null;
    }

    public ArrayList<Card> findTongHuaWu(ArrayList<Card> theCards,final CardsSet setOnDesktop){
        // 查找同花五

        //手牌长度
        int n=theCards.size();
        //关键牌
        Card key=setOnDesktop.getKey();

        if (n < 5) return null;
        ArrayList<Card> result = new ArrayList<>(); // 用来暂存

        for (int i = 0; i < n; i++) {
            if (theCards.get(i).compareTo(key) > 0) {
                result.clear(); // 重置卡牌集合
                result.add(theCards.get(i));
                int j = 1; // 记录已找到的同花牌数量

                for (int k = 0; k < n; k++) {
                    if (i != k && theCards.get(k).getIntSuit() == theCards.get(i).getIntSuit()) {
                        result.add(theCards.get(k));
                        j++;
                        if (j == 5) return result; // 找到同花五
                    }
                }
            }
        }
        //没找到
        return null;
    }

    public ArrayList<Card> findHuLu(ArrayList<Card> theCards,final CardsSet setOnDesktop){
        //手牌长度
        int n=theCards.size();
        //关键牌
        Card key=setOnDesktop.getKey();

        if(n<5)return null;
        //比上家大的最小三牌查找
        ArrayList<Card> result=new ArrayList<>();//用来暂存

        boolean flag=false;
        int tag=0;//用来纪录三个的大小

        for (int i=0;i<n-2;i++)
            //保证当前手牌的下一张和后一张的大小是和当前牌一样的
            if(theCards.get(i).compareTo(key)>0&&
                    theCards.get(i).rankCompareTo(theCards.get(i+1))==0&&
                    theCards.get(i).rankCompareTo(theCards.get(i+2))==0){
                result.add(theCards.get(i));
                result.add(theCards.get(i+1));
                result.add(theCards.get(i+2));
                tag=theCards.get(i).getIntRank();//标记
                flag=true;
                break;
            }
        //没有三个
        if(!flag){
            return null;
        }
        //查找对子
        for (int i=0;i<n-1;i++)
            //保证当前手牌的下一张的大小是和当前牌一样的并且不是三个里面的数
            if(tag!=theCards.get(i).getIntRank() && theCards.get(i).rankCompareTo(theCards.get(i+1))==0){
                result.add(theCards.get(i));
                result.add(theCards.get(i+1));
                return result;
            }

        return null;
    }

    public ArrayList<Card> findTieZhi(ArrayList<Card> theCards,final CardsSet setOnDesktop){
        //手牌长度
        int n=theCards.size();
        //关键牌
        Card key=setOnDesktop.getKey();

        //四带一
        if(n<5)return null;

        ArrayList<Card> result=new ArrayList<>();//用来暂存
        boolean flag=false;
        int tag=0;//用来纪录炸弹的大小
        for (int i=0;i<n-3;i++)
            //保证当前手牌是炸弹
            if(theCards.get(i).compareTo(key)>0&&
                    theCards.get(i).rankCompareTo(theCards.get(i+1))==0&&
                    theCards.get(i).rankCompareTo(theCards.get(i+2))==0&&
                    theCards.get(i).rankCompareTo(theCards.get(i+3))==0){
                result.add(theCards.get(i));
                result.add(theCards.get(i+1));
                result.add(theCards.get(i+2));
                result.add(theCards.get(i+3));
                tag=theCards.get(i).getIntRank();//标记
                flag=true;
                break;
            }
        //没有炸弹
        if(!flag){
            return null;
        }
        //查找单张
        for (int i=0;i<n-1;i++)
            //保证当前手牌的下一张的大小是和当前牌一样的并且不是炸弹里面的数
            if(tag!=theCards.get(i).getIntRank()){
                result.add(theCards.get(i));
                return result;
            }

        return null;
    }

    public ArrayList<Card> findTongHuaShun(ArrayList<Card> theCards,final CardsSet setOnDesktop){
        //手牌长度
        int n=theCards.size();
        //关键牌
        Card key=setOnDesktop.getKey();

        //同花顺
        if (n < 5) return null;
        ArrayList<Card> result = new ArrayList<>(); // 用来暂存

        for (int i = 0; i < n - 4; i++) {
            if (theCards.get(i).compareTo(key) > 0) {
                result.clear();//重置
                result.add(theCards.get(i));
                int j = 1;//第一张

                // 检查从当前位置开始的连续牌是否能组成同花顺子
                for (int k = i + 1; k < n && j < 5; k++) {
                    //检查点数和花色
                    if (theCards.get(k).getIntRank() - result.get(j - 1).getIntRank() == 1&&
                            theCards.get(k).getIntSuit() == theCards.get(j-1).getIntSuit()) {
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

        return null;
    }

}