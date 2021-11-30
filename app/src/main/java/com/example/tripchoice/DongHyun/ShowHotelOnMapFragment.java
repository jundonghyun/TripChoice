package com.example.tripchoice.DongHyun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tripchoice.R;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ShowHotelOnMapFragment extends Fragment {
    private RecommendAttractionData data;
    private attraction attraction;
    private InputStream is;
    private LinearLayout view_tmap;
    private Context context;
    private ArrayList<attraction> list = new ArrayList<attraction>();
    private LinearLayout linearLayout;
    private SharedViewModel sharedViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_hotel_on_map, container, false);
        Bundle bundle = getArguments();
        data = (RecommendAttractionData) bundle.get("data");

        double latitude = 0, longitude = 0;

        String POIName = "";

        if(data != null){
            POIName = data.POIName;
            latitude = data.latitude;
            longitude = data.longitude;
        }

        Toast.makeText(container.getContext(), "관광지를 로딩중입니다...", Toast.LENGTH_SHORT).show();

        try {
            getRecommendAttraction(latitude, longitude, container.getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        view_tmap = (LinearLayout) view.findViewById(R.id.view_tmap);


        movepos(POIName, latitude, longitude);


        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.setList(list);

        return view;
    }

    private void movepos(String POIName, double latitude, double longitude){

        TMapView tmapView = new TMapView(getActivity());
        TMapMarkerItem mapMarkerItem1 = new TMapMarkerItem();
        TMapPoint tMapPoint1 = new TMapPoint(latitude, longitude);

        tmapView.setSKTMapApiKey("l7xxbbb12b800ea54d19bf35aa65f0c1980c");
        view_tmap.addView(tmapView);

        mapMarkerItem1.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        mapMarkerItem1.setTMapPoint( tMapPoint1 ); // 마커의 좌표 지정
        mapMarkerItem1.setName(POIName); // 마커의 타이틀 지정
        mapMarkerItem1.setCalloutTitle(POIName);
        mapMarkerItem1.setAutoCalloutVisible(true);
        tmapView.addMarkerItem("markerItem1", mapMarkerItem1); // 지도에 마커 추가
        tmapView.setCenterPoint(longitude, latitude);
        mapMarkerItem1.setVisible(TMapMarkerItem.VISIBLE);
    }

    private void getRecommendAttraction(double latitude, double longitude, Context context) throws IOException {
        StringBuffer buffer = new StringBuffer();


        new Thread(){
            @Override
            public void run() {
                String mykey = "TFen3YAZ3qSYyKQmw689xVagwAxX/d+rbZXBO09a2orzWmKd4qh7cnNxgpkVRmDtkHANsVzCNfNVl90x5ES/kg==";
                StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList"); /*URL*/
                try {
                    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + mykey); /*Service Key*/
                    urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode(mykey, "UTF-8")); /*공공데이터포털에서 발급받은 인증키*/
                    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
                    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
                    urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰), ETC*/
                    urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
                    urlBuilder.append("&" + URLEncoder.encode("arrange","UTF-8") + "=" + URLEncoder.encode("A", "UTF-8")); /*(A=제목순, B=조회순, C=수정일순, D=생성일순, E=거리순)*/
                    urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*관광타입(관광지, 숙박 등) ID*/
                    urlBuilder.append("&" + URLEncoder.encode("mapX","UTF-8") + "=" + URLEncoder.encode(String.valueOf(longitude), "UTF-8")); /*GPS X좌표(WGS84 경도 좌표)*/
                    urlBuilder.append("&" + URLEncoder.encode("mapY","UTF-8") + "=" + URLEncoder.encode(String.valueOf(latitude), "UTF-8")); /*GPS Y좌표(WGS84 위도 좌표)*/
                    urlBuilder.append("&" + URLEncoder.encode("radius","UTF-8") + "=" + URLEncoder.encode("3000", "UTF-8")); /*거리 반경(단위m), Max값 20000m=20Km*/
                    urlBuilder.append("&" + URLEncoder.encode("listYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*목록 구분 (Y=목록, N=개수)*/
                    urlBuilder.append("&" + URLEncoder.encode("modifiedtime","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*콘텐츠 수정일*/
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                URL url = null;
                try {
                    url = new URL(urlBuilder.toString());
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
                    String title = "", address = "";
                    Bitmap bitmap = null;
                    double longitude = 0, latitude = 0;
                    int contentid = 0;

                    int eventType = xpp.getEventType();

                    while(eventType != XmlPullParser.END_DOCUMENT){
                        switch(eventType){
                            case XmlPullParser.START_TAG:

                                if(xpp.getName().equals("addr1")){
                                    buffer.append("주소: ");
                                    xpp.next();
                                    address = xpp.getText();
                                    buffer.append(xpp.getText());
                                    buffer.append("\n");
                                }
                                else if(xpp.getName().equals("firstimage")){
                                    buffer.append("사진1: ");
                                    xpp.next();

                                    try{
                                        URL image  = new URL(xpp.getText());
                                        URLConnection connection = image.openConnection();
                                        connection.connect();

                                        int size = connection.getContentLength();
                                        BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream(), size);
                                        bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }

                                    buffer.append(xpp.getText());
                                    buffer.append("\n");
                                }
                                else if(xpp.getName().equals("title")){
                                    buffer.append("제목: ");
                                    xpp.next();
                                    title = xpp.getText();
                                    buffer.append(xpp.getText());
                                    buffer.append("\n");
                                }
                                else if(xpp.getName().equals("mapx")){
                                    buffer.append("경도: ");
                                    xpp.next();
                                    longitude = Double.parseDouble(xpp.getText());
                                    buffer.append(xpp.getText());
                                    buffer.append("\n");
                                }
                                else if(xpp.getName().equals("mapy")){
                                    buffer.append("위도: ");
                                    xpp.next();
                                    latitude = Double.parseDouble(xpp.getText());
                                    buffer.append(xpp.getText());
                                    buffer.append("\n");
                                }
                                else if(xpp.getName().equals("contentid")){
                                    buffer.append("콘텐츠ID: ");
                                    xpp.next();
                                    contentid = Integer.parseInt(xpp.getText());
                                    buffer.append(xpp.getText());
                                    buffer.append("\n");
                                }
                                break;
                            case XmlPullParser.TEXT:

                                break;

                            case XmlPullParser.END_TAG:
                                tag = xpp.getName();

                                if(title != "" && address != "" && bitmap != null){
                                    list.add(new attraction(title, address, bitmap, longitude, latitude, contentid));
                                    title = "";
                                    address = "";
                                    bitmap = null;
                                    longitude = 0;
                                    latitude = 0;
                                    contentid = 0;
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
            }
        }.start();

        Toast.makeText(context, "관광지 로딩이 완료되었습니다", Toast.LENGTH_SHORT).show();
    }
}