package com.kalamazoo.ccpd.ccpdapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * RequestAppointmentTime Class
 * Created by Raphael Wieland
 * 11/23/2015
 */

public class RequestAppointmentTime extends AppCompatActivity implements View.OnClickListener {
    //set variables
    final Calendar newCalendar = Calendar.getInstance();
    SharedPreferences newID;
    String kID;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private EditText selectDate, selectTime, enterMessage;
    private Switch addToCalendar;
    private Switch ccMeSwitch;
    private boolean correctDate = false;
    private boolean correctTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_appointment_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set variables to their proper views
        findViewsById();
        //set date and time fields
        setDateField();
        setTimeField();


        // Floating action button used to submit form
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set variables
                String message = enterMessage.getText().toString();
                String date = selectDate.getText().toString();
                String time = selectTime.getText().toString();
                //if the user correctly enters submission information send an email
                if ((correctTime && correctDate) && !message.equals("")) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "career@kzoo.edu", null));
                    //if user wants to be copied in email do that
                    if (ccMeSwitch.isChecked()) {
                        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{kID + "@kzoo.edu"});
                    }
                    //add extras to message
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New appointment requested by: " + kID + " on " + date + " at " + time);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "A new appointment has been requested by" + "\n" + kID +
                            "\nFor:\n" + date +
                            "\nAt:\n" + time +
                            "\nConcerning:\n" + message);
                    //send email
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    //add event to calendar if user wishes
                    if (addToCalendar.isChecked()) {
                        //create new calendar intent
                        Intent calIntent = new Intent(Intent.ACTION_EDIT);
                        calIntent.setType("vnd.android.cursor.item/event");
                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, newCalendar.getTimeInMillis());
                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, newCalendar.getTimeInMillis() + 30 * 60 * 1000);
                        calIntent.putExtra(CalendarContract.Events.TITLE, "Appointment with CCPD staff");
                        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Kalamazoo College Center for Career and Professional Development");
                        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Concerning: " + message);
                        startActivity(calIntent);
                    }
                } else {//toast if the user did not correctly fill out submission
                    Toast.makeText(getApplicationContext(), "Incomplete or invalid appointment request. Please make sure you have completed all fields correctly", Toast.LENGTH_LONG).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*
        A method used to link variables to their view respective view
     */
    private void findViewsById() {
        selectDate = (EditText) findViewById(R.id.dateSelect);
        selectDate.setInputType(InputType.TYPE_NULL);
        selectDate.requestFocus();


        selectTime = (EditText) findViewById(R.id.timeSelect);
        selectTime.setInputType(InputType.TYPE_NULL);

        enterMessage = (EditText) findViewById(R.id.message);

        addToCalendar = (Switch) findViewById(R.id.addToCalendar);
        ccMeSwitch = (Switch) findViewById(R.id.ccme);

        newID = getSharedPreferences("K-ID", Context.MODE_PRIVATE);
        kID = newID.getString("ID", "No ID entered");
    }

    /*
        A method that sets the date field
     */
    private void setDateField() {
        //Create a new calendar object used to get current time
        selectDate.setOnClickListener(this);

        //use datePickerDialog to get requested date
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newCalendar.set(year, monthOfYear, dayOfMonth);
                //if it is on a day the CCPD is closed set message
                if (newCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || newCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    selectDate.setText("The CCPD is not open on the weekend.");
                    correctDate = false;
                } else {
                    //set the selected date to the date view
                    selectDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                    correctDate = true;
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    /*
            A method that sets the time field
    */
    private void setTimeField() {
        selectTime.setOnClickListener(this);
        //create new timePickerDialog
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            //set time
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectTime.setText(formatTime(hourOfDay, minute));
                newCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newCalendar.set(Calendar.MINUTE, minute);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), false);
    }

    /**
     * A method that formats the time field
     * @param hour
     * @param minute
     * @return a string containing the formatted time
     */
    public String formatTime(int hour, int minute) {
        int hourOfDay = hour;
        String dayOrNight = "AM";
        String leadingZero = "";
        //if not during business hours set error message
        if (hourOfDay < 8 || hourOfDay > 15) {
            correctTime = false;
            return "Please enter a time during business hours (8-4)";
        } else {
            //set to PM if after noon
            if (hourOfDay > 11) {
                dayOrNight = "PM";
                if (hourOfDay > 12) {
                    hourOfDay = hourOfDay - 12;
                }
            }
            //add leading zero if it is a single digit minute
            if (minute < 10) {
                leadingZero = "0";
            }
            correctTime = true;
            return hourOfDay + ":" + leadingZero + minute + dayOrNight;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == selectDate) {
            datePickerDialog.show();
            selectTime.requestFocus();
        } else if (view == selectTime) {
            timePickerDialog.show();
        }
    }

    /**
     * public boolean onCreateOptionsMenu(Menu menu) {
     * // Inflate the menu; this adds items to the action bar if it is present.
     * getMenuInflater().inflate(R.menu.menu_faqsubmission, menu);
     * return true;
     * }
     **/

    /**
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
    }*/

}

