package com.emon.weather_app_android_mvvm_architecture_livedata_databinding.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.emon.weather_app_android_mvvm_architecture_livedata_databinding.model.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectRepository {
    public static final String BaseUrl="https://api.openweathermap.org/";
    public static final String appliedId="73bb1dd44b0cc93ec1b695164df7e092";
    public static String lat = "28.21";
    public static String lon = "79.54";

    private static ProjectRepository projectRepository;
    private ApiService apiService;

    public ProjectRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized ProjectRepository getInstance() {
        if (projectRepository == null) {
            projectRepository = new ProjectRepository();

        }
        return projectRepository;
    }

    public LiveData<Model> getData(String lat, String lon, String appliedId){
        final MutableLiveData<Model> data=new MutableLiveData<>();
        apiService.getCurrentWeather(lat,lon,appliedId).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
            //    data.setValue(null);

            }
        });
        return data;
    }
}
