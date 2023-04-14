package com.example.approve.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.approve.LoadingDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import com.example.approve.R;

public class StudentUserProfileActivity extends AppCompatActivity {

        TextView studentProfileNameTextView, studentProfileRollNumberTextView, studentProfileHostelTextView, studentProfilePhoneTextView;
        ImageView studentProfilePicImageView, studentProfileUpdatePhotoImageView;

        FirebaseDatabase database;
        DatabaseReference reference;
        StorageReference mStorageRef;
        FirebaseAuth auth;
        ActionBar actionBar;
        LoadingDialog dialog;

        String userID = "";

        public void edit(View view) {
            Intent i = new Intent(getApplicationContext(), StudentEditProfileActivity.class);
            startActivity(i);
        }

        public void getPhoto() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)             //this gets called after the function onActivityResult()
        {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1 && resultCode == RESULT_OK && data != null)         // to make sure user selected an image file
            {
                Uri imageUri = data.getData();

                uploadPhoto(imageUri);
            }
        }

        public void uploadPhoto(Uri imageUri)
        {
            final StorageReference photoRef = mStorageRef.child("Users/" + userID + "/profile");

            photoRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            //imageView.setImageURI(imageUri);

                            dialog.startLoadingDialog();

                            photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    Picasso.get().load(uri).into(studentProfilePicImageView);
                                    dialog.dismissDialog();
                                    Snackbar.make(findViewById(android.R.id.content), "Upload Successful!", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception exception)
                        {

                            dialog.dismissDialog();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_student_user_profile);

            studentProfileNameTextView = (TextView)findViewById(R.id.studentProfileNameTextView);
            studentProfileRollNumberTextView = (TextView)findViewById(R.id.studentProfileRollNumberTextView);
            studentProfileHostelTextView = (TextView)findViewById(R.id.studentProfileHostelTextView);
            studentProfilePicImageView = (ImageView)findViewById(R.id.studentProfilePicImageView);
            studentProfilePhoneTextView = (TextView)findViewById(R.id.studentProfilePhoneTextView);
            studentProfileUpdatePhotoImageView = (ImageView)findViewById(R.id.studentProfileUpdatePhotoimageView);

            actionBar = getSupportActionBar();
            actionBar.setTitle("Your Profile");

            Intent i = getIntent();
            auth = FirebaseAuth.getInstance();
            mStorageRef = FirebaseStorage.getInstance().getReference();
            database = FirebaseDatabase.getInstance();
            userID = auth.getCurrentUser().getUid();
            dialog = new LoadingDialog(StudentUserProfileActivity.this);

            reference = database.getReference().child("Users").child(userID);
            //will the valueEventListener work to initially get the student's name to be displayed (before editing) ?

            reference.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    dialog.startLoadingDialog();

                    for(DataSnapshot snap : snapshot.getChildren())
                    {
                        if(snap.getKey().equals("Name"))
                        {
                            studentProfileNameTextView.setText(snap.getValue().toString());
                            Log.i("Name", snap.getValue().toString());
                        }

                        else if(snap.getKey().equals("Roll Number"))
                        {
                            studentProfileRollNumberTextView.setText(snap.getValue().toString());
                            Log.i("Roll Number", snap.getValue().toString());
                        }

                        else if(snap.getKey().equals("Hostel"))
                        {
                            studentProfileHostelTextView.setText(snap.getValue().toString());
                            Log.i("Hostel", snap.getValue().toString());
                        }

                        else if(snap.getKey().equals("Phone Number"))
                        {
                            studentProfilePhoneTextView.setText(snap.getValue().toString());
                            Log.i("Phone Number", snap.getValue().toString());
                        }
                    }

                    dialog.dismissDialog();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    Toast.makeText(getApplicationContext(), "Failed to retrieve information!", Toast.LENGTH_SHORT).show();
                }
            });

            StorageReference finalRef = mStorageRef.child("Users/" + userID + "/profile");

            //if the profile pic already exists
            finalRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri)
                {

                    dialog.startLoadingDialog();
                    Picasso.get().load(uri).into(studentProfilePicImageView);
                    dialog.dismissDialog();
                }
            });

            studentProfileUpdatePhotoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getPhoto();
                }
            });
        }
}