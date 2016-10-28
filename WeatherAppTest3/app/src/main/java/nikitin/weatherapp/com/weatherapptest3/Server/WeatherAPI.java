package nikitin.weatherapp.com.weatherapptest3.Server;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import nikitin.weatherapp.com.weatherapptest3.City;
import nikitin.weatherapp.com.weatherapptest3.CityAdapter;
import nikitin.weatherapp.com.weatherapptest3.Fragments.CitiesFragment;
import nikitin.weatherapp.com.weatherapptest3.PreferencesAPI;

/**
 * Created by Влад on 01.07.2016.
 */
public class WeatherAPI {

    public static WeatherAPI weatherAPI;
    public static String urlWeatherByCityName = "http://api.openweathermap.org/data/2.5/weather?q=%1$s,%2$s&appid=%3$s";
    public static String urlWeatherByCityId = "http://api.openweathermap.org/data/2.5/weather?id=%1$d&appid=%2$s";
    public static String urlFindCityByName = "http://api.openweathermap.org/data/2.5/find?q=%1$s&type=like&appid=%2$s";
    public static String urlWeatherByCoordinate = "http://api.openweathermap.org/data/2.5/weather?lat=%1$f&lon=%2$f&appid=%3$s";
    public static String appId = "485400ed5d502b7b04378efed428665b";
    public static String [] appIdArray = {"485400ed5d502b7b04378efed428665b", "ebada0dfd416b3eac0580be8f683374a", "825bf54f9180eef27ebd4554b2d15aa4", "6217835778407ccc2bb15bd84c54af6f"};
    public static int appIdPosition = 0;
    public boolean end = false;
    public static WeatherAPI getInstance () {
        if (weatherAPI==null) {
            weatherAPI = new WeatherAPI();
        }
        return weatherAPI;
    }

    public void getWeatherByCityName(String cityName, String countryCode, final ResponseCallback callback) {
        new GetTask(String.format(urlWeatherByCityName, cityName, countryCode, appId), new RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }).execute();
    }

    public void getWeatherByCityId(int cityId, final ResponseCallback callback) {
        new GetTask(String.format(urlWeatherByCityId, cityId, appId), new RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }).execute();
    }

    public void getCitiesByCityName(String cityName, final ResponseCallback callback) {
        new GetTask(String.format(urlFindCityByName, cityName, appId), new RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }).execute();
    }

    public void getWeatherByCityCoordinate(String appId,double latitude, double longitude, final ResponseCallback callback) {
        new GetTask(String.format(urlWeatherByCoordinate, latitude, longitude, appId), new RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
                //CitiesFragment.
            }
        }).execute();
    }

//

    class AppIdException extends Exception {
        public AppIdException(){}
    }

    private String getNextAppId() {
        if (appIdPosition == appIdArray.length) {
            appIdPosition = 0;
            return "end";
        }
        return appIdArray[appIdPosition++];
    }
}