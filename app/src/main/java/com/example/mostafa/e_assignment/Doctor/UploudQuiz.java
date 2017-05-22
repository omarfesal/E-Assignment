package com.example.mostafa.e_assignment.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mostafa.e_assignment.Model.Quiz;
import com.example.mostafa.e_assignment.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploudQuiz extends AppCompatActivity {
    int countOfQuestion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploud_quiz);

        // declare views
        final Button  _submit_question = (Button)findViewById(R.id.submit_qs);
        final Button  _next_button = (Button)findViewById(R.id.next_qs);

        final TextView quiz_textView= (TextView) findViewById(R.id.quiz_txt);
        final TextView crsNameTxtView= (TextView) findViewById(R.id.crsNameTxtView);
        final EditText question_editText = (EditText) findViewById(R.id.question_name);
        final EditText answer1_editText = (EditText) findViewById(R.id.answer1);
        final EditText answer2_editText = (EditText) findViewById(R.id.answer2);
        final EditText answer3_editText = (EditText) findViewById(R.id.answer3);
        final EditText answer4_editText = (EditText) findViewById(R.id.answer4);
        final EditText correctAns_editText = (EditText) findViewById(R.id.correctAns_editText);

        // get Data from Intent
        Intent intent = getIntent();
        final String assignment_name = intent.getStringExtra("assignmentName");
        final String crsName = intent.getStringExtra("crsName");
        System.out.println("assss" + assignment_name + crsName);
        quiz_textView.append(assignment_name);
        crsNameTxtView.append(crsName);



        // submit question
        _submit_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Question = question_editText.getText().toString();
                final String answer1 = answer1_editText.getText().toString();
                final String answer2 = answer2_editText.getText().toString();
                final String answer3 = answer3_editText.getText().toString();
                final String answer4 = answer4_editText.getText().toString();
                final String numOfcorrectAns =  correctAns_editText.getText().toString();
                String correctAns="";
                // put the correct ans
                if(numOfcorrectAns.equals("1")){correctAns=answer1;}
                else if(numOfcorrectAns.equals("2")){correctAns=answer2;}
                else if(numOfcorrectAns.equals("3")){correctAns=answer3;}
                else if(numOfcorrectAns.equals("4")){correctAns=answer4;}
                else {Toast.makeText(getBaseContext(), "You must add number from Range 1 to 4", Toast.LENGTH_LONG).show();}

                upload_Quiz(crsName,assignment_name,Question, answer1 ,answer2,answer3,answer4,correctAns);
                Intent intent = new Intent(UploudQuiz.this , HomeDoctor.class);
                startActivity(intent);
            }
        });

        // next Question
        _next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Question = question_editText.getText().toString();
                final String answer1 = answer1_editText.getText().toString();
                final String answer2 = answer2_editText.getText().toString();
                final String answer3 = answer3_editText.getText().toString();
                final String answer4 = answer4_editText.getText().toString();
                final String numOfcorrectAns =  correctAns_editText.getText().toString();
                String correctAns="";
                // put the correct ans
                if(numOfcorrectAns.equals("1")){correctAns=answer1;}
                else if(numOfcorrectAns.equals("2")){correctAns=answer2;}
                else if(numOfcorrectAns.equals("3")){correctAns=answer3;}
                else if(numOfcorrectAns.equals("4")){correctAns=answer4;}
                else {Toast.makeText(getBaseContext(), "You must add number from Range 1 to 4", Toast.LENGTH_LONG).show();}

                upload_Quiz(crsName ,assignment_name,Question, answer1 ,answer2,answer3,answer4 , correctAns);
                question_editText.setText("");
                answer1_editText.setText("");
                answer2_editText.setText("");
                answer3_editText.setText("");
                answer4_editText.setText("");
                correctAns_editText.setText("");
            }
        });

    }

    public void upload_Quiz(String CourseName , String assignment_name , String q ,
                            String a1 , String a2 , String a3 , String a4 , String correcans){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Doctor");

        Quiz quiz = new Quiz();
        quiz.setQuestion(q);
        quiz.setAnswer1(a1);
        quiz.setAnswer2(a2);
        quiz.setAnswer3(a3);
        quiz.setAnswer4(a4);
        quiz.setCorrectAnswer(correcans);
        myRef.child("Courses").child(CourseName).child("Assignments").child(assignment_name).child("question"+countOfQuestion).setValue(quiz);

        countOfQuestion++;
    }

}
