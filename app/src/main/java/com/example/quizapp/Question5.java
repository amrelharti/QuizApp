package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Question5 extends AppCompatActivity {

    int score;
    Button next;
    RadioGroup rg;
    RadioButton rb;
    String correctAnswer="Soft tyres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);
        rg=findViewById(R.id.rg);
        next=findViewById(R.id.buttonNext);
        Intent i=getIntent();
        score=i.getIntExtra("score",score);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "select a correct answer", Toast.LENGTH_SHORT).show();
                }else{
                    rb=findViewById(rg.getCheckedRadioButtonId());
                    if(rb.getText().toString().equals(correctAnswer))
                        score+=1;
                    Intent i1=new Intent(getApplicationContext(),Score.class);
                    i1.putExtra("score",score);
                    startActivity(i1);
                    finish();
                }
            }
        });
    }}