package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class winPage extends AppCompatActivity {
    public  ArrayList<player> namelist=new ArrayList<>();

    String p1n,p2n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_page);

        final Intent intent=getIntent();
        Integer turn=intent.getIntExtra("winner",0);
        p1n=intent.getStringExtra("p1n");
        p2n=intent.getStringExtra("p2n");


        TextView win=findViewById(R.id.winner);
        Button restart=findViewById(R.id.rebtn);
        if(turn==3){
            win.setText("Its a Draw!!");
            namelist.add(new player(p2n,0));
            namelist.add(new player(p1n,0));
        }else {

            if(turn==1){
                win.setText(p2n+ "\nWon!!");
                win.setTextColor(Color.BLUE);
                namelist.add(new player(p2n,1));
                namelist.add(new player(p1n,0));

            }else {
                win.setText(p1n+ "\nWon!!");
                win.setTextColor(Color.RED);
                namelist.add(new player(p2n,0));
                namelist.add(new player(p1n,1));
            }
        }




        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(winPage.this,choice.class);
                startActivity(intent1);


            }
        });

        Button rand=findViewById(R.id.leader);
        rand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(winPage.this,leader.class);
                intent1.putExtra("p1n",namelist.get(1).name);
                intent1.putExtra("p2n",namelist.get(0).name);
                intent1.putExtra("p1s",namelist.get(1).score);
                intent1.putExtra("p2s",namelist.get(0).score);

                startActivity(intent1);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent=new Intent(winPage.this,choice.class);
        startActivity(intent);
    }


}
