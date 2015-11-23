package com.kalamazoo.ccpd.ccpdapp;

        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import org.jsoup.Jsoup;
        import org.jsoup.select.Elements;

        import java.io.IOException;

public class Info extends AppCompatActivity implements View.OnClickListener{

    Button internshipBtn,externshipBtn, pdiBtn, employerBtn, alumniBtn, parentBtn, aboutBtn;
    String Url;
    String internshipUrl = "http://reason.kzoo.edu/ccd/internships/";
    String externshipUrl = "http://reason.kzoo.edu/ccd/programs/";
    String pdiUrl ="http://reason.kzoo.edu/ccd/pdi/";
    String employerUrl ="http://reason.kzoo.edu/ccd/employers/";
    String alumniUrl ="http://reason.kzoo.edu/ccd/alumni/";
    String parentUrl ="http://reason.kzoo.edu/ccd/parents/";
    String aboutUrl ="http://reason.kzoo.edu/ccd/aboutus/";
    TextView showBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        internshipBtn = (Button) findViewById(R.id.internshipBtn);
        externshipBtn = (Button) findViewById(R.id.externshipBtn);
        pdiBtn = (Button) findViewById(R.id.internshipBtn);
        employerBtn = (Button) findViewById(R.id.externshipBtn);
        alumniBtn = (Button) findViewById(R.id.internshipBtn);
        parentBtn = (Button) findViewById(R.id.externshipBtn);
        aboutBtn = (Button) findViewById(R.id.internshipBtn);

        showBox = (TextView) findViewById(R.id.showBox);

        internshipBtn.setOnClickListener(this);
        externshipBtn.setOnClickListener(this);
        pdiBtn.setOnClickListener(this);
        employerBtn.setOnClickListener(this);
        alumniBtn.setOnClickListener(this);
        parentBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
    }

    public class _JSOUP extends AsyncTask<Void, Void, Void>{
        ProgressDialog dialog;
        String message = "";

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(Info.this);
            dialog.setMessage("Loading....");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                org.jsoup.nodes.Document document = Jsoup.connect(Url).get();
                Elements element = document.select("div[class=contentMain]");
                message = element.text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPreExecute();
            dialog.dismiss();
            showBox.setText("" + message);

        }

    }


    private void externshipBtnClick()
    {
        Url = externshipUrl;
        new _JSOUP().execute();
    }

    private void internshipBtnClick()
    {
        Url = internshipUrl;
        new _JSOUP().execute();
    }

    private void pdiBtnClick()
    {
        Url = pdiUrl;
        new _JSOUP().execute();
    }

    private void employerBtnClick()
    {
        Url = employerUrl;
        new _JSOUP().execute();
    }

    private void alumniBtnClick()
    {
        Url = alumniUrl;
        new _JSOUP().execute();
    }

    private void parentBtnClick()
    {
        Url = parentUrl;
        new _JSOUP().execute();
    }

    private void aboutBtnClick()
    {
        Url = aboutUrl;
        new _JSOUP().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.externshipBtn:
                externshipBtnClick();
                break;
            case R.id.internshipBtn:
                internshipBtnClick();
                break;
            case R.id.pdiBtn:
                pdiBtnClick();
                break;
            case R.id.employerBtn:
                employerBtnClick();
                break;
            case R.id.alumniBtn:
                alumniBtnClick();
                break;
            case R.id.parentBtn:
                parentBtnClick();
                break;
            case R.id.aboutBtn:
                aboutBtnClick();
                break;
        }
    }
}
