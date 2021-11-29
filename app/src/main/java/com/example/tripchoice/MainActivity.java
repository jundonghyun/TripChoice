package com.example.tripchoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripchoice.DongHyun.AttractionMainActivity;
import com.example.tripchoice.DongHyun.RecommendAttractionActivity;
import com.example.tripchoice.DongHyun.UserAddAttraction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    EditText MainSearch;
    Button Notice, Map, Schedule, Course, Hotel, Restaurant, Activity, Spot, Account, Logout;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("여행을 초이스");

        //MainSearch = (EditText) findViewById(R.id.MainSearch);
        Notice = (Button) findViewById(R.id.Notice);
        Map = (Button) findViewById(R.id.Map);
        Schedule = (Button) findViewById(R.id.Schedule);
        Course = (Button) findViewById(R.id.Course);
        Hotel = (Button) findViewById(R.id.Hotel);
        Restaurant = (Button) findViewById(R.id.Restaurant);
        Activity = (Button) findViewById(R.id.Activity);
        Spot = (Button) findViewById(R.id.Spot);
        Account = (Button) findViewById(R.id.Account);
        Logout = (Button) findViewById(R.id.Logout);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
                startActivity(intent);
            }
        });

        Hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HotelActivity.class);
                startActivity(intent);
            }
        });

        Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivity(intent);
            }
        });

        Spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AttractionMainActivity.class);
                startActivity(intent);
            }
        });

        Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Map2Activity.class);
                startActivity(intent);
            }
        });

        Restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                startActivity(intent);
            }
        });

        Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityActivity.class);
                startActivity(intent);
            }
        });

        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyidActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}