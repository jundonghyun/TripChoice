package com.example.tripchoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout id_inputLayout, password_inputLayout;
    private EditText edit_id, edit_passwd;
    private Button button_login, button_register;
    private CheckBox setid;
    private FirebaseAuth mAuth;

    private Context context;
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_inputLayout = findViewById(R.id.input_layout_id);
        password_inputLayout = findViewById(R.id.input_layout_password);
        edit_id = id_inputLayout.getEditText();
        edit_passwd = password_inputLayout.getEditText();
        button_login = findViewById(R.id.LoginBtn);
        button_register = findViewById(R.id.RegisterBtn);
        setid = findViewById(R.id.setid);

        mAuth = FirebaseAuth.getInstance();
        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();

        if(setting.getBoolean("setid", false)){
            edit_id.setText(setting.getString("setID", ""));
            setid.setChecked(true);
        }

        setid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setid.isChecked()){
                    String id = edit_id.getText().toString();
                    editor.putString("setID", id);
                    editor.putBoolean("setid", true);
                    editor.commit();
                }
                else{
                    editor.clear();
                    editor.commit();
                }
            }
        });

        edit_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String t = s.toString();
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(t).matches()){
                    id_inputLayout.setError("올바른 이메일 형식이 아닙니다");
                }
                else{
                    id_inputLayout.setError(null);
                }
            }
        });

        edit_passwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String t = s.toString();
                Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");

                Matcher m = p.matcher(t);
                if (m.find() && !t.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
                    password_inputLayout.setError(null);
                }else{
                    password_inputLayout.setError("올바른 비밀번호 형식이 아닙니다");
                }
            }
        });
        password_inputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_id.getText().equals("") || edit_passwd.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Login (edit_id.getText().toString(), edit_passwd.getText().toString());
                    ManageConnection();
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, JoinActivity.class));
            }
        });
    }

    private void Login (String email, String password) {
        id = email;
        context = this;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("tag", "SignWithEmail:onComplete" + task.isSuccessful());
                        if(task.isSuccessful()){
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.d("tag", "SignInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "이메일 혹은 비밀번호가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void ManageConnection(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String TempName = id;
        int idx = TempName.indexOf("@");

        final String NickName = id.substring(0, idx);


        final DatabaseReference connectRegerence = db.getReference().child("connection");
        final DatabaseReference lastConnected = db.getReference().child("lastConndected");
        final DatabaseReference infoConnected = db.getReference().child(".info/connected");

        infoConnected.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(boolean.class);

                if(connected){
                    DatabaseReference connect = connectRegerence.child(NickName);
                    connect.setValue(true);
                    connect.onDisconnect().setValue(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}