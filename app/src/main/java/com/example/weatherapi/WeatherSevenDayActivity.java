package com.example.weatherapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapi.adapter.ItemAdapter;
import com.example.weatherapi.inter.ApiClient;
import com.example.weatherapi.inter.JsonPlaholderApi;
import com.example.weatherapi.model.City;
import com.example.weatherapi.model.Coord;
import com.example.weatherapi.model.Example;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherSevenDayActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ID_APP = "0695c3ec837519c183ae5f50cd77c3ba";
    public static final String UNITS = "metric";
    public static final String LANG = "vi";

    private ProgressBar progressBar;
    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    Coord coord = new Coord();
    City city = new City();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_seven_day);
        recyclerView = findViewById(R.id.recycleview);
        progressBar = findViewById(R.id.progressBar3);
        actionButton = findViewById(R.id.floatingActionButton2);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String cityname = bundle.getString("key_city");
            getWeather15day(cityname);
        }
        getWeather15day("hà nội");

        actionButton.setOnClickListener(this::onClick);

    }

    public void getWeather15day(String citys) {

        JsonPlaholderApi jsonPlaholderApi = ApiClient.getClient().create(JsonPlaholderApi.class);
        jsonPlaholderApi.getData(citys, ID_APP, UNITS, LANG).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                progressBar.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    Example example = response.body();
                    coord.setLat(example.getCity().getCoord().getLat());
                    coord.setLon(example.getCity().getCoord().getLon());
                    city.setName(example.getCity().getName());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    ItemAdapter itemAdapter = new ItemAdapter(example.getList(), getApplicationContext(), example.getCity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(itemAdapter);
                    recyclerView.setHasFixedSize(true);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("TAG", t.getMessage());

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == actionButton) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key_1", coord.getLat());
            bundle.putString("key_2", coord.getLon());
            bundle.putString("key_3", city.getName());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}

