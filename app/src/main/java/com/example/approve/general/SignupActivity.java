package com.example.approve.general;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.approve.LoadingDialog;
import com.example.approve.R;
import com.example.approve.security.SecurityUserActivity;
import com.example.approve.student.StudentUserActivity;
import com.example.approve.warden.WardenUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Spinner usersListSpinner;
    String signUpMethod = "", signUpEmail = "", signUpPassword = "", signUpName = "", userID = "";
    EditText signUpEmailEditText, signUpPasswordEditText, signUpNameEditText;
    ImageButton signUpButton;

    FirebaseAuth auth;
    FirebaseDatabase database;

    public void signUpUser(final String name, final String email, String password, final String userType)
    {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if(task.isSuccessful())
                {
                    if(auth.getCurrentUser() != null)
                        userID = auth.getCurrentUser().getUid();

                    HashMap<String,Object> user = new HashMap<>();
                    user.put("User Type", userType);
                    user.put("Name", name);
                    user.put("Email ID", email);

                    database.getReference().child("Users").child(userID).updateChildren(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(SignupActivity.this, "User registered!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    switch (userType)
                    {
                        case "Student":
                            startActivity(new Intent(SignupActivity.this, StudentUserActivity.class));
                            finish();
                            break;

                        case "Warden":
                            startActivity(new Intent(SignupActivity.this, WardenUserActivity.class));
                            finish();
                            break;

                        case "Security Official":
                            startActivity(new Intent(SignupActivity.this, SecurityUserActivity.class));
                            finish();
                            break;
                    }
                }

                else {
                    Toast.makeText(SignupActivity.this, "Unsuccessful! Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usersListSpinner = (Spinner)findViewById(R.id.usersListSpinner);
        signUpEmailEditText = (EditText)findViewById(R.id.signUpEmailEditText);
        signUpPasswordEditText = (EditText)findViewById(R.id.signUpPasswordEditText);
        signUpNameEditText = (EditText)findViewById(R.id.signUpNameEditText);
        signUpButton = (ImageButton) findViewById(R.id.signUpButton);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        usersListSpinner.setAdapter(adapter);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signUpMethod = usersListSpinner.getSelectedItem().toString();
                signUpEmail = signUpEmailEditText.getText().toString();
                signUpPassword = signUpPasswordEditText.getText().toString();
                signUpName = signUpNameEditText.getText().toString();

                if(TextUtils.isEmpty(signUpName) || TextUtils.isEmpty(signUpEmail) || TextUtils.isEmpty(signUpPassword))  {
                    Toast.makeText(SignupActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }

                else if(signUpPassword.length() < 8)
                    Toast.makeText(SignupActivity.this, "Password should be of at least 8 characters!", Toast.LENGTH_SHORT).show();

                else if(!emailValidator(signUpEmail))
                    Toast.makeText(SignupActivity.this, "Enter a valid email address!", Toast.LENGTH_SHORT).show();
                else
                    signUpUser(signUpName, signUpEmail, signUpPassword, signUpMethod);
            }
        });
    }
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}