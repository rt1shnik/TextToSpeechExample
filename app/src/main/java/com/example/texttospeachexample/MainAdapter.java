package com.example.texttospeachexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private ArrayList<String> items;
    private int currentPosition = -1;

    public MainAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(inflater.inflate(R.layout.adapter_item, null));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(items.get(position));
        if (currentPosition == position) {
            holder.img1.setVisibility(View.INVISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
        } else {
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.INVISIBLE);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView img1;
        ImageView img2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.image1);
            img2 = itemView.findViewById(R.id.image2);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
