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

public class NoticeActivity extends AppCompatActivity {

    TextView ntv1, ntv2, toastTv1;
    Button NPrev, NWrite1, NWrite2;
    EditText dlgEd1;
    View dialogView, toastView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ntv1 = (TextView) findViewById(R.id.ntv1);
        ntv2 = (TextView) findViewById(R.id.ntv2);
        NPrev = (Button) findViewById(R.id.NPrev);
        NWrite1 = (Button) findViewById(R.id.NWrite1);
        NWrite2 = (Button) findViewById(R.id.NWrite2);

        NWrite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(NoticeActivity.this, R.layout.noticedialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(NoticeActivity.this);
                dlg.setIcon(R.drawable.star);
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dlgEd1 = (EditText) dialogView.findViewById(R.id.dlgEd1);

                        ntv1.setText(dlgEd1.getText().toString());
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = new Toast(NoticeActivity.this);
                        toastView = (View) View.inflate(NoticeActivity.this, R.layout.noticetoast, null);
                        toastTv1 = (TextView) toastView.findViewById(R.id.toastTv1);
                        toastTv1.setText("취소했습니다");
                        toast.setView(toastView);
                        toast.show();
                    }
                });
                dlg.show();
            }
        });

        NWrite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(NoticeActivity.this, R.layout.noticedialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(NoticeActivity.this);
                dlg.setIcon(R.drawable.star);
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dlgEd1 = (EditText) dialogView.findViewById(R.id.dlgEd1);

                        ntv2.setText(dlgEd1.getText().toString());
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = new Toast(NoticeActivity.this);
                        toastView = (View) View.inflate(NoticeActivity.this, R.layout.noticetoast, null);
                        toastTv1 = (TextView) toastView.findViewById(R.id.toastTv1);
                        toastTv1.setText("취소했습니다");
                        toast.setView(toastView);
                        toast.show();
                    }
                });
                dlg.show();
            }
        });

        NPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
