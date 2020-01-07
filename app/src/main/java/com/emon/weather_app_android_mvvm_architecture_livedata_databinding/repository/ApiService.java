package com.emon.weather_app_android_mvvm_architecture_livedata_databinding.repository;


import com.emon.weather_app_android_mvvm_architecture_livedata_databinding.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("data/2.5/weather?")
    Call<Model> getCurrentWeather(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("APPID") String appid
    );

}

