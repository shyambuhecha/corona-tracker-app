package com.example.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtrackerapp.api.ApiUtilities;
import com.example.covidtrackerapp.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView totalActive,totalConfirm,totalDeaths,totalRecovered,totalTests;
    private TextView todayRecovered,todayConfirm,todayDeaths;
    private TextView date;
    PieChart mPieChart;

    private List<CountryData> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        init();


        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                list.addAll(response.body());

                for(int i=0;i<list.size();i++){
                    if(list.get(i).getCountry().equals("India")){
                        int confirm = Integer.parseInt(list.get(i).getCases());
                        int active = Integer.parseInt(list.get(i).getActive());
                        int recovered = Integer.parseInt(list.get(i).getRecovered());
                        int death= Integer.parseInt(list.get(i).getDeaths());

                        totalActive.setText(NumberFormat.getInstance().format(active));
                        totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                        totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                        totalDeaths.setText(NumberFormat.getInstance().format(death));
                        totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                        todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                        todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                        todayDeaths.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));

                        setText(list.get(i).getUpdated());

                        mPieChart.addPieSlice(new PieModel("Confirm",confirm,getResources().getColor(R.color.yellow)));
                        mPieChart.addPieSlice(new PieModel("Active",active,getResources().getColor(R.color.blue_pie)));
                        mPieChart.addPieSlice(new PieModel("Recovered",recovered,getResources().getColor(R.color.green_pie)));
                        mPieChart.addPieSlice(new PieModel("Death",death,getResources().getColor(R.color.red_pie)));
                        mPieChart.startAnimation();

                    }


                }
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: "+ t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setText(String updated) {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        long millisecond = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
         date.setText("Update at "+ calendar.getTime());
    }

    private void init(){
        totalActive=findViewById(R.id.totalActive);
        totalConfirm=findViewById(R.id.totalConfirm);
        totalDeaths=findViewById(R.id.totalDeath);
        totalRecovered=findViewById(R.id.totalRecovered);
        totalTests=findViewById(R.id.totalTests);
        todayConfirm=findViewById(R.id.todayConfirm);
        todayDeaths=findViewById(R.id.todayDeath);
        todayRecovered=findViewById(R.id.todayRecovered);
        mPieChart = (PieChart) findViewById(R.id.piechart);
        date=findViewById(R.id.date);
    }
}