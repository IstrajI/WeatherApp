package nikitin.weatherapp.com.weatherapptest3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nikitin.weatherapp.com.weatherapptest3.Fragments.CitiesFragment;
import nikitin.weatherapp.com.weatherapptest3.Presenters.CitiesPresenter;
import nikitin.weatherapp.com.weatherapptest3.model.WeatherResponse;

/**
 * Created by Влад on 04.08.2016.
 */
public class PreferencesAPI {
    String TAG = "PreferencesAPI";
    final String CITIES_AMOUNT = "Amount";
    final String CITY_NAME = "CityName";
    final String CITY_ID = "CityId";
    final String COUNTRY = "Country";
    final String IS_ACTIVE = "IsActive";
    final String CITY_ITEM = "city_%1$s";

    final String WEATHER_TEMPERATURE = "Temperature";
    final String WEATHER_WEATHER_NAME = "WeatherName";
    final String WEATHER_HUMIDITY = "Humidity";
    final String WEATHER_WIND_SPEED = "WindSpeed";
    final String WEATHER_PRESSURE = "Pressure";

    static PreferencesAPI preferencesAPI;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public int citiesAmount = 0;
    private Gson gson;


    private PreferencesAPI(Activity activity) {
        preferences = activity.getPreferences(activity.MODE_PRIVATE);
        editor = preferences.edit();
        //citiesAmount = restoreCitiesAmount();
        gson = new Gson();
    }

    public static PreferencesAPI getInstance(Activity activity) {
        if (preferencesAPI == null) {
            preferencesAPI = new PreferencesAPI(activity);
        }
        return preferencesAPI;
    }

    public void saveCities() {
        CityAdapter cityAdapter = CitiesFragment.getInstance().getPresenter().getCityAdapter();
        editor.putInt(CITIES_AMOUNT, cityAdapter.getCount()-1);
        for (int i = 1; i < cityAdapter.getCount(); i++) {
            WeatherResponse cityItem = cityAdapter.getItem(i);
            String cityItemJSON = gson.toJson(cityItem, WeatherResponse.class);
            editor.putString(String.format(CITY_ITEM, i), cityItemJSON);
        }
        editor.apply();
    }

    public List <WeatherResponse> restoreCities() {
        List<WeatherResponse> citiesList = new ArrayList<>();
        int cityAmount = preferences.getInt(CITIES_AMOUNT, 0);
        for (int i = 1; i <= cityAmount; i++) {
            String cityItemJSON = preferences.getString(String.format(CITY_ITEM, i), "");
            WeatherResponse cityItem = gson.fromJson(cityItemJSON, WeatherResponse.class);
            citiesList.add(cityItem);
        }
        return citiesList;
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

//    public void storeNewCity(City city) {
//        storeCity(city, citiesAmount, false);
//        increaseCitiesAmount();
//    }
//
//    public void storeExistedCity(City city, int position) {
//        storeCity(city, position, city.isActive());
//    }
//
//    private void storeCity(City city, int position, boolean isActive) {
//        JSONObject cityJSON = new JSONObject();
//        System.out.println("Name" +city.getCityName());
//        try {
//            cityJSON.put(CITY_NAME, city.getCityName());
//            cityJSON.put(CITY_ID, city.getCityId());
//            cityJSON.put(COUNTRY, city.getCountry());
//            cityJSON.put(IS_ACTIVE, isActive);
//
//            cityJSON.put(WEATHER_WEATHER_NAME, city.getWeather().getWeatherName());
//            cityJSON.put(WEATHER_TEMPERATURE, city.getWeather().getTemperature());
//            cityJSON.put(WEATHER_HUMIDITY, city.getWeather().getHumidity());
//            cityJSON.put(WEATHER_PRESSURE, city.getWeather().getPressure());
//            cityJSON.put(WEATHER_WIND_SPEED, city.getWeather().getWindSpeed());
//        } catch (JSONException ex) {
//            Log.d(TAG, ex.getMessage());
//        }
//        editor.putString(String.format(CITY_ITEM, position), cityJSON.toString());
//        editor.apply();
//    }
//
////    public City restoreCity(int position) {
////        String cityName, county;
////        JSONObject storedCity;
////        boolean isActive;
////        int cityId;
////
////        try {
////            storedCity = getCityJSON(position);
////            cityName = storedCity.getString(CITY_NAME);
////            cityId = storedCity.getInt(CITY_ID);
////            county = storedCity.getString(COUNTRY);
////            isActive = storedCity.getBoolean(IS_ACTIVE);
////            return new City(cityName, cityId, county, isActive);
////        } catch(JSONException ex){
////            Log.d(TAG, ex.getMessage());
////        } catch (NullPointerException ex) {
////            Log.d(TAG, ex.getMessage());
////        }
////        return null;
////    }
//
//    public void setCityActive(int position, boolean isActive) {
//        JSONObject cityJSON;
//        try {
//            cityJSON = getCityJSON(position);
//            cityJSON.put(IS_ACTIVE, isActive);
//            editor.putString(String.format(CITY_ITEM, position), cityJSON.toString());
//            editor.apply();
//
//            if (isActive) {
//                CitiesFragment.activeCityId = cityJSON.getInt(CITY_ID);
//                CitiesFragment.activeCityName = cityJSON.getString(CITY_NAME);
//                System.out.println(CitiesFragment.activeCityId);
//            }
//        } catch (JSONException ex) {
//            Log.d(TAG, ex.getMessage());
//        } catch (NullPointerException ex) {
//            Log.d(TAG, ex.getMessage());
//        }
//    }
//
////    public void deleteCity(int position) {
////        System.out.println("Deleting city # " +position);
////        System.out.println(preferences.getString(String.format(CITY_ITEM, position), "null"));
////        editor.remove(String.format(CITY_ITEM, position));
////        System.out.println(preferencesAPI.restoreCity(position).toString());
////        for (int i = position + 1; i < citiesAmount; i++) {
////            String cityString = preferences.getString(String.format(CITY_ITEM, i), "null");
////            editor.putString(String.format(CITY_ITEM, i-1), cityString);
////        }
////        editor.apply();
////        decreaseCitiesAmount();
////
////        System.out.println("Deleting");
////        for (int i = 0; i < 5; i++) {
////            String cityString = preferences.getString(String.format(CITY_ITEM, i), "null");
////            System.out.println(cityString);
////        }
////    }
//
//    public int restoreCitiesAmount() {
//        System.out.println("restoreCitiesAmount");
//        System.out.println(preferences.getInt(CITIES_AMOUNT, 0));
//        for (int i = 0; i < 5; i++) {
//            System.out.println("this");
//            System.out.println(preferences.getString(String.format(CITY_ITEM, i), ""));
//        }
//        return preferences.getInt(CITIES_AMOUNT, 0);
//    }
//
//    public void increaseCitiesAmount() {
//        ++citiesAmount;
//        editor.putInt(CITIES_AMOUNT, citiesAmount);
//        editor.apply();
//    }
//
//    public void decreaseCitiesAmount() {
//        --citiesAmount;
//        editor.putInt(CITIES_AMOUNT, citiesAmount);
//        editor.apply();
//    }
//
//    public void clearFile() {
//        citiesAmount = 0;
//        editor.clear();
//        editor.apply();
//    }
//
//    private JSONObject getCityJSON(int position) {
//        try {
//            String storedCity = preferences.getString(String.format(CITY_ITEM, position), "null");
//            return new JSONObject(storedCity);
//        } catch (JSONException ex) {
//            Log.d(TAG, ex.getMessage());
//        }
//        return null;
//    }
//
//    public int restoreCityId(int position) {
//        int cityId = 0;
//        try {
//            cityId = getCityJSON(position).getInt(CITY_ID);
//        } catch(JSONException ex) {
//            Log.d(TAG, ex.getMessage());
//        } catch (NullPointerException ex) {
//            Log.d(TAG, ex.getMessage());
//        }
//        return cityId;
//    }
}
