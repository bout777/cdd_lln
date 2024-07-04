package GameLogic;

import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import androidx.core.graphics.Insets;
import java.util.ArrayList;



public class Frame extends AppCompatActivity{
//    private ArrayList
    private ArrayList<Integer> HandIndex = new ArrayList<Integer>();
    private GameController controller;
    private LinearLayout HandView;
    private LinearLayout DesktopView;
    private LinearLayout ButtonView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HandView = findViewById(R.id.HandView);
        HandView.setOrientation(LinearLayout.HORIZONTAL);

        this.controller = new GameController(this);
        controller.startGame();

    }


    public Frame(){        this.controller = new GameController(this);};

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void paintHand(ArrayList<Card> handCards){
        HandView.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                80,264
        );

        for (int i = 0;i<handCards.size()-1;i++){
           ImageView cardImage = new ImageView(this);

           int resID = getResources().getIdentifier(handCards.get(i).getImageName(),"drawable",getPackageName());
           cardImage.setImageResource(resID);
           cardImage.setPadding(0,0,-112,0);
           cardImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            cardImage.setLayoutParams(layoutParams);
            int finalI = i;
            cardImage.setOnClickListener(view -> HandIndex.add(finalI));
           HandView.addView(cardImage);
        }


        ImageView cardImage = new ImageView(this);
        layoutParams = new LinearLayout.LayoutParams(
                192,264
        );
        int resID = getResources().getIdentifier(handCards.get(handCards.size()-1).getImageName(),"drawable",getPackageName());
        cardImage.setImageResource(resID);

        cardImage.setPadding(0,0,0,0);
        cardImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        cardImage.setLayoutParams(layoutParams);
        cardImage.setOnClickListener(view -> HandIndex.add(handCards.size()-1));

        HandView.addView(cardImage);

        Button playbtn = findViewById(R.id.play);
        playbtn.setOnClickListener(view -> {
            ArrayList<Card> cardsToPlay = new ArrayList<Card>();
            for (Integer idx:HandIndex){
                cardsToPlay.add(handCards.get(idx));
            }
            controller.PlayCardSet(cardsToPlay);
            HandIndex.clear();
        });

        Button passbtn = findViewById(R.id.pass);
        passbtn.setOnClickListener(view -> {
            controller.PlayerPass();
        });

    }

    public void paintResult(ArrayList<Integer> result){
        TextView textView = findViewById(R.id.textView);
        textView.setText(result.toString());
        textView.setVisibility(View.VISIBLE);
        HandView.setVisibility(View.GONE);
        Button passbtn = findViewById(R.id.pass);
        Button playbtn = findViewById(R.id.play);
        playbtn.setVisibility(View.GONE);
        passbtn.setVisibility(View.GONE);
        DesktopView.setVisibility(View.GONE);
    }

    public void CoverCard(ArrayList<Card> setOnDesktop){
        //控制器更新数据
        controller.updateSetOnDesktop(setOnDesktop);

        //获取桌面的引用
        DesktopView = findViewById(R.id.DesktopView);

        //清除上一次出牌
        DesktopView.removeAllViews();

        //设置size
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                60,264
        );
        LayoutInflater inflater = LayoutInflater.from(this);


        for (int i = 0;i<setOnDesktop.size()-1;i++) {

            ImageView cardImage = new ImageView(this);

            int resID = getResources().getIdentifier(setOnDesktop.get(i).getImageName(), "drawable", getPackageName());
            cardImage.setImageResource(resID);

            cardImage.setPadding(0, 0, -140, 0);
            cardImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            cardImage.setLayoutParams(layoutParams);

            DesktopView.addView(cardImage);
        }

        ImageView cardImage = new ImageView(this);
        layoutParams = new LinearLayout.LayoutParams(
                192,264
        );
        int resID = getResources().getIdentifier(setOnDesktop.get(setOnDesktop.size()-1).getImageName(),"drawable",getPackageName());
        cardImage.setImageResource(resID);

        cardImage.setPadding(0,0,0,0);
        cardImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        cardImage.setLayoutParams(layoutParams);

        DesktopView.addView(cardImage);
    }

    public void paintPlayers(ArrayList<Player> players){

    }

    public void lockPlayerView(){
        for (int i = 0;i<HandView.getChildCount();i++){
            View card = HandView.getChildAt(i);
            card.setEnabled(false);
        }
        Button playbtn = findViewById(R.id.play);
        playbtn.setEnabled(false);
        Button passbtn = findViewById(R.id.pass);
        passbtn.setEnabled(false);
    }

    public void unlockPlayerView(){
        for (int i = 0;i<HandView.getChildCount();i++){
            View card = HandView.getChildAt(i);
            card.setEnabled(true);
        }

        Button playbtn = findViewById(R.id.play);
        playbtn.setEnabled(true);
        Button passbtn = findViewById(R.id.pass);
        passbtn.setEnabled(true);
    }

}
