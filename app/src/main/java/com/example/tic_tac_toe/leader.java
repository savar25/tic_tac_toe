package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class leader extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Integer num=0;
    Integer sc1;
    Integer sc2;
    private static final String TAG = "leader";
   public ArrayList<player> name=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);
        int p1com=-1,p2com=-1;
        int p1val=0,p2val=0;

        Intent intent=getIntent();
        String p1n=intent.getStringExtra("p1n");
        Log.d(TAG, "onCreate: "+p1n);
        String p2n=intent.getStringExtra("p2n");
        Log.d(TAG, "onCreate: "+p2n);
        sc1=intent.getIntExtra("p1s",0);
        sc2=intent.getIntExtra("p2s",0);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        num=sharedPreferences.getInt("num",0);
        for(int i=0;i<num;i++){
            if(sharedPreferences.getString("name"+ i,"").equals(p1n)){
                p1com=i;
                p1val=sharedPreferences.getInt("score"+i,0);
            }
            if(sharedPreferences.getString("name"+i,"").equals(p2n)){
                p2com=i;
                p2val=sharedPreferences.getInt("score"+i,0);
            }
        }
        editor=sharedPreferences.edit();


        if(p1com==-1 && p2com==-1) {
            editor.putString("name" + num, p1n);
            editor.putInt("score" + num, sc1);
            num++;
            editor.putString("name" + (num), p2n);
            editor.putInt("score" + (num), sc2);
            num++;

        }else if(p1com==-1 || p2com==-1) {
            if(p1com==-1){
                editor.putString("name" + num, p1n);
                editor.putInt("score" + num, sc1);
                num++;
                editor.putInt("score"+p2com,p2val+sc2);

            }
            if(p2com==-1){
                editor.putString("name" + (num), p2n);
                editor.putInt("score" + (num), sc2);
                num++;
                editor.putInt("score"+p1com,p1val+sc1);
            }
        }else{
            editor.putInt("score"+p2com,p2val+sc2);
            editor.putInt("score"+p1com,p1val+sc1);
        }
        editor.putInt("num", num);
        editor.commit();

        showList();

        Button ngb=findViewById(R.id.ngbtn);
        ngb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(leader.this,choice.class);
                startActivity(intent1);
            }
        });
    }



    public void recycle1(){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recView);
        RecylerViewAdapter recylerViewAdapter=new RecylerViewAdapter(leader.this,name);
        recyclerView.setAdapter(recylerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void showList(){
        for(int i=0;i<num;i++) {
            name.add(new player(sharedPreferences.getString("name" + i, "j"), sharedPreferences.getInt("score" + i, 0)));
        }
        recycle1();
    }
}
