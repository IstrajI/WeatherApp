package nikitin.weatherapp.com.weatherapptest3.Server;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nikitin.weatherapp.com.weatherapptest3.City;
import nikitin.weatherapp.com.weatherapptest3.MainActivity;
import nikitin.weatherapp.com.weatherapptest3.Weather;

/**
 * Created by Влад on 05.07.2016.
 */
public class JSONParser {
    static JSONParser parser;
    private final String TAG = "JSONParser";

    public static JSONParser getInstance () {
        if (parser == null) {
            parser = new JSONParser();
        }
        return parser;
    }

//    public ArrayList<City> getCitiesList(String response) {
//        ArrayList<City> citiesList = new ArrayList<>();
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("list");
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                String cityName = jsonArray.getJSONObject(i).getString("name");
//                int cityId = jsonArray.getJSONObject(i).getInt("id");
//                String country = jsonArray.getJSONObject(i).getJSONObject("sys").getString("country");
//                City city = new City(cityName, cityId, country, false);
//                citiesList.add(city);
//            }
//        } catch (JSONException ex) {
//            Log.d(TAG, ex.getMessage());
//        }
//        System.out.println(citiesList);
//        return citiesList;
//    }

    public City parseCity(String response) {
        City city = new City();
        try {
            JSONObject jsonObject = new JSONObject(response);
            city.setCityId(jsonObject.getInt("id"));
            city.setCityName(jsonObject.getString("name"));
            city.setCountry(jsonObject.getJSONObject("sys").getString("country"));
            city.setActive(false);

            Weather weather = parseWeather(response);
            city.setWeather(weather);
        } catch (JSONException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return city;
    }

    public Weather parseWeather(String response) {
        Weather weather = new Weather();
        try {
            JSONObject jsonObject = new JSONObject(response);
            weather.setTemperature(convertTemperatureToCelsius(jsonObject.getJSONObject("main").getInt("temp")));
            weather.setWeatherName(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
            weather.setHumidity(jsonObject.getJSONObject("main").getInt("humidity"));
            weather.setPressure(jsonObject.getJSONObject("main").getInt("pressure"));
            weather.setWindSpeed(jsonObject.getJSONObject("wind").getInt("speed"));
        } catch (JSONException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return weather;
    }

    public boolean cityNotFound (String response) {
        int errorCode = 404;
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getInt("cod") == errorCode) {
                return true;
            }
            return false;
        } catch (JSONException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return true;
    }

    public int convertTemperatureToCelsius(int kelvinTemperature) {
        double KELVIN_DIFFERENCE = 273.15;
        return (int)(kelvinTemperature - KELVIN_DIFFERENCE);
    }
}
