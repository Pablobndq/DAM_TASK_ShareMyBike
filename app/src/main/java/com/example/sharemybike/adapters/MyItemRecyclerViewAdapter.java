package com.example.sharemybike.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemybike.R;
import com.example.sharemybike.models.BikesContent;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<BikesContent.Bike> bikes;
    private int layout;
    private OnEmailBtnClickListener listener;

    public MyItemRecyclerViewAdapter(List<BikesContent.Bike> bikes, int layout, OnEmailBtnClickListener listener){
        this.bikes = bikes;
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(bikes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView photo;
        public TextView city_text;
        public TextView owner_text;
        public TextView description_text;
        public ImageButton email_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.photo = itemView.findViewById(R.id.imageView);
            this.city_text = itemView.findViewById(R.id.city_textView);
            this.owner_text = itemView.findViewById(R.id.owner_textView);
            this.description_text = itemView.findViewById(R.id.description_textView);
            this.email_btn = itemView.findViewById(R.id.btn_email);
        }

        public void bind(final BikesContent.Bike bike, final OnEmailBtnClickListener listener){

            this.city_text.setText(bike.getLocation());
            this.owner_text.setText(bike.getOwner());
            this.description_text.setText(bike.getDescription());
            this.photo.setImageBitmap(bike.getPhoto());

            this.email_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBtnClick(bike, getAdapterPosition());
                }
            });
        }
    }

    public interface OnEmailBtnClickListener {
        void onBtnClick(BikesContent.Bike bike, int position);
    }
}
