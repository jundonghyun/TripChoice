package com.example.tripchoice.DongHyun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripchoice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserAddAttraction extends AppCompatActivity {
    private Spinner SelectArea_spinner, AdministrativeDivision_spinner;
    private EditText insertAttractionTitle_edittext, insertAttractionContent_edittext;
    private Button SelectImage_button, InsertButton_button;
    private ImageView firstImage_imageview, SecondImage_imageview, ThirdImage_imageview;
    private TextView selectgu;
    private InputStream is;
    private ArrayList<metropolitan> list = new ArrayList<metropolitan>();
    private ArrayList<metropolitan> gu = new ArrayList<metropolitan>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Inputdata inputdata;

    private SelectAreaSpinner selectAreaSpinner = new SelectAreaSpinner();
    private AdministrativeDivisionSpinner administrativeDivisionSpinner;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            if (firstImage_imageview.getDrawable() == null) {
                firstImage_imageview.setImageURI(uri);
                firstImage_imageview.setVisibility(View.VISIBLE);
            } else if (SecondImage_imageview.getDrawable() == null) {
                SecondImage_imageview.setImageURI(uri);
                SecondImage_imageview.setVisibility(View.VISIBLE);
            } else {
                ThirdImage_imageview.setImageURI(uri);
                ThirdImage_imageview.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_attraction);

        SelectArea_spinner = findViewById(R.id.SelectArea_spinner);
        AdministrativeDivision_spinner = findViewById(R.id.AdministrativeDivision_spinner);
        insertAttractionTitle_edittext = findViewById(R.id.insertAttractionTitle_edittext);
        insertAttractionContent_edittext = findViewById(R.id.insertAttractionContent_edittext);
        SelectImage_button = findViewById(R.id.SelectImage_button);
        InsertButton_button = findViewById(R.id.InsertButton_button);
        firstImage_imageview = findViewById(R.id.firstImage_imageview);
        SecondImage_imageview = findViewById(R.id.SecondImage_imageview);
        ThirdImage_imageview = findViewById(R.id.ThirdImage_imageview);
        selectgu = findViewById(R.id.selectgu);

        List<Map<String, Object>> Dlist = new ArrayList<Map<String,Object>>();
        ArrayList<String> getspinnerlist = new ArrayList<>();

        try {
            getspinnerlist = (ArrayList<String>) selectAreaSpinner.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.spinner_item, getspinnerlist);
        SelectArea_spinner.setAdapter(adapter);
        SelectArea_spinner.setSelection(0);

        SelectArea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gu.clear();
                int i = parent.getSelectedItemPosition(); //서울이 1이므로 i에다 +1시키고 다음 구군시 task로 넘김
                String num = String.valueOf(i+1);
                administrativeDivisionSpinner = new AdministrativeDivisionSpinner();
                ArrayList<String> getlist = new ArrayList<>();

                try {
                    getlist = (ArrayList<String>) administrativeDivisionSpinner.execute(num).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(getlist.isEmpty()){
                    AdministrativeDivision_spinner.setVisibility(View.INVISIBLE);
                    selectgu.setVisibility(View.INVISIBLE);
                }
                else{
                    AdministrativeDivision_spinner.setVisibility(View.VISIBLE);
                    selectgu.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplication(), R.layout.spinner_item, getlist);
                    AdministrativeDivision_spinner.setAdapter(adapter1);
                    AdministrativeDivision_spinner.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SelectImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/");
                startActivityForResult(intent, 200);
            }
        });



        InsertButton_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gugun = "";
                String dosi = (String) SelectArea_spinner.getSelectedItem();
                BitmapDrawable drawable;
                Bitmap bitmap;
                if(AdministrativeDivision_spinner.getSelectedItem() != null){
                    gugun = (String) AdministrativeDivision_spinner.getSelectedItem();
                    ArrayList<String> imagebitmap = new ArrayList<>();
                    if(firstImage_imageview.getDrawable() != null){
                        drawable = (BitmapDrawable) firstImage_imageview.getDrawable();
                        bitmap = drawable.getBitmap();
                        imagebitmap.add(getBase64String(bitmap));
                    }
                    if(SecondImage_imageview.getDrawable() != null){
                        drawable = (BitmapDrawable) SecondImage_imageview.getDrawable();
                        bitmap = drawable.getBitmap();
                        imagebitmap.add(getBase64String(bitmap));
                    }
                    if(ThirdImage_imageview.getDrawable() != null){
                        drawable = (BitmapDrawable) ThirdImage_imageview.getDrawable();
                        bitmap = drawable.getBitmap();
                        imagebitmap.add(getBase64String(bitmap));
                    }
                    inputdata = new Inputdata(insertAttractionTitle_edittext.getText().toString(), insertAttractionContent_edittext.getText().toString(), imagebitmap);

                    db.collection(dosi).document(gugun).set(inputdata);
                }
            }
        });
    }

    public String getBase64String(Bitmap bitmap) //bitmap to string
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }
    //byte[] decodedByteArray = Base64.decode("변환된 문자열", Base64.NO_WRAP);
    //Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    //string to bitmap




    private class AdministrativeDivisionSpinner extends AsyncTask<String, Integer ,ArrayList>{
        private ArrayList<String> gulist = new ArrayList<>();
        @Override
        protected ArrayList doInBackground(String... strings) {
            StringBuffer buffer = new StringBuffer();
            String areacode = strings[0];
            String mykey = "TFen3YAZ3qSYyKQmw689xVagwAxX%2Fd%2BrbZXBO09a2orzWmKd4qh7cnNxgpkVRmDtkHANsVzCNfNVl90x5ES%2Fkg%3D%3D";
            StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode");
            try{
                urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + mykey); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + areacode);
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("40", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰), ETC*/
                urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            URL url = null;
            try {
                url = new URL(urlBuilder.toString());
                Log.d("tag", url.toString());
                is = url.openStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Content-type", "application/json");
            try {
                Log.d("tag", "Response code: " + conn.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader rd = null;
            try {
                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            String line = "";
            while (true) {
                try {
                    if ((line = rd.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(line);
            }
            try {
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            line = sb.toString();
            Log.d("tag", line);
            conn.disconnect();

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                String name = "";

                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch(eventType){
                        case XmlPullParser.START_TAG:

                            if(xpp.getName().equals("name")){
                                buffer.append("시/도이름: ");
                                xpp.next();
                                name = xpp.getText();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            break;
                        case XmlPullParser.TEXT:

                            break;

                        case XmlPullParser.END_TAG:
                            tag = xpp.getName();

                            if(name != ""){
                                gu.add(new metropolitan(name));
                                name = "";
                            }

                            if(tag.equals("item")) buffer.append("\n");
                            break;
                    }

                    eventType = xpp.next();
                }

            } catch (XmlPullParserException | UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("buffer", String.valueOf(buffer));
            // 리스트에 제목,주소,사진,위도,경도 저장했으므로 recyclerview에 표시하면 끝

            for(int i = 0; i < gu.size(); i++){
                gulist.add(gu.get(i).name);
            }
            //publishProgress(spinnerlist);

            return gulist;
        }
    }

    private class SelectAreaSpinner extends AsyncTask {
        private ArrayList<String> spinnerlist = new ArrayList<>();
        @Override
        protected ArrayList doInBackground(Object... objects) {
            StringBuffer buffer = new StringBuffer();

            String mykey = "TFen3YAZ3qSYyKQmw689xVagwAxX%2Fd%2BrbZXBO09a2orzWmKd4qh7cnNxgpkVRmDtkHANsVzCNfNVl90x5ES%2Fkg%3D%3D";
            StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode");
            try{
                urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + mykey); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("20", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰), ETC*/
                urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            URL url = null;
            try {
                url = new URL(urlBuilder.toString());
                Log.d("tag", url.toString());
                is = url.openStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Content-type", "application/json");
            try {
                Log.d("tag", "Response code: " + conn.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader rd = null;
            try {
                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            String line = "";
            while (true) {
                try {
                    if ((line = rd.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(line);
            }
            try {
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            line = sb.toString();
            Log.d("tag", line);
            conn.disconnect();

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                String name = "";

                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch(eventType){
                        case XmlPullParser.START_TAG:

                            if(xpp.getName().equals("name")){
                                buffer.append("시/도이름: ");
                                xpp.next();
                                name = xpp.getText();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            break;
                        case XmlPullParser.TEXT:

                            break;

                        case XmlPullParser.END_TAG:
                            tag = xpp.getName();

                            if(name != ""){
                                list.add(new metropolitan(name));
                                name = "";
                            }

                            if(tag.equals("item")) buffer.append("\n");
                            break;
                    }

                    eventType = xpp.next();
                }

            } catch (XmlPullParserException | UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("buffer", String.valueOf(buffer));
            // 리스트에 제목,주소,사진,위도,경도 저장했으므로 recyclerview에 표시하면 끝

            for(int i = 0; i < list.size(); i++){
                spinnerlist.add(list.get(i).name);
            }
            publishProgress(spinnerlist);

            return spinnerlist;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {

        }
    }
}

class metropolitan{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public metropolitan(String n){
        this.name = n;
    }
}

class Inputdata{
    String title;
    String content;
    ArrayList<String> imagelist;

    public ArrayList<String> getImagelist() {
        return imagelist;
    }

    public void setImagelist(ArrayList<String> imagelist) {
        this.imagelist = imagelist;
    }

    public Inputdata(String t, String c, ArrayList<String> b){
        this.title = t;
        this.content = c;
        this.imagelist = b;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}