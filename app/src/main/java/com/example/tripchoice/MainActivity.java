package com.example.tripchoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity  extends AppCompatActivity {


    Button SearchBtn, JoinBtn, LoginBtn;
    EditText ID1, password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("여행을 초이스");

        SearchBtn = (Button) findViewById(R.id.SearchBtn);
        JoinBtn = (Button) findViewById(R.id.JoinBtn);
        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        ID1 = (EditText) findViewById(R.id.ID1);
        password1 = (EditText) findViewById(R.id.password1);

    }
}