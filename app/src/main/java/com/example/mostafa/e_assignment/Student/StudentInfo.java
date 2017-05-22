package com.example.mostafa.e_assignment.Student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.mostafa.e_assignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentInfo extends AppCompatActivity {

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    @BindView(R.id.editText_ns) EditText name_student_editTxt;
//    @BindView(R.id.editText3_ps) EditText phone_student_editTxt;
//    @BindView(R.id.editText4_es) EditText email_student_editTxt;
//    @BindView(R.id.editText2_as) EditText address_student_editTxt;
//    @BindView(R.id.image_student) ImageButton image_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        // progress bar
        final ProgressDialog progressDialog = new ProgressDialog(StudentInfo.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("loading...");
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("studenttor").child("info");

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String email = user.getEmail();
                                String name = user.getDisplayName();
                                String userId = user.getUid();

                                TextView emailT = (TextView) findViewById(R.id.Text_student_mail);
                                TextView nameT = (TextView) findViewById(R.id.Text_student_name);
                                TextView UserIdT = (TextView) findViewById(R.id.Text_student_UserId);

                                emailT.setText(email);
                                nameT.setText(name);
                                UserIdT.setText(userId);

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
//        ImageButton img_user_photo = (ImageButton) findViewById(R.id.image_doc);

//        img_user_photo.setOnHoverListener(new View.OnHoverListener() {
//            @Override
//            public boolean onHover(View v, MotionEvent event) {
//                TextView upload_txt = (TextView) findViewById(R.id.upload_photo_txt);
//                upload_txt.setVisibility(View.VISIBLE);
//                return true;
//            }
//
//        });

    }

//    public void uploadImage(View view){
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(intent, 1);
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ImageButton img_user_photo = (ImageButton) findViewById(R.id.image_doc);
//
//        if (requestCode == 1 ) {
//            if (resultCode == RESULT_OK) {
//                Uri uri = data.getData();
//                try {
//                    Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    img_user_photo.setImageBitmap(photo);
//
//                    // save to fireBase
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//                    DatabaseReference ref = FirebaseDatabase.getInstance()
//                            .getReference("studenttor")
//                            .child(user.getUid())
//                            .child("info")
//                            .child("imageUrl");
//                    ref.setValue(imageEncoded);
//
//
//                    Toast.makeText(getApplication() , photo.getHeight() , Toast.LENGTH_SHORT).show();
//                }catch (Exception e) {
//                    System.out.println(e);
//                }
//            }
//        }
//    }
//
//
//    public void updateUser(View view){
//
//        ImageButton img_user_photo = (ImageButton) findViewById(R.id.image_doc);
//
//        img_user_photo.setClickable(true);
//    }
//
//    public void editAccount(View view){
//
//        String name = name_student_editTxt.getText().toString();
//        String phone = name_student_editTxt.getText().toString();
//        String email = name_student_editTxt.getText().toString();
//        String address = name_student_editTxt.getText().toString();
//
//        if(!validate(name,email,phone,address)){
//            return;
//        }
//        // to upload new photo .. make button clickable
//        image_student.setClickable(true);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Students");
//        Student student = new Student();
//        student.setName(name);
//        student.setAddress(address);
//        student.setPhone(phone);
//        student.setEmail(email);
//        myRef.child(user.getUid()).child("info").setValue(student);
//
//
//    }
//    public boolean validate(String n , String e , String p , String a){
//        boolean valid = true;
//
//        if (e.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
//            email_student_editTxt.setError("enter a valid email address");
//            valid = false;
//        } else {
//            email_student_editTxt.setError(null);
//        }
//
//        if (n.isEmpty() || !Patterns.DOMAIN_NAME.matcher(e).matches()) {
//            email_student_editTxt.setError("enter a valid name");
//            valid = false;
//        } else {
//            email_student_editTxt.setError(null);
//        }
//        if (p.isEmpty() || !Patterns.PHONE.matcher(e).matches()) {
//            email_student_editTxt.setError("enter a valid phone number");
//            valid = false;
//        } else {
//            email_student_editTxt.setError(null);
//        }
//        if (a.isEmpty()) {
//            email_student_editTxt.setError("address can't be empty");
//            valid = false;
//        } else {
//            email_student_editTxt.setError(null);
//        }
//
//        return true;
//    }
//
//




}
