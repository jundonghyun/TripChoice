package com.example.tripchoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity {

    EditText JName, JID, JEmail, JPassword1, JPassword2;
    Button JCheckID, JCheckPassword, JJoinBtn, JCancelBtn;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        JName = (EditText) findViewById(R.id.JName);
        JID = (EditText) findViewById(R.id.JID);
        JEmail = (EditText) findViewById(R.id.JEmail);
        JPassword1 = (EditText) findViewById(R.id.JPassword1);
        JPassword2 = (EditText) findViewById(R.id.JPassword2);
        JCheckID = (Button) findViewById(R.id.JCheckID);
        JCheckPassword = (Button) findViewById(R.id.JCheckPassword);
        JJoinBtn = (Button) findViewById(R.id.JJoinBtn);
        JCancelBtn = (Button) findViewById(R.id.JCancelBtn);

        JCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
