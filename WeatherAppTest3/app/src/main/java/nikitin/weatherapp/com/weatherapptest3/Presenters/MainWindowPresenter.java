package nikitin.weatherapp.com.weatherapptest3.Presenters;

<<<<<<< HEAD
import nikitin.weatherapp.com.weatherapptest3.View.CitiesFragment;
import nikitin.weatherapp.com.weatherapptest3.View.MainWindowFragment;
import nikitin.weatherapp.com.weatherapptest3.Model.WeatherModel.WeatherResponse;
=======
import android.graphics.drawable.Drawable;

import java.sql.SQLOutput;

import nikitin.weatherapp.com.weatherapptest3.CityAdapter;
import nikitin.weatherapp.com.weatherapptest3.Fragments.CitiesFragment;
import nikitin.weatherapp.com.weatherapptest3.Fragments.MainWindowFragment;
import nikitin.weatherapp.com.weatherapptest3.model.WeatherResponse;
>>>>>>> 25e2c86627058a9b472112f5875a1d5c15e91bdb
import nikitin.weatherapp.com.weatherapptest3.rest.OpenWeatherMapAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Влад on 15.10.2016.
 */
public class MainWindowPresenter {
    static MainWindowFragment view;
    private static int activeCityId;

    public MainWindowPresenter(MainWindowFragment view) {
        this.view = view;
    }
    public void updateWeather(int activeCityId) {
        System.out.println("activeCityId" + activeCityId);
        OpenWeatherMapAPI openWeatherMapAPI = OpenWeatherMapAPI.getInstance();
        openWeatherMapAPI.getWeatherByCityId(activeCityId, new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                response.body().setData(CitiesFragment.getInstance().getPresenter().convertToCelcium(response.body().getData()));
                view.applyWeather(response.body());
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                System.out.println("error");
                t.printStackTrace();
            }
        });
    }
}
