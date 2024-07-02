package GameLogic;



public interface CardsType {
    public final
    int Single=1; //单张
    int Pair =2; //对子
    int Triplet =3; //三张
    int Bomb = 4;
    int Shun = 5;
    int TongHua=6;//5 张花色相同的牌型
    int HuLu=7;//3 张数字一样的牌再加一个对子
    int TieZhi=8;//4 张数字一样的牌再加任意一张牌
    int TongHuaShun=9;
    int error=10;

}
