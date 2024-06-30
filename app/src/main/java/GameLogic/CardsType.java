package GameLogic;



public interface CardsType {
    public
    int Single=1; //单张
    int Pair =2; //对子
    int Triplet =3; //三张
    int Shun=4;
    int Bomb = 5;
    int TongHuaShun=6;
    int TongHua=7;//5 张花色相同的牌型
    int HuLu=8;//3 张数字一样的牌再加一个对子
    int TieZhi=9;//4 张数字一样的牌再加任意一张牌
    int error=10;

    int directionHorizontal=0;
    int directionVertical=1;
}
