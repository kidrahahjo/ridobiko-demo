package com.project.ridobiko.RECYCLER_VIEW;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ridobiko.ACTIVITIES.BikeActivity;
import com.project.ridobiko.INCLUDES.Bikes;
import com.project.ridobiko.R;

import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeViewHolder>{
    private List<Bikes> bikesList;

    public BikeAdapter(List<Bikes> bikesList) {
        this.bikesList = bikesList;
    }

    @NonNull
    @Override
    public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View bikeItemView = layoutInflater.inflate(R.layout.bike_info, parent, false);

        BikeViewHolder bikeViewHolder = new BikeViewHolder(bikeItemView);

        return bikeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BikeViewHolder holder, int position) {
        Bikes bikes = bikesList.get(position);
        Resources res = holder.context
                .getResources();
        String mDrawableName = bikes.getBikePic();
        int resID = res.getIdentifier(mDrawableName , "drawable", holder.packageName);
        holder.getBikeImage().setImageResource(resID);
        holder.getBikeName().setText(bikes.getBikeName());
        holder.getBikeID().setText("Bike ID: "+bikes.getBikeID());
        holder.getBikeRent().setText("Bike Rent: "+bikes.getBikeRent());
        holder.getBikeDeposit().setText("Bike Deposit: "+bikes.getBikeDeposit());
    }

    @Override
    public int getItemCount() {
        try {
            return bikesList.size();
        }catch (Exception e){
            return 0;
        }
    }
}
