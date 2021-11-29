package com.example.tripchoice;

import androidx.annotation.NonNull;
import static android.content.ContentValues.TAG;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.google.type.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Map2Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button MPrev;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String SelectName = "";


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



//        CollectionReference cref = db.collection("대구"); //add doc
//        Map<String, Object> data0 = new HashMap<>();
//        data0.put("category", "tourist");
//        data0.put("latitude", 35.84);
//        data0.put("longitude", 128.55);
//        data0.put("name", "두류공원");
//        data0.put("content", "두류공원은 1965년 공원으로 결정된 이후 1977년부터 본격적으로 개발된 도시근린공원으로서 문화, 교양, 체육시설등이 구비되어 있어 많은 시민들이 이용하는 사랑받는 공원으로 발전하고 있다. 두류공원은 두류산과 금봉산을 중심으로 만들어진 공원으로 이월드 83타워가 들어선 곳은 두류산이고, 문화예술회관 뒷산은 금봉산이다. 두류산은 역사적 기록에 의하면 산이 둥글게 펼쳐져 있다고 하여 두리산으로 부르던 것을 지명이 한자화될때 같은 의미의 주산 또는 두류봉으로 쓰여오다가 근래에 와서 두류산으로 지칭되었다고 한다. 공원내에는 대구의 명소인 이월드 83타워, 면학장소인 두류도서관, 시민건강증진에 기여하고 있는 각종 체육시설과 시민문화의 전당인 문화예술회관이 자리잡고 있다.");
//        String name = data0.get("name").toString();
//        cref.document(name).set(data0);
//
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("category", "tourist");
//        data1.put("latitude", 35.81);
//        data1.put("longitude", 128.57);
//        data1.put("name", "앞산");
//        data1.put("content", "앞산은 대한민국 대구광역시 남쪽에 위치한 해발고도 660.3m의 산이다. 1832년 편찬된 《대구읍지》에는 성불산(成佛山)으로 표기돼 있어 성불산이 본래 이름으로 보인다. 산성산(653m), 대덕산(546m), 성북산(589m)이 주변에 연속적으로 이어져 있다. 대구광역시는 1971년 앞산 일대를 앞산공원으로 고시하였다.");
//        name = data1.get("name").toString();
//        cref.document(name).set(data1);
//
//        Map<String, Object> data2 = new HashMap<>();
//        data2.put("category", "restaurant");
//        data2.put("latitude", 35.87);
//        data2.put("longitude", 128.55);
//        data2.put("name", "팔공막창");
//        data2.put("content", "전화번호 : 053-426-4333, 근무시간 : 12:00~03:00, 주메뉴 : 팔공막창 7,000원 생막창 7,000원 삼겹살 7,000원");
//        name = data2.get("name").toString();
//        cref.document(name).set(data2);
//
//        Map<String, Object> data3 = new HashMap<>();
//        data3.put("category", "stay");
//        data3.put("latitude", 35.85);
//        data3.put("longitude", 128.63);
//        data3.put("name", "대구그랜드호텔");
//        data3.put("content", "저희 대구그랜드호텔은 대구 교통과 비즈니스의 중심지인 수성구 범어동에 위치하고 있으며 초현대적 시설과 화려한 장식을 갖추어 대구 제일의 규모를 자랑하고 있습니다. 150개의 객실과 한식, 일식, 중식, 뷔페식당 그리고 남녀 사우나, 휘트니스센터 등과 예식 및 다양한 연회행사를 위한 다목적의 대,중,소 연회장들을 갖추고 있습니다.\n" +
//                "또한 최고 수준의 서비스와 요리를 제공함으로써 대구 경북 최고의 비즈니스, 관광 명문호텔로써 선도적 역할을 성실히 수행해내고 있으며 국내는 물론 해외에서도 명문호텔로 인정받고 있습니다.\n" +
//                "\n" +
//                "저희 대구그랜드호텔에서 최고의 품격이 어우러진 다양한 서비스를 만끽하시기 바랍니다.");
//        name = data3.get("name").toString();
//        cref.document(name).set(data3);

        db.collection("대구")
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
                SelectName = spinner.getSelectedItem().toString();
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



        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
                String name = marker.getTitle();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference documentReference = db.collection(SelectName).document(name);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            String t = snapshot.getString("name");
                            String c = snapshot.getString("content");
                            showDialog(SelectName, t, c);
                        }
                    }
                });

                return false;
            }
        });

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

    public void showDialog(String name, String t, String c){
        View view = getLayoutInflater().inflate(R.layout.detaileddialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView pos = view.findViewById(R.id.pos_textview);
        TextView title = view.findViewById(R.id.title_textview);
        TextView content = view.findViewById(R.id.detail_textview);
        Button ok = view.findViewById(R.id.detailedCustormButton);

        pos.setText("대한민국 > " + name);
        title.setText(t);
        content.setText(c);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }
}