package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Score extends AppCompatActivity {
    TextView score2;
    double score;
    Button buttonLogout,buttonTry;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        score2 = findViewById(R.id.score2);
        buttonLogout = findViewById(R.id.buttonLogout);

        Intent i1 = getIntent();

        score = i1.getIntExtra("score", 0);

        int percentageScore = (int) ((score / 5) * 100);

        score2.setText(String.valueOf(percentageScore));

        buttonTry = findViewById(R.id.buttonTry);
        buttonTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), Question1.class);
                startActivity(i2);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
