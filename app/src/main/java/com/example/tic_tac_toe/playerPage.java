package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class playerPage extends AppCompatActivity {
    int players=2;
    String p1Name,p2Name="Computer";
    int turn=1;
    EditText entP;
    TextView pset;
    Button nxtbtn;
    private static final String TAG = "playerPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_page);


        Intent intent=getIntent();
        players=intent.getIntExtra("players",2);
        Log.d(TAG, "onCreate: "+players);

        pset=findViewById(R.id.pset);
        entP=findViewById(R.id.entName);
        nxtbtn=(Button)findViewById(R.id.setbtn);


        if(players==2) {
            pset.setText("Player 1 Name(X):");
            pset.setTextColor(Color.RED);
            entP.setTextColor(Color.RED);
        }else{
            pset.setText("Player Name(X): ");
            pset.setTextColor(Color.BLUE);
            entP.setTextColor(Color.BLUE);
        }

        if(players==2) {
            nxtbtn.setText("NEXT");
        }else if(players==1){
            nxtbtn.setText("Start Game");
        }


        final View mview=new View(playerPage.this) ;
        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (players == 2 && turn != players) {
                    if(entP.getText().toString().isEmpty()){
                        Toast.makeText(playerPage.this,"Enter Valid Name",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        p1Name = entP.getText().toString();
                        p1Name=changeLetter(p1Name);
                        sweepOut(mview);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sweepIn(mview);
                                entP.setText("");
                                pset.setText("Player 2 Name(O):");
                                pset.setTextColor(Color.BLUE);
                                entP.setTextColor(Color.BLUE);
                                nxtbtn.setText("Start Game");
                            }
                        }, 400);

                        turn++;
                    }


                }else if (turn == players) {
                    if (players == 2) {
                        if(entP.getText().toString().isEmpty()){
                            Toast.makeText(playerPage.this,"Enter Valid Name",Toast.LENGTH_SHORT).show();
                        }else {
                            p2Name = entP.getText().toString();
                            p2Name=changeLetter(p2Name);
                            Intent intent = new Intent(playerPage.this, gamePage.class);
                            intent.putExtra("p1n", p1Name);
                            intent.putExtra("p2n", p2Name);
                            intent.putExtra("cho", players);
                            startActivity(intent);
                        }
                    } else if (players == 1) {
                        if (entP.getText().toString().isEmpty()) {
                            Toast.makeText(playerPage.this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
                        } else {
                            p1Name = entP.getText().toString();
                            p1Name=changeLetter(p1Name);
                            p2Name = "Computer";


                            Intent intent = new Intent(playerPage.this, gamePage.class);
                            intent.putExtra("p1n", p1Name);
                            intent.putExtra("p2n", p2Name);
                            intent.putExtra("cho", players);
                            startActivity(intent);
                        }
                    }
                }
            }
        });


    }

    public  void  sweepIn(View view){
        Animation animation= AnimationUtils.loadAnimation(playerPage.this,R.anim.sweep_in);
        entP.setAnimation(animation);
        pset.setAnimation(animation);
    }

    public void sweepOut(View view){
        Animation animation= AnimationUtils.loadAnimation(playerPage.this,R.anim.sweep_out);
        entP.setAnimation(animation);
        pset.setAnimation(animation);
    }

    public String changeLetter(String s){
        String temp=s.substring(0,1);
        temp=temp.toUpperCase() ;
        s=temp+s.substring(1);
        return s;
    }


}
