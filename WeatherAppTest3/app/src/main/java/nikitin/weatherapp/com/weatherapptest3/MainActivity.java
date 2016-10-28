package nikitin.weatherapp.com.weatherapptest3;

import android.app.ActivityManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nikitin.weatherapp.com.weatherapptest3.Fragments.CitiesFragment;
import nikitin.weatherapp.com.weatherapptest3.Fragments.TabsPagerAdapter;
import nikitin.weatherapp.com.weatherapptest3.Server.ResponseCallback;
import nikitin.weatherapp.com.weatherapptest3.Server.WeatherAPI;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        ImageView iv = (ImageView)findViewById(R.id.mainActivityImage);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap bm = decodeSampledBitmapFromResource(getResources(), R.drawable.main_background3, 1080, 1920);
        iv.setImageBitmap(bm);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setTitle("Choose city");

                    ImageView firstTab = (ImageView)findViewById(R.id.first_tab);
                    firstTab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle2_active));

                    ImageView secondTab = (ImageView)findViewById(R.id.second_tab);
                    secondTab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle2_deactive));
                } else if (position == 1){
                    setTitle(CitiesFragment.activeCityName);

                    ImageView firstTab = (ImageView)findViewById(R.id.first_tab);
                    firstTab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle2_deactive));

                    ImageView secondTab = (ImageView)findViewById(R.id.second_tab);
                    secondTab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle2_active));
                }
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Cursor cursor = CitySuggestionProvider.cursor;
            cursor.move(0);
            int cityId = cursor.getInt(cursor.getColumnIndex(CitySuggestionProvider.SUGGEST_COLUMN_CITY_ID));
            TabsPagerAdapter.citiesFragment.addNewCity(cityId);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.WHITE);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, MainActivity.class)));
        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        options.inPreferredConfig =  Bitmap.Config.RGB_565;

        Bitmap bm = BitmapFactory.decodeResource(res, resId, options);
        return bm;
    }

    public static int calculateInSampleSize(
        BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        System.out.println("PARAMETRS " +height +" " +width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            System.out.println("InSampleSize " +inSampleSize +" H" +options.outHeight +"W " +options.outWidth +" Mime" +options.outMimeType);
        }
        return inSampleSize;
    }
}
