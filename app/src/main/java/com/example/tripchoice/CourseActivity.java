package com.example.tripchoice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CourseActivity extends AppCompatActivity {
    Button a, b, c, d, e, f, back;
    View dialogView;

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
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("여행코스");
        actionBar.setDisplayHomeAsUpEnabled(true);

        a = (Button) findViewById(R.id.a);
        b = (Button) findViewById(R.id.b);
        c = (Button) findViewById(R.id.c);
        d = (Button) findViewById(R.id.d);
        e = (Button) findViewById(R.id.e);
        f = (Button) findViewById(R.id.f);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(CourseActivity.this, R.layout.course_seoul, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(CourseActivity.this, R.layout.course_gyounggi, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(CourseActivity.this, R.layout.course_daegu, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(CourseActivity.this, R.layout.course_busan, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(CourseActivity.this, R.layout.course_jeju, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(CourseActivity.this, R.layout.course_chungcheong, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CourseActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });


    }



}
