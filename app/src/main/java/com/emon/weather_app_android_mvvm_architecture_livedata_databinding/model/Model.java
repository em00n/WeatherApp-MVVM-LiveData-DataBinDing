package com.emon.weather_app_android_mvvm_architecture_livedata_databinding.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Model {

        @SerializedName("coord")
        public Coord coord;
        @SerializedName("sys")
        public Sys sys;
        @SerializedName("weather")
        public ArrayList<Weather> weather = new ArrayList<Weather>();

        @SerializedName("main")
        public Main main;
        @SerializedName("wind")
        public Wind wind;
        @SerializedName("rain")
        public Rain rain;
        @SerializedName("clouds")
        public Clouds clouds;
        @SerializedName("dt")
        public long dt;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("cod")
        public float cod;
    }

class Clouds {
    @SerializedName("all")
    public float all;
}

class Rain {
    @SerializedName("3h")
    public float h3;
}

class Wind {
    @SerializedName("speed")
    public float speed;
    @SerializedName("deg")
    public float deg;
}

class Coord {
    @SerializedName("lon")
    public float lon;
    @SerializedName("lat")
    public float lat;
}