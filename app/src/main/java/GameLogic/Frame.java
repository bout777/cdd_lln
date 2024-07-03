package GameLogic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;


public class Frame extends AppCompatActivity{
    private final GameController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public Frame(GameController c){
        this.controller = c;
    }

    public void paintHand(ArrayList<Card> handCards){

    }

    public void paintResult(ArrayList<Integer> result){

    }

    public void CoverCard(ArrayList<Card> setOnDesktop){
        controller.updateSetOnDesktop(setOnDesktop);
        //TODO:更新ui
    }

    public void paintPlayers(ArrayList<Player> players){

    }

    public void lockPlayerView(){

    }

    public void unlockPlayerView(){

    }

}
