package com.example.tripchoice.DongHyun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tripchoice.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public class RecommendAttractionActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ShowHotelOnMapFragment showHotelOnMapFragment = new ShowHotelOnMapFragment();
    private ShowRecommendAttractionFragement showRecommendAttractionFragement = new ShowRecommendAttractionFragement();
    private RecommendAttractionData data;
    private InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_attraction);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, showHotelOnMapFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new ItemSelectedListener());

        double latitude = 0, longitude = 0;
        String POIName = "";


        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            data = (RecommendAttractionData) intent.getSerializableExtra("data");
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            showHotelOnMapFragment.setArguments(bundle);
            POIName = data.POIName;
            latitude = data.latitude;
            longitude = data.longitude;
        }
    }


    class ItemSelectedListener implements NavigationBarView.OnItemSelectedListener{


        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(item.getItemId()){
                case R.id.viewmap:
                    transaction.replace(R.id.framelayout, showHotelOnMapFragment).commitAllowingStateLoss();
                    break;

                case R.id.viewlist:
                    transaction.replace(R.id.framelayout, showRecommendAttractionFragement).commitAllowingStateLoss();
                    break;

            }
            return true;
        }
    }
}