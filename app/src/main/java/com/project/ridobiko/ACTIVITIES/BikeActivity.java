package com.project.ridobiko.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.project.ridobiko.API.RetrofitClient;
import com.project.ridobiko.INCLUDES.Bikes;
import com.project.ridobiko.R;
import com.project.ridobiko.RECYCLER_VIEW.BikeAdapter;
import com.project.ridobiko.RECYCLER_VIEW.BikeViewHolder;
import com.project.ridobiko.RESPONSES.BikeResponse;
import com.project.ridobiko.RESPONSES.BookResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BikeActivity extends AppCompatActivity {

    static String cityName;
    static String bookStart;
    static String bookEnd;
    List<Bikes> bikesList;

    SimpleDateFormat sdf;
    public Date dateStart;
    public Date dateEnd;
    static Context context;

    public static int totalDays,deposit,rentamount, image;
    public static String name,id,dates;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    public static View mView;


    public static String getBookStart() {
        return bookStart;
    }

    public static String getEndDate() {
        return bookEnd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        setTitle("Ridobiko: Details");

        sdf = new SimpleDateFormat("yyyy-MM-dd");

        context = BikeActivity.this;

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            cityName = bundle.getString("City");
            bookStart = bundle.getString("StartDate");
            bookEnd = bundle.getString("EndDate");
            dates = "From "+bookStart+" to "+bookEnd;
            try{
                dateStart = sdf.parse(bookStart);
                dateEnd = sdf.parse(bookEnd);

                totalDays = (int)( (dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24));
            }catch (ParseException e){
                e.printStackTrace();
            }

            builder = new AlertDialog.Builder(this);
            builder.setMessage("Loading bikes, please wait")
                    .setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();

            getBikes();

        }
        else{
            Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_LONG).show();
        }
    }


    public static void bookBike(final String idSelect, final String bookStart, final String bookEnd) {
        Call<BookResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .bookBike(bookStart,bookEnd,idSelect);

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                try{
                    BookResponse br = response.body();
                    String message;
                    try {
                        message = br.getMessage();
                    } catch (Exception e) {
                        Toast.makeText(context,"Exception caught",Toast.LENGTH_LONG).show();
                        message = "Unsuccessful";
                    }
                    if(message.equals("Successful")){
                        Intent in = new Intent(context,BookedActivity.class);

                        in.putExtra("RENT",rentamount);
                        in.putExtra("DEPOSIT",deposit);
                        in.putExtra("ID",idSelect);
                        in.putExtra("NAME",name);
                        in.putExtra("CITYNAME",cityName);
                        in.putExtra("DATES",dates);
                        BikeViewHolder.myAlert.cancel();
                        context.startActivity(in);
                    }
                    else{
                        BikeViewHolder.myAlert.setMessage("Something went wrong");
                        BikeViewHolder.myAlert.setCancelable(true);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {

            }
        });
    }

    private void getBikes() {
        Call<BikeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .bikeDetails(cityName, bookStart,bookEnd);

        call.enqueue(new Callback<BikeResponse>() {
            @Override
            public void onResponse(Call<BikeResponse> call, Response<BikeResponse> response) {
                try {
                    BikeResponse br = response.body();
                    String message;
                    try {
                        message = br.getMessage();
                    } catch (Exception e) {
                        message = "ERROR";
                    }
                    if (message.equals("Successful")) {
                        bikesList = br.getBikes();
                        alertDialog.cancel();

                        RecyclerView bikeRecyclerView = (RecyclerView)findViewById(R.id.bike_view);
                        bikeRecyclerView.setHasFixedSize(true);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BikeActivity.this);
                        bikeRecyclerView.setLayoutManager(linearLayoutManager);

                        BikeAdapter bikeAdapter = new BikeAdapter(bikesList);
                        bikeRecyclerView.setAdapter(bikeAdapter);

                    } else if(message.equals("No Bikes Available")){
                        Toast.makeText(getApplicationContext(), "No bikes available", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(context,MainActivity.class);
                        startActivity(in);
                    }
                } catch (Exception e) {
                    alertDialog.setMessage("Failed");
                    alertDialog.setCancelable(true);
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BikeResponse> call, Throwable t) {
                alertDialog.setMessage("Connection Failed");
                alertDialog.setCancelable(true);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(BikeActivity.this,MainActivity.class);
        startActivity(setIntent);
    }

}
