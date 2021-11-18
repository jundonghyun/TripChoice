package com.example.tripchoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    EditText SName, SPhone, SEmail;
    Button SJoin, SCancel;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchid);

        SName = (EditText) findViewById(R.id.SName);
        SPhone = (EditText) findViewById(R.id.SPhone);
        SEmail = (EditText) findViewById(R.id.SEmail);
        SJoin = (Button) findViewById(R.id.SJoin);
        SCancel = (Button) findViewById(R.id.SCancel);

        SCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
