package com.kalamazoo.ccpd.ccpdapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
    private EditText kID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        kID = (EditText)findViewById(R.id.KID);
        SharedPreferences prefs = getSharedPreferences("firstLoad", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean("HaveShownSplash", true);
        ed.commit();
    }

    public void onSubmitClick(View view){
        String enteredID = kID.getText().toString();
        boolean matches = enteredID.matches("[K|k]\\d{2}[a-zA-Z]{2}\\d{2}");
        if(enteredID.length()==7 && matches){
            SharedPreferences newID = getSharedPreferences("K-ID", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = newID.edit();
            ed.putString("ID", enteredID);
            ed.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Invalid K-ID. Please try again", Toast.LENGTH_LONG).show();
        }

    }
}
