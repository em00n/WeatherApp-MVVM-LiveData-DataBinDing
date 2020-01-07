package com.emon.weather_app_android_mvvm_architecture_livedata_databinding.model;

import com.google.gson.annotations.SerializedName;

public class Sys {
    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public long sunrise;
    @SerializedName("sunset")
    public long sunset;
}
