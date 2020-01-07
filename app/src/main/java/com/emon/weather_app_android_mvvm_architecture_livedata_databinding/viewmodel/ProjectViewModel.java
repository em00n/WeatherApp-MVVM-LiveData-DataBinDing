package com.emon.weather_app_android_mvvm_architecture_livedata_databinding.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emon.weather_app_android_mvvm_architecture_livedata_databinding.model.Model;
import com.emon.weather_app_android_mvvm_architecture_livedata_databinding.repository.ProjectRepository;

public class ProjectViewModel extends AndroidViewModel {
    LiveData<Model> dataObsarvable;
    String lat,lon;
    public ProjectViewModel(@NonNull Application application) {
        super(application);
    }
   public LiveData<Model> getDataObsarvable(String lat,String lon){
       dataObsarvable= ProjectRepository.getInstance().getData(lat,lon,ProjectRepository.appliedId);
        return dataObsarvable;
    }
}
