package com.example.tripchoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {

    DatePicker dp;
    EditText Sed;
    Button WriteBtn, PrevBtn;
    String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        setTitle("나만의 일정");

        dp = (DatePicker) findViewById(R.id.dp);
        Sed = (EditText) findViewById(R.id.Sed);
        WriteBtn = (Button) findViewById(R.id.WriteBtn);
        PrevBtn = (Button) findViewById(R.id.PrevBtn);

        PrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName = Integer.toString(year) + "_" + Integer.toString(monthOfYear + 1)
                        + "_" + Integer.toString(dayOfMonth) + ".txt";
                String str = readDiary(fileName);
                Sed.setText(str);
                WriteBtn.setEnabled(true);
            }
        });

        WriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = Sed.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), fileName+"이 저장됨", Toast.LENGTH_SHORT).show();
                } catch (IOException E) {
                }
            }
        });
    }
    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            WriteBtn.setText("수정하기");
        } catch (IOException e) {
            Sed.setHint("일정을 입력하세요");
            WriteBtn.setText("새로 저장");
        }
        return null;
    }
}