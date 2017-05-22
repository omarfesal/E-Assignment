package com.example.mostafa.e_assignment.Student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mostafa.e_assignment.Doctor.CourseDetail;
import com.example.mostafa.e_assignment.LoginActivity;
import com.example.mostafa.e_assignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeStudent extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // add data to nav bar

//        ((TextView)findViewById(R.id.studentNameNav)).setText(user.getDisplayName());
//        ((TextView)findViewById(R.id.StudentEmailNav)).setText(user.getEmail());



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // progress bar
        final ProgressDialog progressDialog = new ProgressDialog(HomeStudent.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("loading...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Doctor").child("Courses");
                        final ArrayList<String> listOfCourses = new ArrayList<>();
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                for (DataSnapshot quiz : dataSnapshot.getChildren()) {
                                    System.out.println("wwwwwwwwwwwwwww" + quiz.getKey());
                                    listOfCourses.add(quiz.getKey());
                                }
                                addtoList(listOfCourses);

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
                }, 500);

        // on item of list click listener

        final ListView list = (ListView) findViewById(R.id.listViewOfCourses2);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ClickedCourse = (String) parent.getItemAtPosition(position);
                System.out.println("woooooow" + ClickedCourse);
                Intent intent = new Intent(getBaseContext(), CourseDetail.class);
                intent.putExtra("currentCourse", ClickedCourse);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_student) {
            Intent intent;
            intent = new Intent(HomeStudent.this, StudentInfo.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            HomeStudent.this.startActivity(intent);

        } else if (id == R.id.nav_quizes_info) {
            Intent intent;
            intent = new Intent(HomeStudent.this, StudentQuizzesInfo.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            HomeStudent.this.startActivity(intent);


        } else if (id == R.id.nav_sign_out) {
            Intent intent;
            intent = new Intent(HomeStudent.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            HomeStudent.this.startActivity(intent);


        } else if (id == R.id.nav_answer_assignment) {
            Intent intent;
            intent = new Intent(HomeStudent.this, answerAssignment.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            HomeStudent.this.startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addtoList(ArrayList<String> listOfAssignment){
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.textview_of_assignments_list, listOfAssignment);
        ListView listView = (ListView) findViewById(R.id.listViewOfCourses2);
        listView.setAdapter(adapter);
    }
}
