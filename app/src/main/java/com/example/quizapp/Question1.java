package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;

public class Question1 extends AppCompatActivity {
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
        setContentView(R.layout.activity_question1);

        rg = findViewById(R.id.rg);
        next = findViewById(R.id.buttonNext1);
        questionText = findViewById(R.id.question_text);
        timerTextView = findViewById(R.id.timer_text);
        radioA = findViewById(R.id.radioA1);
        radioB = findViewById(R.id.radioB1);
        radioC = findViewById(R.id.radioC1);

        db = FirebaseFirestore.getInstance();
        db.collection("questions").document("AK9THnGrYOfBzxVJeweb")
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

                        startTimer();  // Start the timer once the question is loaded
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
        Intent i1 = new Intent(getApplicationContext(), Question2.class);
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
