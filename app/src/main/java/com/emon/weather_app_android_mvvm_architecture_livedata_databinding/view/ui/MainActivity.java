package com.emon.weather_app_android_mvvm_architecture_livedata_databinding.view.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.emon.weather_app_android_mvvm_architecture_livedata_databinding.R;
import com.emon.weather_app_android_mvvm_architecture_livedata_databinding.databinding.ActivityMainBinding;
import com.emon.weather_app_android_mvvm_architecture_livedata_databinding.model.Model;
import com.emon.weather_app_android_mvvm_architecture_livedata_databinding.viewmodel.ProjectViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    ProjectViewModel projectViewModel;

    private FusedLocationProviderClient client;
    private LocationCallback callback;
    private LocationRequest request;

    public static String lat ;
    public static String lon ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);

        getLatLon();
       // getWeather();

    }

    private void getWeather(){

        projectViewModel.getDataObsarvable(lat,lon).observe(this, new Observer<Model>() {
            @Override
            public void onChanged(Model model) {
                String img=model.weather.get(0).icon.toString().trim();
                String imgUrl="http://openweathermap.org/img/w/"+img+".png";
                //    Glide.with(MainActivity.this).load(imgUrl).into(imageView);
                Picasso.get().load(imgUrl).into(activityMainBinding.weatherIconId);

                String temp = convert(String.valueOf(model.main.temp));
                activityMainBinding.temparetureId.setText(temp);

                activityMainBinding.addressTvId.setText(model.name+", "+model.sys.country);
                activityMainBinding.sunriseTextView.setText(getTime(model.sys.sunrise));
                activityMainBinding.sunsetTextView.setText(getTime(model.sys.sunset));
                activityMainBinding.dateTimeId.setText(getDateTime(model.dt));
                activityMainBinding.weatherDiscriptionId.setText(model.weather.get(0).description);
                activityMainBinding.humidityId.setText("Humidity : "+model.main.humidity +" %");
                activityMainBinding.pressureId.setText("Pressure : "+model.main.pressure +" mBar");


            }


        });
    }

    private void getLatLon(){
        client = LocationServices.getFusedLocationProviderClient(this);
        request = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3000)
                .setFastestInterval(1000);

        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    lat = String.valueOf(location.getLatitude());
                    lon = String.valueOf(location.getLongitude());

                    getWeather();
                }

            }
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            }
        }

        client.requestLocationUpdates(request, callback, null);

    }


   public String convert(String v) {
        double conver = Double.parseDouble(v);
        double value = conver - 273.15;
        int finalValue=(int)value;
        return String.valueOf(finalValue);
    }

    private String getDateTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm a", cal).toString();
        return date;
    }
    private String getTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                }

                client.requestLocationUpdates(request, callback, null);
            }
        }
    }

}