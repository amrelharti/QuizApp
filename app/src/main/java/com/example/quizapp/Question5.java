package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class Question5 extends AppCompatActivity {
    int score;
    Button next;
    TextView questionText, timerTextView;
    RadioGroup rg;
    RadioButton rb, radioA, radioB, radioC;
    private FirebaseFirestore db;
    String correctAnswer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        rg = findViewById(R.id.rg);
        next = findViewById(R.id.buttonNext5);
        questionText = findViewById(R.id.question_text5);
        timerTextView = findViewById(R.id.timer_text5);
        radioA = findViewById(R.id.radioA5);
        radioB = findViewById(R.id.radioB5);
        radioC = findViewById(R.id.radioC5);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        db = FirebaseFirestore.getInstance();

        db.collection("questions").document("vkGXHxOR1hP3bEpcgmSn")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String question = documentSnapshot.getString("question");
                        String optionA = documentSnapshot.getString("option_a");
                        String optionB = documentSnapshot.getString("option_b");
                        String optionC = documentSnapshot.getString("option_c");
                        correctAnswer = documentSnapshot.getString("correct_answer");

                        questionText.setText(question);
                        radioA.setText(optionA);
                        radioB.setText(optionB);
                        radioC.setText(optionC);

                        startTimer();
                    } else {
                        Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error getting document", Toast.LENGTH_SHORT).show());

        next.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            evaluateAnswerAndProceed();
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time remaining: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                timerTextView.setText("Time's up!");
                evaluateAnswerAndProceed();
            }
        }.start();
    }

    private void evaluateAnswerAndProceed() {
        if (rg.getCheckedRadioButtonId() != -1) {
            rb = findViewById(rg.getCheckedRadioButtonId());
            if (rb.getText().toString().equals(correctAnswer)) {
                score += 1;
                Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
            }
        }
        Intent i1 = new Intent(getApplicationContext(), Score.class);
        i1.putExtra("score", score);
        startActivity(i1);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
