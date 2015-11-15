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

    String Url;
    String internshiUrl = "http://reason.kzoo.edu/ccd/internships/";
    String externshipUrl = "http://reason.kzoo.edu/ccd/programs/";
    TextView showBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button internshipBtn = (Button) findViewById(R.id.internshipBtn);
        Button externshipBtn = (Button) findViewById(R.id.externshipBtn);

        showBox = (TextView) findViewById(R.id.showBox);
        internshipBtn.setOnClickListener(this);
        externshipBtn.setOnClickListener(this);
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
        new _JSOUP().execute();    }

    private void internshipBtnClick()
    {
        Url = internshiUrl;
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
        }
    }
}
