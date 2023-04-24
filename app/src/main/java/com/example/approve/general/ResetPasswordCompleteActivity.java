package com.example.approve.general;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.approve.R;

public class ResetPasswordCompleteActivity extends AppCompatActivity {
    TextView loginredirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_complete);
        loginredirect = (TextView) findViewById(R.id.loginredirect);

        loginredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResetPasswordCompleteActivity.this, LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }
}