package com.example.tripchoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ResRegionSelect extends AppCompatActivity {

    private Button Busan, CN;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_region_select);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("맛집정보");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Busan = findViewById(R.id.Busan_restaurant);
        CN = findViewById(R.id.CN_restaurant);

        Busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResRegionSelect.this, ResMainActivity.class);
                intent.putExtra("region", "Busan");
                startActivity(intent);
            }
        });

        CN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResRegionSelect.this, ResMainActivity.class);
                intent.putExtra("region", "CN");
                startActivity(intent);
            }
        });
    }

}
