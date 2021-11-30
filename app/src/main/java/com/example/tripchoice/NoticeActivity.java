package com.example.tripchoice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NoticeActivity extends AppCompatActivity {

    TextView ntv1, ntv2, ntv3, toastTv1;
    Button NPrev, NWrite1, NWrite2, NWrite3;
    EditText dlgEd1;
    View dialogView, toastView;
    ViewFlipper vf1;

    float initX;

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
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) { // 터치가 눌러졌을 때
            initX = event.getRawX();
        } else if(event.getAction() == MotionEvent.ACTION_UP) { // 터치가 떼어졌을 때
            float diffX = initX - event.getRawX(); // 손가락이 떨어지는 그 지점의 x좌표

            if(diffX > 30) {
                vf1.showNext();
            } else if(diffX < -30) {
                vf1.showPrevious();
            }
        }
        return true;
    } // 드래그로 화면 넘기기 액티비티

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("공지사항");
        actionBar.setDisplayHomeAsUpEnabled(true);

        ntv1 = (TextView) findViewById(R.id.ntv1);
        ntv2 = (TextView) findViewById(R.id.ntv2);
        ntv3 = (TextView) findViewById(R.id.ntv3);
        NWrite1 = (Button) findViewById(R.id.NWrite1);
        NWrite2 = (Button) findViewById(R.id.NWrite2);
        NWrite3 = (Button) findViewById(R.id.NWrite3);
        vf1 = (ViewFlipper) findViewById(R.id.vf1);

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

        NWrite3.setOnClickListener(new View.OnClickListener() {
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

                        ntv3.setText(dlgEd1.getText().toString());
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
    }
}
