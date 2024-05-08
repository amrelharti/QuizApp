package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Score extends AppCompatActivity {
    TextView score2;
    int score;
    Button buttonLogout, buttonTry;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        score2 = findViewById(R.id.score2);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonTry = findViewById(R.id.buttonTry);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        int percentageScore = (int) ((score / 5.0) * 100);
        score2.setText(percentageScore + "%");


        buttonTry.setOnClickListener(v -> {
            Intent retryIntent = new Intent(getApplicationContext(), Question1.class);
            startActivity(retryIntent);
            finish();
        });

        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent logoutIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(logoutIntent);
            finish();
        });
    }
}
