package com.example.approve.warden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.approve.R;
import com.example.approve.general.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.LinearLayout.HORIZONTAL;


public class WardenUserActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ActionBar actionBar;

    ArrayList<String> pendingStudentID;
    ArrayList<String> pendingStudentName;
    ArrayList<String> pendingStudentRoll;
    ArrayList<String> pendingFromDate;
    ArrayList<String> pendingToDate;
    ArrayList<String> pendingPlace;
    ArrayList<String> pendingPurpose;

    String reviewMode = "Warden";

    RecyclerView wardenPendingApplicationsRecyclerView;
    RecyclerView.LayoutManager wardenPendingApplicationsRecyclerViewManager;
    PendingApplicationsRecyclerAdapter wardenPendingApplicationsAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            /*case android.R.id.home :
            {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            */

            case R.id.menuActionLogout :
            {
                auth.signOut();
                Toast.makeText(getApplicationContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WardenUserActivity.this, LoginActivity.class));

                return true;
            }

            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot)
        {
            pendingStudentID.clear();
            pendingStudentRoll.clear();
            pendingStudentName.clear();
            pendingFromDate.clear();
            pendingToDate.clear();
            pendingPlace.clear();
            pendingPurpose.clear();

            for(DataSnapshot uidSnap : snapshot.getChildren())
            {
                for(DataSnapshot userInfoSnap : uidSnap.getChildren())
                {
                    if(userInfoSnap.getKey().equals("Application"))
                    {
                        for(DataSnapshot applicationSnap : userInfoSnap.getChildren())
                        {
                            if(applicationSnap.getKey().equals("Student ID"))
                            {
                                pendingStudentID.add(applicationSnap.getValue().toString());
                            }

                            else if(applicationSnap.getKey().equals("Name"))
                            {
                                pendingStudentName.add(applicationSnap.getValue().toString());
                            }

                            else if(applicationSnap.getKey().equals("Roll Number"))
                            {
                                pendingStudentRoll.add(applicationSnap.getValue().toString());
                            }

                            else if(applicationSnap.getKey().equals("From Date"))
                            {
                                pendingFromDate.add(applicationSnap.getValue().toString());
                            }

                            else if(applicationSnap.getKey().equals("To Date"))
                            {
                                pendingToDate.add(applicationSnap.getValue().toString());
                            }

                            else if(applicationSnap.getKey().equals("Place"))
                            {
                                pendingPlace.add(applicationSnap.getValue().toString());
                            }

                            else if(applicationSnap.getKey().equals("Purpose"))
                            {
                                pendingPurpose.add(applicationSnap.getValue().toString());
                            }
                        }
                    }
                }
            }

            /*for(DataSnapshot uidSnap : snapshot.getChildren())
            {
                for(DataSnapshot userInfoSnap : uidSnap.getChildren())
                {
                    if(userInfoSnap.getKey().equals("Application"))
                    {
                        for(DataSnapshot applicationSnap : userInfoSnap.getChildren())
                        {
                            if(applicationSnap.getKey().equals("Warden Approval"))
                            {
                                if(applicationSnap.getValue().toString().equals("false"))
                                {
                                    for(DataSnapshot applicationSnap1 : userInfoSnap.getChildren())
                                    {
                                        if(applicationSnap.getKey().equals("Student ID"))
                                        {
                                            pendingStudentID.add(applicationSnap.getValue().toString());
                                        }

                                        else if(applicationSnap.getKey().equals("Name"))
                                        {
                                            pendingStudentName.add(applicationSnap.getValue().toString());
                                        }

                                        else if(applicationSnap.getKey().equals("Roll Number"))
                                        {
                                            pendingStudentRoll.add(applicationSnap.getValue().toString());
                                        }

                                        else if(applicationSnap.getKey().equals("From Date"))
                                        {
                                            pendingFromDate.add(applicationSnap.getValue().toString());
                                        }

                                        else if(applicationSnap.getKey().equals("To Date"))
                                        {
                                            pendingToDate.add(applicationSnap.getValue().toString());
                                        }

                                        else if(applicationSnap.getKey().equals("Place"))
                                        {
                                            pendingPlace.add(applicationSnap.getValue().toString());
                                        }

                                        else if(applicationSnap.getKey().equals("Purpose"))
                                        {
                                            pendingPurpose.add(applicationSnap.getValue().toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
             */

            wardenPendingApplicationsAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Data updated!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error)
        {
            Toast.makeText(getApplicationContext(), "Couldn't refresh data!", Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_user);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        pendingStudentID = new ArrayList<String>();
        pendingStudentName = new ArrayList<String>();
        pendingStudentRoll = new ArrayList<String>();
        pendingFromDate = new ArrayList<String>();
        pendingToDate = new ArrayList<String>();
        pendingPlace = new ArrayList<String>();
        pendingPurpose = new ArrayList<String>();

        wardenPendingApplicationsRecyclerView = (RecyclerView)findViewById(R.id.wardenPendingApplicationsRecyclerView);
        wardenPendingApplicationsRecyclerViewManager = new LinearLayoutManager(this);
        wardenPendingApplicationsRecyclerView.setLayoutManager(wardenPendingApplicationsRecyclerViewManager);

        //add code to store application data in arrayList and then, after notifyDataSetChanged(), write the following code inside the valueEventListener's onSuccess itself:
        wardenPendingApplicationsAdapter = new PendingApplicationsRecyclerAdapter(pendingStudentID, pendingStudentName,
                pendingStudentRoll, pendingFromDate,
                pendingToDate, pendingPlace, pendingPurpose, reviewMode, this);
        //setHasFixedSize() would be false here
        DividerItemDecoration itemDecor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        wardenPendingApplicationsRecyclerView.addItemDecoration(itemDecor);
        wardenPendingApplicationsRecyclerView.setAdapter(wardenPendingApplicationsAdapter);

        //code for the query
        Query query = database.getReference("Users");
        query.orderByChild("Warden Approval").equalTo("false");
        query.addListenerForSingleValueEvent(valueEventListener);

        //database.getReference().child("Users").addListenerForSingleValueEvent(valueEventListener);

        //code for the toolbar
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Pending Applications");

        /*drawerLayout = (DrawerLayout)findViewById(R.id.wardenDrawerLayout);
        navigationView = (NavigationView)findViewById(R.id.wardenNavigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.navigationProfile:
                    {
                        item.setChecked(true);
                        //add code
                        drawerLayout.closeDrawers();
                        return true;
                    }


                    case R.id.navigationProfilePic:
                    {
                        item.setChecked(true);
                        //add code
                        drawerLayout.closeDrawers();
                        return true;
                    }

                    case R.id.navigationContactInfo:
                    {
                        item.setChecked(true);
                        //add code
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }

                return false;
            }
        });
         */
    }
}