package com.example.tripchoice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    private EditText mEmail, mPassword, mName, conrifmPassword;
    private TextInputLayout passwordcheckLayout;
    private Button mRegisterBtn, password_check, cancelbtn;
    private ProgressBar mProgressBar;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mName = findViewById(R.id.input_name);
        mRegisterBtn = findViewById(R.id.btn_register);
        cancelbtn = findViewById(R.id.btn_cancel);
        conrifmPassword = findViewById(R.id.input_confirm_password);
        passwordcheckLayout = findViewById(R.id.checkpasswordLayout);
        mAuth = FirebaseAuth.getInstance();

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mEmail.getText().toString(), mPassword.getText().toString(), mName.getText().toString());
            }
        });

        conrifmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mPassword.getText().toString().equals(conrifmPassword.getText().toString())){
                    passwordcheckLayout.setError(null);
                }
                else{
                    passwordcheckLayout.setError("비밀번호가 일치하지 않습니다.");
                }
            }
        });
    }

    /* 계정 생성하는 메소드 */
    private void createAccount(String email, String password, String name){

        Map<String, String> User = new HashMap<>();

        User.put("Email", email);
        User.put("Password", password);
        User.put("이름", name);


        DocumentReference newUserRef = db
                .collection("User")
                .document(email);


        newUserRef.set(User).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        if(!isValidEmail(email)){
            Toast.makeText(JoinActivity.this, "올바른 이메일 형식이 아닙니다",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isValidPasswd(password)){
            Toast.makeText(JoinActivity.this, "비밀번호는 영문자와 숫자만 사용가능합니다",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        /* firebase와 계정 연동하는곳 */
        mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(JoinActivity.this, "중복되는 계정입니다",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(JoinActivity.this, "계정을 생성했습니다", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(JoinActivity.this, LoginActivity.class));
                        }
                    }
                });
    }

    /* 유효성 검사 */
    private boolean isValidPasswd(String target){
        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");

        Matcher m = p.matcher(target);
        if (m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
            return true;
        }else{
            return false;
        }
    }

    /* 이메일 유효성 검사 */
    private boolean isValidEmail(String target) {
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
