package com.example.weatherapi;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_weather;
    private EditText editText_search;
    private ImageView testimage;
    boolean isConnected = false;
    MediaPlayer mediaPlayer;
    ConnectivityManager connectivityManager;
    VideoView videoView;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    private TextView cityname, countryname, date_nows, txt_nhietdo, txt_trangthai, txt_luongmua, txt_doam, txt_speedwindy, txt_mintemp;
    private Button btn_search, btn_nextDay;
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String MY_KEY_API = "0695c3ec837519c183ae5f50cd77c3ba";
    public static final String LANGUAGE = "&lang=vi";
    public static final String URL_ICON = "http://openweathermap.org/img/wn/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        isCheckInternet();
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.media);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer = mp;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
        }
        btn_search.setOnClickListener(this::onClick);
        btn_nextDay.setOnClickListener(this::onClick);
        getCurentWeatherData("HANOI");
    }

    private void initView() {
        img_weather = findViewById(R.id.img_clouds);
        editText_search = findViewById(R.id.txt_search_city);
        cityname = findViewById(R.id.txt_city);
        countryname = findViewById(R.id.txt_country);

        date_nows = findViewById(R.id.nows);
        txt_doam = findViewById(R.id.txt_am);
        txt_luongmua = findViewById(R.id.txt_rain);
        txt_nhietdo = findViewById(R.id.txt_temp);
        txt_speedwindy = findViewById(R.id.txt_wind);
        txt_trangthai = findViewById(R.id.txt_desprication);
        txt_mintemp = findViewById(R.id.mintemp);
        testimage = findViewById(R.id.testimage);
        btn_search = findViewById(R.id.btn_search);
        btn_nextDay = findViewById(R.id.nextDay);
        videoView = findViewById(R.id.videoView);
    }

    public void getCurentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/find?q=" + data + "&appid=" + MY_KEY_API + LANGUAGE + "&units=metric";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray list = jsonObject.getJSONArray("list");
                    JSONObject listChidren = list.getJSONObject(0);
                    //lấy phần tử trong list
                    String name = listChidren.getString("name");

                    String ngayHT = listChidren.getString("dt");
                    //lấy mây
                    JSONObject clounds = listChidren.getJSONObject("clouds");
                    String may = clounds.getString("all");
                    //lấy vĩ độ
                    JSONObject coordObject = listChidren.getJSONObject("coord");
                    String lat = coordObject.getString("lat");
                    String lon = coordObject.getString("lon");


                    //lấy ngày hiên tại
                    long ngay = Long.valueOf(ngayHT);
                    Date date = new Date(ngay * 1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE-dd-MM-yyyy");
//                    Date date3 = new Date(1617545363);

                    String day = simpleDateFormat.format(date);
                    //lấy phần tử trong wearther  thuộc MẢNG LIST
                    JSONObject object = listChidren.getJSONObject("main");
                    //lấy nhiệt độ
                    String temp = object.getString("temp");
                    String tempmin = object.getString("feels_like");
                    String humidity = object.getString("humidity");
                    //lấy mảng country
                    JSONObject COUNTRY = listChidren.getJSONObject("sys");
                    String country = COUNTRY.getString("country");
                    //lấy phần tử trong mảng wearther
                    JSONArray jsonArrayWearther = listChidren.getJSONArray("weather");
                    JSONObject weatherJson = jsonArrayWearther.getJSONObject(0);
                    //lấy mô tả thời tiết
                    String description = weatherJson.getString("description");
                    String icon = weatherJson.getString("icon");

                    //lấy giá trị trong  wind
                    JSONObject windy = listChidren.getJSONObject("wind");
                    //lấy tốc độ gió
                    String speedwind = windy.getString("speed");
                    Picasso.get().load(URL_ICON + icon + "@2x.png").into(img_weather);
                    txt_doam.setText(humidity + "%");
                    txt_nhietdo.setText(temp + "°C");
                    txt_speedwindy.setText(speedwind + "m/s");
                    txt_trangthai.setText(description);
                    date_nows.setText(day);
                    txt_luongmua.setText(may + "%");
                    countryname.setText(country);
                    cityname.setText(name);
                    txt_mintemp.setText("Cảm Nhận : \t" + tempmin + "°C");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ERROL" + e);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "LỖI " + error);
                    }
                });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (isCheckInternet() != true) {
            if (v == btn_search) {
                isCheckInternet();
                String cityname = editText_search.getText().toString().trim();
                getCurentWeatherData(cityname);
            } else if (v == btn_nextDay) {
                isCheckInternet();
                String cityname = editText_search.getText().toString().trim();
                Intent intent = new Intent(getApplicationContext(), WeatherSevenDayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key_city", cityname);
                intent.putExtras(bundle);
                startActivity(intent);
                mediaPlayer.pause();
            }
        }
    }

    public boolean isCheckInternet() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(getApplicationContext(), "VUI LÒNG BẬT MẠNG HOẶC DỮ LIỆU DI ĐỘNG", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), NotActivity.class));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        mediaPlayer.start();
        videoView.start();
        super.onResume();
    }
}