package com.example.tripchoice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.lang.String;

public class ResMainActivity extends AppCompatActivity {
    private ArrayList<ResInfo> resList = new ArrayList<>() ;
    private ResInfo data;
    private RecyclerView rcv;
    private RcvResAdapter rcvRes;
    private String region;
    private int getCnt = 20;
    private String Busan = "http://apis.data.go.kr/6260000/FoodService/getFoodKr?serviceKey=bjYOGF%2BdlPWgSKwCYHYiF4dSy73dJJJdVWE%2BgKM%2BK5jqTZcrV7fZjIaVmk5R%2Fc5q2%2BR%2F50PQ%2FM0pqh0TyzWYDw%3D%3D&pageNo=1&numOfRows=";
    private String CN = "https://tour.chungnam.go.kr/_prog/openapi/?func=food&start=0&end=";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("맛집정보");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        region = intent.getExtras().getString("region");

        if(region.equals("Busan")){
            readXML(Busan);
        } else if (region.equals("CN")){
            readXML(CN);
        }

        rcv = findViewById(R.id.res_rcv);

        rcvRes = new RcvResAdapter(this, resList);

        while (rcvRes.getItemCount() != getCnt) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        rcv.setAdapter(rcvRes);

        rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcv.setHasFixedSize(true);

        rcvRes.notifyDataSetChanged();

    }

    void readXML(String ul){
        try {
            StringBuilder urlBuilder = new StringBuilder(ul + getCnt);
            URL url = new URL(urlBuilder.toString());
            getResList getreslist = new getResList();
            getreslist.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private class getResList extends AsyncTask<URL, Void, String> {

        StringBuffer buffer = new StringBuffer();

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            try {
                InputStream is = url.openStream();

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type","application/json");
                BufferedReader rd;
                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300){
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder sb = new StringBuilder();
                String line = "";

                while((line = rd.readLine()) != null)
                    sb.append(line);

                rd.close();
                conn.disconnect();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(is,"UTF-8");

                String name = "", addr = "", menu = "", call = "", desc = "";
                Bitmap bitmap = null;
                int eventType = xpp.getEventType();

                ResInfo rInfo = null;
                String tag;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:


                            if (xpp.getName().equals("item")){
                                rInfo = new ResInfo();
                            }

                            if (xpp.getName().equals("nm") || xpp.getName().equals("MAIN_TITLE")) {
                                xpp.next();
                                name = xpp.getText();
                                if(rInfo != null) rInfo.setName(name);

                            } else if (xpp.getName().equals("tel") || xpp.getName().equals("CNTCT_TEL")) {
                                xpp.next();
                                call = xpp.getText();
                                if(rInfo != null) rInfo.setCall(call);

                            } else if (xpp.getName().equals("list_img") || xpp.getName().equals("MAIN_IMG_THUMB")) {
                                xpp.next();
                                try {

                                    URL image = new URL((xpp.getText()));
                                    URLConnection connection = image.openConnection();
                                    connection.connect();

                                    int size = connection.getContentLength();
                                    BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream(), size);
                                    bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if(rInfo != null) rInfo.setBitmap(bitmap);

                            } else if (xpp.getName().equals("REPRSNT_FOOD_NM") || xpp.getName().equals("RPRSNTV_MENU")) {
                                xpp.next();
                                menu = xpp.getText();
                                if(rInfo != null) rInfo.setMenu(menu);

                            } else if (xpp.getName().equals("addr") || xpp.getName().equals("ADDR1")) {
                                xpp.next();
                                addr = xpp.getText();
                                if(rInfo != null) rInfo.setAddr(addr);
                            } else if (xpp.getName().equals("ITEMCNTNTS")) {
                                xpp.next();
                                desc = xpp.getText();
                                if(rInfo != null) rInfo.setDesc(desc);
                            }

                            break;
                        case XmlPullParser.TEXT:

                            break;

                        case XmlPullParser.END_TAG:
                            tag = xpp.getName();

                            if(tag.equals("item")) {
                                resList.add(rInfo);
                                buffer.append("\n");
                            }

                            break;
                    }

                    eventType = xpp.next();
                }
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return "파싱 개수";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Toast.makeText(ResMainActivity.this, s+":"+resList.size(), Toast.LENGTH_SHORT).show();
        }
    }
}