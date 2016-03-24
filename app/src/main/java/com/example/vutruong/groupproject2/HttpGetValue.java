package com.example.vutruong.groupproject2;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by VuTruong on 23/03/2016.
 */
public class HttpGetValue extends AsyncTask<String, Void, String> {

    private SendingDataToFragment mData;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;

    public interface SendingDataToFragment {
        void sendData(String str);
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder builder = new StringBuilder();
        String result = "";
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }
            String finalJson = builder.toString();
            JSONObject jsonObject = new JSONObject(finalJson);
            result = jsonObject.getString("content");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mData.sendData(s);
    }

    public void setDataInstance(SendingDataToFragment interfaceInstance) {
        mData = interfaceInstance;
    }

}
