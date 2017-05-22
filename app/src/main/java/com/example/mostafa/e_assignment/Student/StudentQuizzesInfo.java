package com.example.mostafa.e_assignment.Student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mostafa.e_assignment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentQuizzesInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_quizzes_list);

        // progress bar
        final ProgressDialog progressDialog = new ProgressDialog(StudentQuizzesInfo.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("loading...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Doctor").child("Assignments");
                        final ArrayList<String> listOfAssignment = new ArrayList<>();
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                for (DataSnapshot quiz: dataSnapshot.getChildren()) {
                                    System.out.println("wwwwwwwwwwwwwww"+quiz.getKey());

                                    listOfAssignment.add(quiz.getKey());
                                }
                                addtoList(listOfAssignment);

                                //hide progress bar
                                progressDialog.dismiss();
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                System.out.println("canceled");
                            }
                        });
                    }
                },500);

        // on item of list click listener

        final ListView list = (ListView) findViewById(R.id.quiz_info_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = list.getItemAtPosition(position);
                String clickedAssignment = (String) parent.getItemAtPosition(position);
                System.out.println("woooooow"+clickedAssignment);
                Intent intent = new Intent(getBaseContext(), QuizesActivity.class);
                intent.putExtra("currentAssignment",clickedAssignment);
                startActivity(intent);
            }
        });


    }

    public void addtoList(ArrayList<String> listOfAssignment){
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.textview_of_assignments_list, listOfAssignment);
        ListView listView = (ListView) findViewById(R.id.quiz_info_list);
        listView.setAdapter(adapter);
    }

}
