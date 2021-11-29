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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private String SelectName = null;
    List<Map<String, Object>> Dlist = new ArrayList<Map<String,Object>>();

    LatLng Seoul = new LatLng(37.54, 126.95);
    LatLng Daegu = new LatLng(35.82, 128.58);
    LatLng Busan = new LatLng(35.19, 129.05);
    LatLng Inchoen = new LatLng(37.46, 126.57);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        MPrev = (Button) findViewById(R.id.MPrev);
        MPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> myarray;
        myarray = ArrayAdapter.createFromResource(this,R.array.region_array, android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        spinner.setAdapter(myarray);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectName = (String)spinner.getSelectedItem();
                String Path = (String)spinner.getSelectedItem();
                Dlist.clear();
                mMap.clear();

                db.collection(Path)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("tag", document.getId() + " => " + document.getData());
                                        Dlist.add(document.getData());
                                    }
                                } else {
                                    Log.d("tag", "Error getting documents: ", task.getException());
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("err", "Error getting documents: ", e);
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(int i = 0;i<Dlist.size();i++){
                            MarkerOptions marker1 = new MarkerOptions();
                            marker1.position(new LatLng((Double)(Dlist.get(i).get("latitude")),(Double)(Dlist.get(i).get("longitude"))));
                            marker1.title((String)(Dlist.get(i).get("name")));
                            mMap.addMarker(marker1);
                        }
                    }
                });

                if (position == 0){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Seoul));
                    mMap.animateCamera((CameraUpdateFactory.zoomTo(11)));
                }
                if (position == 1){
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