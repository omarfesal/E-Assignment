package com.example.mostafa.e_assignment.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mostafa.e_assignment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class answerAssignment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String Item;
    final HashMap<String , ArrayList<String> > attachedAssignments  = new HashMap<>();
    private ArrayList<String> result = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Spinner element
        final Spinner CourseSpinner = (Spinner) findViewById(R.id.courseSpinner_student);

        // Spinner click listener
        CourseSpinner.setOnItemSelectedListener(this);

        final ArrayList<String> listOfAssignments = new ArrayList<>();
        final ArrayList<String> listOfCourses = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // get courses List
        DatabaseReference myRef = database.getReference("Doctor").child("Courses");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot quiz: dataSnapshot.getChildren()) {
                    String Coursename = quiz.getKey();
                    listOfCourses.add(Coursename);

                    DataSnapshot Assignments = quiz.child("Assignments");

                    for(DataSnapshot assignment : Assignments.getChildren() ){
                        listOfAssignments.add(assignment.getKey());
                    }
                    attachedAssignments.put(Coursename, listOfAssignments);

                }
                putInList(listOfCourses,CourseSpinner);



            }
            public void onCancelled(DatabaseError error) {
                System.out.println("canceled");
            }
        });


    }

    // put data in spinner  of courses list
    public void putInList(ArrayList<String> listOfCourses  , Spinner spinner){

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfCourses);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }


    public void startAnswer(View view){
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Intent intent= new Intent(answerAssignment.this , QuizesActivity.class);
        intent.putExtra("assignment" , item);
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Item = "no selected";
    }
}
