package com.example.approve.general;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.approve.LoadingDialog;
import com.example.approve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button logInButton, signUpButton;

    FirebaseAuth auth;
    FirebaseDatabase database;
    //LoadingDialog dialog;

    @Override
    protected void onStart()
    {
        super.onStart();

        //dialog = new LoadingDialog(MainActivity.this);
        //dialog.startLoadingDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Approve");

        logInButton = (Button)findViewById(R.id.logInButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);

//        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();

        logInButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        });

        signUpButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(i);
        });
    }
}

