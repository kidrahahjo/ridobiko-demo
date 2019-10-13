package com.project.ridobiko.RECYCLER_VIEW;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ridobiko.ACTIVITIES.BikeActivity;
import com.project.ridobiko.ACTIVITIES.BookedActivity;
import com.project.ridobiko.R;

public class BikeViewHolder extends RecyclerView.ViewHolder {

    private ImageView bikeImage = null;
    private TextView bikeName = null;
    private TextView bikeID = null;
    private TextView bikeRent = null;
    private TextView bikeDeposit = null;
    public Context context = null;
    public String packageName = null;

    static AlertDialog.Builder builder;
    public static AlertDialog myAlert;
    static AlertDialog.Builder myBuilder;
    static AlertDialog alert;


    public BikeViewHolder(@NonNull View itemView) {
        super(itemView);

        if (itemView != null) {
            bikeName = (TextView) itemView.findViewById(R.id.bike_name);
            bikeID = (TextView) itemView.findViewById(R.id.bike_ID);
            bikeRent = (TextView) itemView.findViewById(R.id.bike_rent);
            bikeDeposit = (TextView) itemView.findViewById(R.id.bike_deposit);
            bikeImage = (ImageView) itemView.findViewById(R.id.bike_image);
            context = itemView.getContext();
            packageName = context.getPackageName();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String idSelect = bikeID.getText().toString().replace("Bike ID: ","");
                    final int deposit = Integer.valueOf(bikeDeposit.getText().toString().replace("Bike Deposit: ",""));
                    final int rentamount = Integer.valueOf(bikeRent.getText().toString().replace("Bike Rent: ","")) * BikeActivity.totalDays;
                    final String name = bikeName.getText().toString();


                    builder = new AlertDialog.Builder(context);
                    builder.setMessage("You have to pay Rs. " + deposit + " as DEPOSIT and Rs. " + rentamount + " as your RENT amount. Book the bike?")
                            .setPositiveButton("Book Now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myBuilder = new AlertDialog.Builder(context);
                                    myBuilder.setMessage("Booking your bike, please wait")
                                            .setCancelable(false);
                                    myAlert = myBuilder.create();
                                    myAlert.show();
                                    BikeActivity.name = name;
                                    BikeActivity.id = idSelect;
                                    BikeActivity.rentamount = rentamount;
                                    BikeActivity.deposit = deposit;
                                    BikeActivity.bookBike(idSelect, BikeActivity.getBookStart(), BikeActivity.getEndDate());
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    alert = builder.create();
                    alert.show();
                }
            });
        }
    }
    public ImageView getBikeImage() {
        return bikeImage;
    }

    public TextView getBikeName() {
        return bikeName;
    }

    public TextView getBikeID() {
        return bikeID;
    }

    public TextView getBikeRent() {
        return bikeRent;
    }

    public TextView getBikeDeposit() {
        return bikeDeposit;
    }
}
