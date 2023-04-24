package com.example.approve.general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.approve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgottenPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText emailText= (EditText) findViewById(R.id.forget_password_email);
        Button next= (Button) findViewById(R.id.forget_password_next_btn);
        //Log.d(TAG,emailAddress);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = emailText.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgottenPasswordActivity.this,"Email Sent!",Toast.LENGTH_SHORT).show();
                                    Intent myIntent = new Intent(ForgottenPasswordActivity.this, ResetPasswordCompleteActivity.class);
                                    startActivity(myIntent);
                                    //Log.d(TAG, "Email sent.");
                                }
                                else Toast.makeText(ForgottenPasswordActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}