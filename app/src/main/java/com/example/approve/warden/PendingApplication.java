package com.example.approve.warden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.approve.LoadingDialog;
import com.example.approve.R;
import com.example.approve.security.SecurityUserActivity;
import com.example.approve.warden.WardenUserActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PendingApplication extends AppCompatActivity {

    TextView tellNameTextView, tellRollNumberTextView, tellPlaceTextView, tellFromDateTextView, tellToDateTextView, tellPurposeTextView;
    Button approveButton, declineButton;

    String name = "", roll= "", fromDate= "", toDate = "", place = "", purpose = "", studentID = "", mode = "";
    FirebaseDatabase database;
    DatabaseReference reference;
    ActionBar actionBar;
    LoadingDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_application);

        approveButton = (Button)findViewById(R.id.approveButton);
        declineButton = (Button)findViewById(R.id.declineButton);
        tellNameTextView  = (TextView)findViewById(R.id.tellNameTextView);
        tellRollNumberTextView = (TextView)findViewById(R.id.tellRollNumberTextView);
        tellFromDateTextView = (TextView)findViewById(R.id.tellFromDateTextView);
        tellToDateTextView = (TextView)findViewById(R.id.tellToDateTextView);
        tellPlaceTextView = (TextView)findViewById(R.id.tellPlaceTextView);
        tellPurposeTextView = (TextView)findViewById(R.id.tellPurposeTextView);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Approve");

        Intent i = getIntent();
        studentID = i.getStringExtra("Student ID");
        mode = i.getStringExtra("Review Mode");
        database = FirebaseDatabase.getInstance();
        final HashMap<String, Object> applicationData = new HashMap<>();
        dialog = new LoadingDialog(PendingApplication.this);

        //write code to fetch info from the studentID and display it in the textViews
        reference = database.getReference().child("Users").child(studentID).child("Application");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                dialog.startLoadingDialog();

                for(DataSnapshot snap : snapshot.getChildren())
                {
                    if(snap.getKey().equals("Name"))
                    {
                        tellNameTextView.setText(snap.getValue().toString());
                    }

                    else if(snap.getKey().equals("Roll Number"))
                    {
                        tellRollNumberTextView.setText(snap.getValue().toString());
                    }

                    else if(snap.getKey().equals("From Date"))
                    {
                        tellFromDateTextView.setText(snap.getValue().toString());
                    }

                    else if(snap.getKey().equals("To Date"))
                    {
                        tellToDateTextView.setText(snap.getValue().toString());
                    }

                    else if(snap.getKey().equals("Place"))
                    {
                        tellPlaceTextView.setText(snap.getValue().toString());
                    }

                    else if(snap.getKey().equals("Purpose"))
                    {
                        tellPurposeTextView.setText(snap.getValue().toString());
                    }
                }

                dialog.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //code to approve the application and notify the student about it (i.e. change the value of the bool variable to true)
                if(mode.equals("Warden"))
                {
                    //code
                    applicationData.clear();
                    applicationData.put("Warden Approval", "true");
                    reference = database.getReference().child("Users").child(studentID).child("Application");
                    reference.updateChildren(applicationData);

                    Intent intent = new Intent(getApplicationContext(), WardenUserActivity.class);
                    startActivity(intent);
                }

                else if(mode.equals("Security"))
                {
                    //code
                    applicationData.clear();
                    applicationData.put("Security Approval", "true");
                    reference = database.getReference().child("Users").child(studentID).child("Application");
                    reference.updateChildren(applicationData);

                    Intent intent = new Intent(getApplicationContext(), SecurityUserActivity.class);
                    startActivity(intent);
                }
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //write code to decline the application and notify the student about it (i.e. don't change the value of the bool variable)
                if(mode.equals("Warden"))
                {
                    //code
                    Intent intent = new Intent(getApplicationContext(), SecurityUserActivity.class);
                    startActivity(intent);
                }

                else if(mode.equals("Security"))
                {
                    //code
                    Intent intent = new Intent(getApplicationContext(), SecurityUserActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}