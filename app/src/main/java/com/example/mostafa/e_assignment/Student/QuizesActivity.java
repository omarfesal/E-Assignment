package com.example.mostafa.e_assignment.Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mostafa.e_assignment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizesActivity extends AppCompatActivity {
    long AssignmentLength=0;
    int Currentcount = 1;
    String question , answer1 , answer2 , answer3 , answer4 ,currentAssignement, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);

        SharedPreferences prefs = getSharedPreferences("PrefFile", MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            user_id = prefs.getString("name", "No id");//"No name defined" is the default value.
        }

        // get Clciked Assignment
        Intent intent = getIntent();
        currentAssignement = intent.getStringExtra("currentAssignment");
        System.out.println("from quiz acccccccccc" + currentAssignement);

        TextView AssignmentName = (TextView) findViewById(R.id.assignment_name_entire);
        AssignmentName.append(currentAssignement);

        loadAssignement(currentAssignement,Currentcount);

        System.out.println( "qqqqqqqqqqqqqqqq"+Currentcount+AssignmentLength);
    }

    public void loadAssignement(String currentAssignement , final int count){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Doctor").child("Assignments").child(currentAssignement);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot assignements) {
                AssignmentLength = assignements.getChildrenCount();

                DataSnapshot quiz = assignements.child("question"+count);

                System.out.println("eeeeeeeeeeeeeeeeeeeeee"+quiz + " leeeeeen : " + AssignmentLength);

                question =(String) quiz.child("question").getValue();
                answer1= (String) quiz.child("answer1").getValue();
                answer2= (String) quiz.child("answer2").getValue();
                answer3= (String) quiz.child("answer3").getValue();
                answer4= (String) quiz.child("answer4").getValue();

                System.out.println("ttttttttttt"+answer1+answer2+answer3+answer4+question);

                // put Data in actvity
                TextView questionTextView = (TextView) findViewById(R.id.quiz_q_text);
                questionTextView.setText(question);
                RadioButton answer1_RadioButton = (RadioButton) findViewById(R.id.ans_1);
                answer1_RadioButton.setText(answer1);
                RadioButton answer2_RadioButton = (RadioButton) findViewById(R.id.ans_2);
                answer2_RadioButton.setText(answer2);
                RadioButton answer3_RadioButton = (RadioButton) findViewById(R.id.ans_3);
                answer3_RadioButton.setText(answer3);
                RadioButton answer4_RadioButton = (RadioButton) findViewById(R.id.ans_4);
                answer4_RadioButton.setText(answer4);

                // control buttons
                controlBtns();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("canceled");
            }

        });
    }

    public void nextBtn(View view){
        //submit the current answer
        System.out.println("neeeeeeeee"+Currentcount+AssignmentLength);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        int checked_id = radioGroup.getCheckedRadioButtonId();
        System.out.println("ccccccccccccccccccccccccc"+checked_id);
        if(checked_id!=0) {
            RadioButton radioButton = (RadioButton) findViewById(checked_id);
            upload_answer(user_id, currentAssignement, radioButton.getText().toString());
        }
        // load new content
        Currentcount++;
        loadAssignement(currentAssignement,Currentcount);

    }

    public void previousBtn(View view){
        //submit the current answer
        System.out.println("neeeeeeeee"+Currentcount+AssignmentLength);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        int checked_id = radioGroup.getCheckedRadioButtonId();
        System.out.println("ccccccccccccccccccccccccc"+checked_id);
        if(checked_id!=0) {
            RadioButton radioButton = (RadioButton) findViewById(checked_id);
            upload_answer(user_id, currentAssignement, radioButton.getText().toString());
        }
        // load new content
        Currentcount--;
        loadAssignement(currentAssignement,Currentcount);
        System.out.println("preeeeeeeeeeeeeeeeeeeee"+Currentcount+AssignmentLength);

    }

    public void submitBtn(View view){
        Intent intent = new Intent(this , StudentQuizzesInfo.class);
        startActivity(intent);

    }

    public void controlBtns(){
        Button next_btn = (Button) findViewById(R.id.next_Btn);
        Button back_btn = (Button) findViewById(R.id.back_Btn);
        Button submit_Btn = (Button) findViewById(R.id.submit_quiz);

        if(Currentcount <= 1) {
            back_btn.setEnabled(false);
            back_btn.setClickable(false);
        }else{
            back_btn.setEnabled(true);
            back_btn.setClickable(true);
        }
        if(Currentcount >= AssignmentLength){
            next_btn.setEnabled(false);
            next_btn.setClickable(false);
            submit_Btn.setVisibility(View.VISIBLE);
        }else{
            next_btn.setEnabled(true);
            next_btn.setClickable(true);
            submit_Btn.setVisibility(View.INVISIBLE);
        }
    }


    public void upload_answer( String User_id , String assignment_name , String ans){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students");
        myRef.child(user_id).child("Assignments").child(assignment_name).child("question"+Currentcount).setValue(ans);
    }



}
