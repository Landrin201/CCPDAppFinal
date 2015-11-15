package com.kalamazoo.ccpd.ccpdapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView wv = (WebView) findViewById(R.id.mainWebView);
        wv.loadUrl("http://reason.kzoo.edu/ccd/events/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
