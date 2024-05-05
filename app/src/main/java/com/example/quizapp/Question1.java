package com.example.quizapp;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.firestore.FirebaseFirestore;

public class Question1 extends AppCompatActivity {
    int score;
    Button next;
    TextView questionText;
    RadioGroup rg;
    RadioButton rb, radioA, radioB, radioC;
    private FirebaseFirestore db;
    String correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        rg = findViewById(R.id.rg);
        next = findViewById(R.id.buttonNext1);
        questionText = findViewById(R.id.question_text);
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

                        // Log the data
                        Log.d("Question1", "Question: " + question);
                        Log.d("Question1", "Option A: " + optionA);
                        Log.d("Question1", "Option B: " + optionB);
                        Log.d("Question1", "Option C: " + optionC);
                        Log.d("Question1", "Correct Answer: " + correctAnswer);
                    } else {
                        Log.d("Question1", "No such document");
                        Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Question1", "Error getting document", e);
                    Toast.makeText(getApplicationContext(), "Error getting document", Toast.LENGTH_SHORT).show();
                });

        next.setOnClickListener(v -> {
            if (rg.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Select an answer", Toast.LENGTH_SHORT).show();
            } else {
                rb = findViewById(rg.getCheckedRadioButtonId());
                String selectedAnswer = rb.getText().toString();
                if (selectedAnswer.equals(correctAnswer)) {
                    score += 1;
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                }

                Intent i1 = new Intent(getApplicationContext(), Question2.class);
                i1.putExtra("score", score);
                startActivity(i1);
                finish();
            }
        });
    }
}
