package com.kalamazoo.ccpd.ccpdapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
This is an implementation of an ArrayAdaptor, a common method. I followed a tutorial on
how to use these, found in the links below. I didn't need to completely make this from scratch,
it is very common.

https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView

 */
/**
 * Takes a number of strings and places them into the questionlistlayout to format them properly.
 * Created by Marc Kuniansky on 11/3/2015.
 */
public class TextAdapter extends ArrayAdapter<Question>
{
    //Instance variables
    Context context;
    ArrayList<Question> questions;
    //Constructor
    public TextAdapter(Context con, ArrayList<Question> q)
    {
        super(con, 0, q);
        context=con;
        questions=q;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Get the question to be formatted
        Question question = questions.get(position);

        View view = convertView;
        if(view==null)
        {
            //If the view is empty, set it to the desired view
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.questionlistlayout, null);
        }

        //Get the text views for the question and answer
        TextView questionTV = (TextView) view.findViewById(R.id.question_text_view);
        TextView answerTV = (TextView) view.findViewById(R.id.answer_text_view);

        //Add text to the text views
        questionTV.setText(question.getQuestion());
        answerTV.setText(question.getAnswer());

        return view;
    }
}
