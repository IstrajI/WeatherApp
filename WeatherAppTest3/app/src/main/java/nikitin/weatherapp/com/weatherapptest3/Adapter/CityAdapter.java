package nikitin.weatherapp.com.weatherapptest3.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import nikitin.weatherapp.com.weatherapptest3.Model.WeatherModel.WeatherResponse;
import nikitin.weatherapp.com.weatherapptest3.R;
import nikitin.weatherapp.com.weatherapptest3.View.CitiesFragment;
import nikitin.weatherapp.com.weatherapptest3.View.TabsPagerAdapter;
import nikitin.weatherapp.com.weatherapptest3.Presenters.CitiesPresenter;
/**
 * Created by Влад on 28.07.2016.
 */
public class CityAdapter extends ArrayAdapter<WeatherResponse> {
    public static int selectedPosition = -1;
    Activity mainActivity;
    CitiesPresenter citiesPresenter;
    public static CityAdapter cityAdapter;

    final int TYPE_ITEM_CITY = 0;
    final int TYPE_ITEM_LOCATION = 1;
    final int TYPES_AMOUNT = 2;

    public CityAdapter(Context context, ArrayList<WeatherResponse> cities) {
        super(context, 0, cities);
        this.mainActivity = (Activity)context;
    }

    public CityAdapter(Context context, CitiesPresenter citiesPresenter, ArrayList<WeatherResponse> cities) {
        super(context, 0, cities);
        this.citiesPresenter = citiesPresenter;
        this.mainActivity = (Activity)context;
    }

    public static CityAdapter getInstance(Context context, ArrayList<WeatherResponse> cities) {
        if (cityAdapter == null) {
            cityAdapter = new CityAdapter(context, cities);
        }
        return cityAdapter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            switch(type) {
                case TYPE_ITEM_CITY:
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_city, parent, false);
                    break;

                case TYPE_ITEM_LOCATION:
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_city_2, parent, false);
                    break;
            }
        }
        TextView cityName = (TextView) convertView.findViewById(R.id.cityName);
        TextView cityShortWeather = (TextView) convertView.findViewById(R.id.cityShotWeather);

        WeatherResponse city = this.getItem(position);
        cityName.setText(city.getName());
        cityShortWeather.setText(city.getData().getTemp() + ", " + city.getWeathers().get(0).getMain());
        createRadioButton(convertView, position, false);
        if (type == TYPE_ITEM_CITY) {
            createDeleteButton(convertView, position);
        } else if (type == TYPE_ITEM_LOCATION) {
            createFindLocationButton(convertView, position);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_ITEM_LOCATION : TYPE_ITEM_CITY;
    }

    @Override
    public int getViewTypeCount() {
        return TYPES_AMOUNT;
    }

    public int getActiveCityId() {
        if (selectedPosition != -1) {
            return getItem(selectedPosition).getId();
        }
        return 0;
    }

    private void createRadioButton(View convertView, final int position, boolean isChecked) {
        RadioButton r = (RadioButton)convertView.findViewById(R.id.activeCity);
        r.setChecked(position == selectedPosition);
        r.setTag(position);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = (Integer) view.getTag();
                if (getActiveCityId() == 0) {
                    notifyDataSetChanged();
                    return;
                }
                TabsPagerAdapter.mainWindowFragment.updateWeather(getActiveCityId());
                notifyDataSetChanged();
            }
        });
    }

    private void createDeleteButton(View convertView, int position) {
        Button deleteButton = (Button)convertView.findViewById(R.id.deleteButton);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CitiesFragment.getInstance().deleteCity((int) view.getTag());
            }
        });
    }

    private void createFindLocationButton(View convertView, int position) {
        Button findLocationButton = (Button)convertView.findViewById(R.id.findLocationButton);
        findLocationButton.setTag(position);
        findLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CitiesFragment.getInstance().getCityByGPS();
            }
        });
    }
}
