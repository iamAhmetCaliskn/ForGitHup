package com.example.jsoupilewebdenverialma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button getData;
    private ListView list;
    private ProgressDialog dialog;
    private ArrayList dataList= new ArrayList<>();

    private static final String URL = "https://bigpara.hurriyet.com.tr/doviz/";

    private void init(){
        getData=findViewById(R.id.getData);
        list=findViewById(R.id.list);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new model().execute();
            }
        });


    }

    private class model extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog= new ProgressDialog(MainActivity.this);
            dialog.setTitle("Veri Çekiliyor");
            dialog.setIndeterminate(false);
            dialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {


            try {
                dataList.clear();
                Document doc = Jsoup.connect(URL).timeout(6000).get();

                Elements paraİsimleri = doc.select("div.tBody ul li h3");

                for(int a=0;a<paraİsimleri.size();a++)
                {

                    dataList.add(paraİsimleri.get(a).text());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, dataList);
            list.setAdapter(adapter);
            dialog.dismiss();
        }
    }


}