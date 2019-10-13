package com.project.ridobiko.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.ridobiko.R;

public class BookedActivity extends AppCompatActivity {

    String cityName,rentamountS,depositS,name, idSelect,dateEnd, dates;
    int deposit,rentamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);

        setTitle("Ridobiko: Booked");

        Bundle bundle = getIntent().getExtras();
        cityName = bundle.getString("CITYNAME");
        name = bundle.getString("NAME");
        idSelect = bundle.getString("ID");
        deposit = bundle.getInt("DEPOSIT");
        rentamount = bundle.getInt("RENT");
        dates = bundle.getString("DATES");

        ImageView imageView = findViewById(R.id.bookingIMG);
        TextView nameTV = findViewById(R.id.bike_n);
        TextView rentTV = findViewById(R.id.bike_r);
        TextView depositTV = findViewById(R.id.bike_dep);
        TextView idTV = findViewById(R.id.bike_i);
        TextView dateTV = findViewById(R.id.bike_d);


        rentamountS = "Your rent is Rs."+rentamount;
        depositS = "You have to deposit Rs."+deposit;
        idSelect = "Your Bike ID is "+idSelect;
        name= "You've booked "+name;

        nameTV.setText(name);
        rentTV.setText(rentamountS);
        depositTV.setText(depositS);
        idTV.setText(idSelect);
        dateTV.setText(dates);
        imageView.setImageResource(R.drawable.booked);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(BookedActivity.this,MainActivity.class);
        startActivity(setIntent);
    }
}
