package nikitin.weatherapp.com.weatherapptest3.Server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Влад on 03.07.2016.
 */
public class GetTask extends AsyncTask<String, String, String> {

    final String TAG = "GetTask";
    String requestUrl;
    RestTaskCallback callback;

    GetTask(String url, RestTaskCallback callback) {
        requestUrl = url;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String output = "";
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
            System.out.println(output);
            connection.disconnect();

        } catch (MalformedURLException ex) {
            Log.e(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        }
        return output;
    }

    @Override
    protected void onPostExecute(String output) {
        callback.onTaskComplete(output);
        super.onPostExecute(output);
    }
}

abstract class RestTaskCallback{
    public abstract void onTaskComplete(String result);
}
