package com.example.tripchoice;

import androidx.annotation.NonNull;
import static android.content.ContentValues.TAG;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Map2Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button MPrev;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    LatLng Seoul = new LatLng(37.54, 126.95);
    LatLng Daegu = new LatLng(35.82, 128.58);
    LatLng Busan = new LatLng(35.19, 129.05);
    LatLng Inchoen = new LatLng(37.46, 126.57);
    LatLng Gwangju = new LatLng(35.12, 126.83);
    LatLng Ulsan = new LatLng(35.51, 129.23);
    LatLng Daejeon = new LatLng(36.32, 127.37);
    LatLng Jeju = new LatLng(33.36, 126.54);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        List<Map<String, Object>> Dlist = new ArrayList<Map<String,Object>>();

        MPrev = (Button) findViewById(R.id.MPrev);
        MPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

//        CollectionReference cref = db.collection("Daegu"); //add doc
//        Map<String, Object> data0 = new HashMap<>();
//        data0.put("category", "tourist");
//        data0.put("latitude", 35.84);
//        data0.put("longitude", 128.55);
//        data0.put("name", "두류공원");
//        cref.document().set(data0);
//
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("category", "tourist");
//        data1.put("latitude", 35.81);
//        data1.put("longitude", 128.57);
//        data1.put("name", "앞산");
//        cref.document().set(data1);
//
//        Map<String, Object> data2 = new HashMap<>();
//        data2.put("category", "restaurant");
//        data2.put("latitude", 35.87);
//        data2.put("longitude", 128.55);
//        data2.put("name", "팔공막창");
//        cref.document().set(data2);
//
//        Map<String, Object> data3 = new HashMap<>();
//        data3.put("category", "stay");
//        data3.put("latitude", 35.85);
//        data3.put("longitude", 128.63);
//        data3.put("name", "대구그랜드호텔");
//        cref.document().set(data3);

        db.collection("Daegu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Dlist.add(document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        ArrayAdapter<CharSequence> myarray;
        myarray = ArrayAdapter.createFromResource(this,R.array.region_array, android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(myarray);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Seoul));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(11)));
                }
                if (position == 1){
                    for(int i = 0;i<Dlist.size();i++){
                        MarkerOptions marker1 = new MarkerOptions();
                        marker1.position(new LatLng((Double)(Dlist.get(i).get("latitude")),(Double)(Dlist.get(i).get("longitude"))));
                        marker1.title((String)(Dlist.get(i).get("name")));
                        mMap.addMarker(marker1);
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Daegu));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(11)));
                }
                if (position == 2){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Busan));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(11)));
                }
                if (position == 3){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Inchoen));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(10)));
                }
                if (position == 4){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Gwangju));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(12)));
                }
                if (position == 5){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Ulsan));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(11)));
                }
                if (position == 6){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Daejeon));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(12)));
                }
                if (position == 7){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Jeju));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(10)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//
//            public boolean onMarkerClick(Marker marker) {
//                System.out.println("marker click");
//                return false;
//            }
//        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(Seoul));
        mMap.animateCamera((CameraUpdateFactory.zoomTo(11)));

        //맵 터치 이벤트 구현 //
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
//            @Override
//           public void onMapClick(LatLng point) {
//                 MarkerOptions mOptions = new MarkerOptions();
//                // 마커 타이틀
//                mOptions.title("마커 좌표");
//                Double latitude = point.latitude; // 위도
//                Double longitude = point.longitude; // 경도
//                // 마커의 스니펫(간단한 텍스트) 설정
//                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
//                // LatLng: 위도 경도 쌍을 나타냄
//                mOptions.position(new LatLng(latitude, longitude));
//                // 마커(핀) 추가
//                googleMap.addMarker(mOptions);
//            }
//        });
    }
}