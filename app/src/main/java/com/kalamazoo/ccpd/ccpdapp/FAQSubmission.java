package com.kalamazoo.ccpd.ccpdapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**FAQSubission Class
 * Created by Raphael Wieland
 * 11/23/2015
 */
public class FAQSubmission extends AppCompatActivity {

    //Declare variables
    private String kID;
    private Switch ccMeSwitch;
    private EditText enterMessage;
    private String message;
    SharedPreferences newID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqsubmission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //assign views to variables
                ccMeSwitch = (Switch) findViewById(R.id.ccme);
                enterMessage = (EditText) findViewById(R.id.emailcontent);
                message = enterMessage.getText().toString();
                //grab k-id from shared preferences
                newID = getSharedPreferences("K-ID", Context.MODE_PRIVATE);
                kID = newID.getString("ID", "No ID entered");
                //send message
                if (!message.equals("")) {
                    //create new email intent
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "career@kzoo.edu", null));
                    //check if the user wants to be copied in email
                    if (ccMeSwitch.isChecked()) {
                        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{kID + "@kzoo.edu"});
                    }
                    //put information into email
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Question Submitted by " + kID);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Question:\n" + message + "\nSubmitted by:\n" + kID);
                    //start activity
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                } else {//send error toast if the user did not correctly complete submission form
                    Toast.makeText(getApplicationContext(), "Question empty! Please enter your question in the field provided", Toast.LENGTH_LONG).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * @Override public boolean onCreateOptionsMenu(Menu menu) {
     * // Inflate the menu; this adds items to the action bar if it is present.
     * getMenuInflater().inflate(R.menu.menu_faqsubmission, menu);
     * return true;
     * }


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
    }**/
}
