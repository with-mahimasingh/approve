package com.example.approve.general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.approve.R;
import com.example.approve.security.SecurityUserActivity;
import com.example.approve.student.StudentUserActivity;
import com.example.approve.warden.WardenUserActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ImageButton logInButton;
    EditText logInEmailEditText, logInPasswordEditText;
    String logInEmail, logInPassword;
    FirebaseDatabase database;
    String userType = "";
    TextView tvForgotPwd;

    public void logInUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

            @Override
            public void onSuccess(AuthResult authResult) {

                FirebaseUser user = auth.getCurrentUser();
                String userID = "";
                if(user != null){
                    userID = user.getUid();
                }
                if (!userID.equals(""))
                {
                    DatabaseReference reference = database.getReference().child("Users").child(userID);

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            for(DataSnapshot snap : snapshot.getChildren())
                            {
                                if(Objects.equals(snap.getKey(), "User Type"))
                                {
                                    userType = String.valueOf(snap.getValue());

                                    switch (userType)
                                    {
                                        case "Student":
                                            Toast.makeText(LoginActivity.this, "LogIn Successful!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, StudentUserActivity.class));
                                            finish();
                                            break;

                                        case "Warden":
                                            Toast.makeText(LoginActivity.this, "LogIn Successful!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, WardenUserActivity.class));
                                            finish();
                                            break;

                                        case "Security Official":
                                            Toast.makeText(LoginActivity.this, "LogIn Successful!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, SecurityUserActivity.class));
                                            finish();
                                            break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {
                            Toast.makeText(LoginActivity.this, "Couldn't load user information! Try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInButton = (ImageButton) findViewById(R.id.logInButton);
        logInEmailEditText = (EditText) findViewById(R.id.logInEmailEditText);
        logInPasswordEditText = (EditText) findViewById(R.id.logInPasswordEditText);
        tvForgotPwd = (TextView) findViewById(R.id.txtVwForgetPassword);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(logInEmail) || TextUtils.isEmpty(logInPassword))  {
                    Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                logInEmail = logInEmailEditText.getText().toString();
                logInPassword = logInPasswordEditText.getText().toString();
                logInUser(logInEmail, logInPassword);
            }
        });
        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, ForgottenPasswordActivity.class);
                startActivity(myIntent);
            }
        });
    }
}