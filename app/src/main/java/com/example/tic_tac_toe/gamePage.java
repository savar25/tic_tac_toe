package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class gamePage extends AppCompatActivity {

    public static TextView plName;
    public static ImageView plChoice;
    public static Drawable cross,circle;
    public static Context mContext;
    public static View view;
    public static Vibrator vibrator;
    public static String p1n,p2n;
    public static int players;
    public static ActGame actGame;
    private static final String TAG = "gamePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        Intent intent=getIntent();
        p1n=intent.getStringExtra("p1n");
        p2n=intent.getStringExtra("p2n");
        players=intent.getIntExtra("cho",2);
        Log.d(TAG, "onCreate: "+players);

        actGame=findViewById(R.id.actGame);
        mContext=gamePage.this;
        view=new View(mContext);

        plChoice=findViewById(R.id.playChoice);
        plName=findViewById(R.id.playName);

        circle=getResources().getDrawable(R.drawable.id_circle,null);
        cross=getResources().getDrawable(R.drawable.id_cross,null);

        plName.setText(p1n);
        plName.setTextColor(Color.RED);
        plChoice.setImageDrawable(cross);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        actGame.setPlayers(players);
    }

    public static void setPLayer(int turn){
        switch(turn){
            case 1:
                if (Build.VERSION.SDK_INT > 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(100);
                }
                sweepIn(view);
                plChoice.setImageDrawable(cross);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plName.setText(p1n);
                        plName.setTextColor(Color.RED);
                    }
                },400);


                break;
            case 2:
                if (Build.VERSION.SDK_INT > 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(100);
                }
                sweepIn(view);
                plChoice.setImageDrawable(circle);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plName.setText(p2n);
                        plName.setTextColor(Color.BLUE);
                    }
                },400);


        }
    }

    public static void  sweepIn(View view){
        Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.sweep_in);
        gamePage.plChoice.setAnimation(animation);
    }

    public static void sweepOut(View view){
        Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.sweep_out);
        gamePage.plChoice.setAnimation(animation);
    }

    public static void declareWinner(final int turn){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(mContext,winPage.class);
                intent.putExtra("winner",turn);
                    intent.putExtra("p1n", p1n);
                    intent.putExtra("p2n", p2n);

                switch(turn){
                    case 1:
                        MediaPlayer.create(mContext,R.raw.cheer).start();
                        break;
                    case 2:
                        MediaPlayer.create(mContext,R.raw.fc).start();
                        break;
                    case 3:
                        MediaPlayer.create(mContext,R.raw.draw).start();
                        break;
                }
                mContext.startActivity(intent);
            }
        },800);

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent=new Intent(gamePage.this,choice.class);
        startActivity(intent);
    }


}

