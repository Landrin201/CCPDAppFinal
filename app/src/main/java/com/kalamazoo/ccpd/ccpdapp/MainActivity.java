package com.kalamazoo.ccpd.ccpdapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstRun();
        WebView wv = (WebView) findViewById(R.id.mainWebView);
        wv.loadUrl("http://reason.kzoo.edu/ccd/events/");
    }

    private void firstRun(){
        SharedPreferences prefs = getSharedPreferences("firstLoad", Context.MODE_PRIVATE);
        boolean haveWeShownSplash = prefs.getBoolean("HaveShownSplash", false);
        if (!haveWeShownSplash) {
            Intent intent = new Intent(this, Splash.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, Splash.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onFAQButtonClick(View view)
    {
        Intent intent = new Intent(this, FAQListActivity.class);
        startActivity(intent);
    }

    public void onAppointmentButtonClick(View view)
    {
        Intent intent = new Intent(this, RequestAppointmentTime.class);
        startActivity(intent);
    }

    public void onInfoButtonClick(View view)
    {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }
}
