package com.example.tic_tac_toe;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {


    private ArrayList<player> name=new ArrayList<>();

    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,score;
        LinearLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.layout_parent);
            name=itemView.findViewById(R.id.name);
            score=itemView.findViewById(R.id.score);



        }
    }


    public RecylerViewAdapter( Context context,ArrayList<player> name) {
        this.name = name;
        Collections.sort(name);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listelem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(name.get(position).name);
        holder.score.setText(String.valueOf(name.get(position).score));
        if(position%3==0) {
            holder.parent.setBackgroundColor(holder.parent.getResources().getColor(R.color.tingGreen));
        }else if(position%4==0){
            holder.parent.setBackgroundColor(holder.parent.getResources().getColor(R.color.Lpink));
        }else if(position%2==0){
            holder.parent.setBackgroundColor(holder.parent.getResources().getColor(R.color.LightBrown));
        }else{
            holder.parent.setBackgroundColor(holder.parent.getResources().getColor(R.color.lgrey));
        }



    }





    @Override
    public int getItemCount() {
        return name.size();
    }



}
