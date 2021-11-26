package com.example.tripchoice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HotelActivity extends AppCompatActivity {

    Button Hs, Hg, Hd, Hb, Hj, Hc, HBtn;
    View dialogView, toastView;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        HBtn = (Button) findViewById(R.id.HBtn);
        Hs = (Button) findViewById(R.id.Hs);
        Hg = (Button) findViewById(R.id.Hg);
        Hd = (Button) findViewById(R.id.Hd);
        Hb = (Button) findViewById(R.id.Hb);
        Hj = (Button) findViewById(R.id.Hj);
        Hc = (Button) findViewById(R.id.Hc);

        Hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(HotelActivity.this, R.layout.seouldialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(HotelActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        Hg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(HotelActivity.this, R.layout.gyeonggidialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(HotelActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        Hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(HotelActivity.this, R.layout.daegudialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(HotelActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        Hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(HotelActivity.this, R.layout.busandialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(HotelActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        Hj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(HotelActivity.this, R.layout.jejudialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(HotelActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        Hc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(HotelActivity.this, R.layout.chungcheongdialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(HotelActivity.this);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });

        HBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
