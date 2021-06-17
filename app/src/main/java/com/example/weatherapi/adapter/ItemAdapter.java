package com.example.weatherapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapi.R;
import com.example.weatherapi.inter.CoordLocations;
import com.example.weatherapi.model.City;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHoder>  {

    private List<com.example.weatherapi.model.List> lists;
    private Context context;
    private City city;
    private CoordLocations coordLocations;

    public ItemAdapter(List<com.example.weatherapi.model.List> lists, Context context, City city) {
        this.lists = lists;
        this.context = context;
        this.city = city;
    }

    @NonNull
    @Override
    public ItemViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new ItemViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHoder holder, int position) {
        com.example.weatherapi.model.List list = lists.get(position);
        holder.txt_ngaynext.setText(list.getDtTxt());
        holder.despricationday.setText(list.getWeather().get(0).getDescription());
        holder.tempday.setText(list.getMain().getTemp()+"°C");
        Picasso.get().load("http://openweathermap.org/img/wn/" + list.getWeather().get(0).getIcon().trim() + ".png").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        } else {
            return 0;
        }

    }



    public class ItemViewHoder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txt_ngaynext, despricationday, tempday;
        public ItemViewHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_cloudsday);
            txt_ngaynext = itemView.findViewById(R.id.txt_daynext);
            despricationday = itemView.findViewById(R.id.txt_despricationday);
            tempday = itemView.findViewById(R.id.txt_tempday);
        }
    }
}
