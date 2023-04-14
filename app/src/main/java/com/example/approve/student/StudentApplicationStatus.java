package com.example.approve.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.approve.general.MainActivity;
import com.example.approve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class StudentApplicationStatus extends AppCompatActivity {

    TextView tellNameStudentTextView, tellRollNumberStudentTextView, tellPlaceStudentTextView,
            tellFromDateStudentTextView, tellToDateStudentTextView, tellPurposeStudentTextView, tellStatusStudentTextView;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String userID = "";
    boolean wardenApproval, securityApproval;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snap : snapshot.getChildren()) {
                if (Objects.equals(snap.getKey(), "Name")) {
                    tellNameStudentTextView.setText(Objects.requireNonNull(snap.getValue()).toString());
                } else if (Objects.equals(snap.getKey(), "Roll Number")) {
                    tellRollNumberStudentTextView.setText(Objects.requireNonNull(snap.getValue()).toString());
                } else if (snap.getKey().equals("From Date")) {
                    tellFromDateStudentTextView.setText(snap.getValue().toString());
                } else if (snap.getKey().equals("To Date")) {
                    tellToDateStudentTextView.setText(snap.getValue().toString());
                } else if (snap.getKey().equals("Place")) {
                    tellPlaceStudentTextView.setText(snap.getValue().toString());
                } else if (snap.getKey().equals("Purpose")) {
                    tellPurposeStudentTextView.setText(snap.getValue().toString());
                } else if (snap.getKey().equals("Warden Approval")) {
                    wardenApproval = snap.getValue().toString().equals("true");
                } else if (snap.getKey().equals("Security Approval")) {
                    securityApproval = snap.getValue().toString().equals("true");
                }
            }

            if (wardenApproval && securityApproval) {
                tellStatusStudentTextView.setText("Approved");
            } else {
                tellStatusStudentTextView.setText("Pending");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuActionLogout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(StudentApplicationStatus.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StudentApplicationStatus.this, MainActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_application_status);

        tellNameStudentTextView = (TextView) findViewById(R.id.tellNameStudentTextView);
        tellRollNumberStudentTextView = (TextView) findViewById(R.id.tellRollNumberStudentTextView);
        tellPlaceStudentTextView = (TextView) findViewById(R.id.tellPlaceStudentTextView);
        tellFromDateStudentTextView = (TextView) findViewById(R.id.tellFromDateStudentTextView);
        tellToDateStudentTextView = (TextView) findViewById(R.id.tellToDateStudentTextView);
        tellPurposeStudentTextView = (TextView) findViewById(R.id.tellPurposeStudentTextView);
        tellStatusStudentTextView = (TextView) findViewById(R.id.tellStatusStudentTextView);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
       reference = database.getReference().child("Users").child(userID).child("Application");
        reference.addListenerForSingleValueEvent(valueEventListener);
    }
}