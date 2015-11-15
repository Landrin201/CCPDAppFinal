package com.kalamazoo.ccpd.ccpdapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/*
Thanks to http://www.telerik.com/blogs/google-spreadsheet-as-data-source-android for the
extremly helpful tutorial on how to use google spreadsheets as databases in android. I used
his tutorial and code quite a bit in this project.
 */


public class FAQListActivity extends AppCompatActivity
{ //Begin class

    ListView listview;
    ArrayList<Question> questions = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    { //Begin onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqlist);

        //Instantiate instance variables
        listview = (ListView) findViewById(R.id.faqListView);

        //Ensure an active internet connection
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //Check to ensure that the app is connected to the internet. If it is, we are fine.
        new DownloadSpreadsheet
                (
                        new AsyncResult() {
                            @Override
                            public void onResult(JSONObject object) {
                                Log.d("JSON Object", object.toString());
                                processJson(object);
                            }
                        }
                ).execute("https://spreadsheets.google.com/tq?key=1JvZVFWM3Emvgf2opoZI9fLanQJD9Hbkjb_I5BhPsrwU");
    } //End onCreate

    public void onSubmitFAQButtonClick(View view)
    {
        Intent intent = new Intent(this, FAQSubmission.class);
        startActivity(intent);
    }


    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); r++) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                int id = columns.getJSONObject(0).getInt("v");
                String qu = columns.getJSONObject(1).getString("v");
                String answer = columns.getJSONObject(2).getString("v");

                Question question = new Question(id, qu, answer);
                questions.add(question);

            }

            Log.d("An Answer", questions.get(1).getAnswer());
            //Use the adapter written earlier to make the list view look good
            final TextAdapter adapter = new TextAdapter(this, questions);
            listview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
} //End class
