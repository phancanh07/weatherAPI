package com.example.weatherapi.inter;

import com.example.weatherapi.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaholderApi {
//    data/2.5/forecast?q=SEUL&appid=0695c3ec837519c183ae5f50cd77c3ba&units=metric&lang=vi
    @GET("data/2.5/forecast")
    Call<Example> getData(@Query("q") String cityname
            , @Query("appid") String appID
            , @Query("units") String units
            , @Query("lang") String lang);
}