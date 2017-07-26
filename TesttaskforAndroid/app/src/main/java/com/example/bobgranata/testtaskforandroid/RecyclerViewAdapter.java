package com.example.bobgranata.testtaskforandroid;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BobGranata on 25.07.2017.
*/

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PersonViewHolder>{
    List<Adverts> mAdvertsList;
    RecyclerViewAdapter(List<Adverts> advertsList){
        this.mAdvertsList = advertsList;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.advType.setText(mAdvertsList.get(position).type);
        holder.advTitle.setText(mAdvertsList.get(position).title);
        holder.advDate.setText(mAdvertsList.get(position).date);
        holder.advPrice.setText(mAdvertsList.get(position).price);
        holder.advPhoto.setImageResource(mAdvertsList.get(position).photoId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mAdvertsList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView advPhoto;
        TextView advType;
        TextView advDate;
        TextView advTitle;
        TextView advPrice;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            advPhoto = (ImageView)itemView.findViewById(R.id.adv_photo);
            advType= (TextView)itemView.findViewById(R.id.adv_type);
            advDate = (TextView)itemView.findViewById(R.id.adv_date);
            advTitle = (TextView)itemView.findViewById(R.id.adv_title);
            advPrice = (TextView)itemView.findViewById(R.id.adv_price);
        }
    }
}