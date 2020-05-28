package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME = 2000;
    ImageView title_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        title_image=findViewById(R.id.tiImage);
        swirl(new View(MainActivity.this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,choice.class);
                startActivity(intent);
            }
        },SPLASH_TIME);

    }

    public void swirl(View view){
        title_image.setImageDrawable(getResources().getDrawable(R.drawable.cr));
        Animation swirl= AnimationUtils.loadAnimation(MainActivity.this,R.anim.swirl);
        title_image.setAnimation(swirl);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation1=AnimationUtils.loadAnimation(MainActivity.this,R.anim.outs);
                title_image.setImageResource(R.drawable.cir);
                title_image.setAnimation(animation1);
            }
        },600);

    }
    @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent=new Intent(MainActivity.this,choice.class);
        startActivity(intent);
    }
}
