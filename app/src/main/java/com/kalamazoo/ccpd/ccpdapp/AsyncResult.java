package com.kalamazoo.ccpd.ccpdapp;

/*
    Thanks to http://www.telerik.com/blogs/google-spreadsheet-as-data-source-android for the
    great tutorial on how to use google spreadsheets as a database. This class comes directly from
    them- no need to reinvent the wheel.
 */

import org.json.JSONObject;

/**
 * Created by Work on 11/3/2015.
 */
interface AsyncResult
{
    void onResult(JSONObject object);
}
