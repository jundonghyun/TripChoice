package com.example.tripchoice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyidActivity extends AppCompatActivity {

    Button IdPrev;
    private TextView name_textview, email_textview, id_textview;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myid);

        IdPrev = (Button) findViewById(R.id.IdPrev);
        name_textview = findViewById(R.id.name);
        id_textview = findViewById(R.id.id);
        email_textview = findViewById(R.id.email);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        List<Map<String, Object>> Dlist = new ArrayList<Map<String, Object>>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("User").document(id);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    id_textview.setText("ID: " + id);
                    name_textview.setText("이름: " + documentSnapshot.getString("name"));
                    email_textview.setText("이메일: " + documentSnapshot.getString("email"));
                }
            }
        });

        IdPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
