package com.example.bobgranata.testtaskforandroid;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by BobGranata on 25.07.2017.
*/

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PersonViewHolder>{
    List<Adverts> mAdvertsList;
    Context mContext;
    RecyclerViewAdapter(Context context, List<Adverts> advertsList){
        this.mContext = context;
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

        String sPhotoUrl = mAdvertsList.get(position).photoUrl;

        Picasso.with(mContext).load(sPhotoUrl).placeholder(R.drawable.load_image).error(R.drawable.cat_commandments).into(holder.advPhoto);
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