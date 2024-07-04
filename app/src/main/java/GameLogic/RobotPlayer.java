package GameLogic;

import java.util.ArrayList;

public class RobotPlayer extends Player implements Strategy{

    private final CardChecker cardChecker = CardChecker.getInstance();
    public RobotPlayer(String name) {
        super(name);
    }

    @Override
    public ArrayList<Card> playCards1(CardsSet setOnDesktop,int Round) {

        //当前牌的类型
        int Type=setOnDesktop.getType();
        //拿到手牌
        ArrayList<Card> theCards= new ArrayList<Card>(this.getHand().getCards());
        //手牌长度
        int n=theCards.size();
        //关键牌
        Card key=setOnDesktop.getKey();

        switch (Type){
            case 1: {
                //比上家大的最小单牌查找
                for (int i = 0; i < n; i++)
                    //找到比上家大的牌,
                    if (theCards.get(i).compareTo(key) > 0) {
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
                    if(theCards.get(i+1).compareTo(key)>0&&
                            cardChecker.rankToValue(theCards.get(i).getRank())==cardChecker.rankToValue(theCards.get(i+1).getRank())){
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
                    if(theCards.get(i+2).compareTo(key)>0&&
                            cardChecker.rankToValue(theCards.get(i).getRank())==cardChecker.rankToValue(theCards.get(i+1).getRank())&&
                            cardChecker.rankToValue(theCards.get(i+1).getRank())==cardChecker.rankToValue(theCards.get(i+2).getRank())){
                        ArrayList<Card> result = new ArrayList<>();
                        result.add(theCards.get(i));
                        result.add(theCards.get(i+1));
                        result.add(theCards.get(i+2));
                        return result;
                    }
                //
                break;
            }
//            case 4:{
//                if(n<4)return null;
//                for (int i=0;i<n-3;i++)
//                    //保证当前手牌是炸弹
//                    if(theCards.get(i).compareTo(key)>0&&
//                            theCards.get(i).rankCompareTo(theCards.get(i+1))==0&&
//                            theCards.get(i).rankCompareTo(theCards.get(i+2))==0&&
//                            theCards.get(i).rankCompareTo(theCards.get(i+3))==0){
//                        ArrayList<Card> result = new ArrayList<>();
//                        result.add(theCards.get(i));
//                        result.add(theCards.get(i+1));
//                        result.add(theCards.get(i+2));
//                        result.add(theCards.get(i+3));
//                        return result;
//                    }
//                break;
//            }
        }
        return null;
    }

    @Override
    public ArrayList<Card> playCards2(CardsSet setOnDesktop,int Round) {
        if(this.getGameRound()==Round){
            ArrayList<Card> result = new ArrayList<>();
            Card card = this.getHand().getCards().remove(0);
            result.add(card);
            return result;
        }
        int Type=setOnDesktop.getType();
        if(Type==CardsType.Single){
            ArrayList<Card> result = new ArrayList<>();
            ArrayList<Card> cards = this.getHand().getCards();
            for (Card card:cards){
                if(card.compareTo(setOnDesktop.getKey())>0){
                    result.add(card);
                    return result;
                }
            }
        }
        return null;
    }


}
