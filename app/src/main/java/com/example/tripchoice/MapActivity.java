package com.example.tripchoice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    ImageView im1, im2, im3, im4, im;
    Button MPrev;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "경기");
        menu.add(0, 2, 0, "대구");
        menu.add(0, 3, 0, "부산");
        menu.add(0, 4, 0, "제주");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                im1.setVisibility(View.VISIBLE);
                im2.setVisibility(View.INVISIBLE);
                im3.setVisibility(View.INVISIBLE);
                im4.setVisibility(View.INVISIBLE);
                im.setVisibility(View.INVISIBLE);
                return true;
            case 2:
                im1.setVisibility(View.INVISIBLE);
                im2.setVisibility(View.VISIBLE);
                im3.setVisibility(View.INVISIBLE);
                im4.setVisibility(View.INVISIBLE);
                im.setVisibility(View.INVISIBLE);
                return true;
            case 3:
                im1.setVisibility(View.INVISIBLE);
                im2.setVisibility(View.INVISIBLE);
                im3.setVisibility(View.VISIBLE);
                im4.setVisibility(View.INVISIBLE);
                im.setVisibility(View.INVISIBLE);
                return true;
            case 4:
                im1.setVisibility(View.INVISIBLE);
                im2.setVisibility(View.INVISIBLE);
                im3.setVisibility(View.INVISIBLE);
                im4.setVisibility(View.VISIBLE);
                im.setVisibility(View.INVISIBLE);
                return true;
        }
        return false;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        im = (ImageView) findViewById(R.id.im);
        im1 = (ImageView) findViewById(R.id.im1);
        im2 = (ImageView) findViewById(R.id.im2);
        im3 = (ImageView) findViewById(R.id.im3);
        im4 = (ImageView) findViewById(R.id.im4);
        MPrev = (Button) findViewById(R.id.MPrev);

        MPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}