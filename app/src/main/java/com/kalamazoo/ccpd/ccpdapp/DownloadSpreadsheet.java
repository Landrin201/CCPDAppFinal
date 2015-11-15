package com.kalamazoo.ccpd.ccpdapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
    Thanks to http://www.telerik.com/blogs/google-spreadsheet-as-data-source-android for the awesome
    tutorial on how to use a google spreadsheet as a database. Much of my code is based on or comes from
    their implementation.
 */

/**
 * Created by Work on 11/3/2015.
 */
public class DownloadSpreadsheet extends AsyncTask<String, Void, String>
{
   //Instance variables
   AsyncResult callback;

    //Constructor
    public DownloadSpreadsheet(AsyncResult cb)
    {
        callback = cb;
    }

    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call in FAQ activity: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to download the requested page.";
        }
    }

    /**
     * onPostExecute displays the results of the AsyncTask.
     * @param result the result as a string
     */
    @Override
    protected void onPostExecute(String result) {
        // remove the unnecessary parts from the response and construct a JSON
        int start = result.indexOf("{", result.indexOf("{") + 1);
        int end = result.lastIndexOf("}");
        String jsonResponse = result.substring(start, end);
        try {
            JSONObject table = new JSONObject(jsonResponse);
            callback.onResult(table);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String downloadUrl(String urlString) throws IOException {
        InputStream is = null;

        try {
            //Get the url of the spreadsheet
            URL url = new URL(urlString);
            //Try to connect to it
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set how long the connection will attempt before it times out
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            //We want to get information, so the method here needs to be a get
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Start the query
            conn.connect();
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();

            String contentAsString = convertStreamToString(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
