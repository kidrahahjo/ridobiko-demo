package com.project.ridobiko.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.ridobiko.API.RetrofitClient;
import com.project.ridobiko.INCLUDES.City;
import com.project.ridobiko.R;
import com.project.ridobiko.RESPONSES.CityResponse;

import java.security.cert.CertificateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView startDateTV;
    private TextView endDateTV;
    private DatePickerDialog.OnDateSetListener startDateListner;
    private DatePickerDialog.OnDateSetListener endDateListner;
    public int startDate;
    public int endDate;
    public int startMonth;
    public int endMonth;
    public int startYear;
    public int endYear;
    public String city;

    private List<City> cities;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    SimpleDateFormat sdf;
    public Date dateStart;
    public Date dateEnd;

    Button searchBn;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Ridobiko: Home");

        sdf = new SimpleDateFormat("yyyy-MM-dd");

        spin = (Spinner) findViewById(R.id.cityList);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                city = item.toString();

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        startDateTV = findViewById(R.id.start);
        endDateTV = findViewById(R.id.end);

        searchBn = findViewById(R.id.search);

        startDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DATE);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth,startDateListner,year,month,date);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Start of Journey");
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.show();
            }
        });
        endDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DATE);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth,endDateListner,year,month,date);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("End of Journey");
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.show();
            }
        });

        startDateListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                startDate = dayOfMonth;
                startYear = year;
                startMonth = month;

                try{
                    dateStart = sdf.parse(year+"-"+month+"-"+dayOfMonth);
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                String date = dayOfMonth + "/" + month + "/" + year;
                startDateTV.setText(date);
            }
        };
        endDateListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                endDate = dayOfMonth;
                endYear = year;
                endMonth = month;
                try {
                    dateEnd = sdf.parse(year+"-"+month+"-"+dayOfMonth);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String date = dayOfMonth + "/" + month + "/" + year;
                endDateTV.setText(date);
            }
        };


        searchBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateStart==null){
                    Toast.makeText(getApplicationContext(),"Enter start date",Toast.LENGTH_LONG).show();
                }
                else if(dateEnd==null){
                    Toast.makeText(getApplicationContext(),"Enter end date",Toast.LENGTH_LONG).show();
                }
                else if(city.equals("") || city==null){
                    Toast.makeText(getApplicationContext(),"Enter the city",Toast.LENGTH_LONG).show();
                }
                else if(dateEnd.after(dateStart)){
                    Intent in = new Intent(MainActivity.this,BikeActivity.class);
                    in.putExtra("City",city);
                    in.putExtra("StartDate",startYear+"-"+startMonth+"-"+startDate);
                    in.putExtra("EndDate",endYear+"-"+endMonth+"-"+endDate);
                    startActivity(in);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid dates",Toast.LENGTH_LONG).show();
                }
            }
        });

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Loading cities, please wait")
                .setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        getCities();
    }

    public void getCities(){

        Call<CityResponse> call = RetrofitClient.getInstance().getApi().getCities();

        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                try{
                    CityResponse cr = response.body();
                    String message;
                    try{
                        message = cr.getMessage();
                    }catch (Exception e){
                        message = "ERROR";
                    }
                    if(message.equals("Successful")){
                        cities = cr.cityNames();
                        List<String> city = new ArrayList<String>();
                        for (int i = 0; i < cities.size(); i++) {
                            city.add(cities.get(i).getCity());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,city);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spin.setAdapter(adapter);
                        alertDialog.cancel();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error occurred",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    alertDialog.setMessage("Failed");
                    alertDialog.setCancelable(true);
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                alertDialog.setMessage("Connection Failed");
                alertDialog.setCancelable(true);
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.search:
                getCities();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
