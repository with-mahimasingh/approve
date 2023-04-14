package com.example.approve.warden;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.approve.R;

public class WardenDetails extends AppCompatActivity {

    RecyclerView wardenListRecyclerView;
    RecyclerView.LayoutManager manager;
    WardenRecyclerAdapter wardenRecyclerAdapter;
    ActionBar actionBar;

    int[] wardenImages = {R.drawable.bit_logo, R.drawable.bit_logo, R.drawable.bit_logo, R.drawable.bit_logo, R.drawable.bit_logo, R.drawable.bit_logo};
    String[] wardenNames = {"Dr. Kaushlendra Sharma ", "Dr. Premlata Singh", "Dr. Nidhi Mishra", "Dr. K.P. Tiwary", "Dr. Sushil Kumar"};
    String[] designations = {"Chief Warden", "Warden", "Asst. Warden", "Warden", "Asst. Warden", "Warden"};
    String[] phoneNumbers = {"+91-954-6800874", "+91-903-1812709", "+91-997-1986676", "+91-990-5126933", "+91-703-33990203"};
   // String[] emailIds = {"pankajs@iiitm.ac.in", "vishal@iiitm.ac.in", "nidhimishra@bitmesra.ac.in", "kkpatnaik@iiitm.ac.in", "arunkumar@iiitm.ac.in"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_details);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Wardens");

        wardenListRecyclerView = (RecyclerView) findViewById(R.id.wardenListRecyclerView);
        manager = new LinearLayoutManager(this);

        wardenListRecyclerView.setLayoutManager(manager);
        //wardenListRecyclerView.setHasFixedSize(true);

        wardenRecyclerAdapter = new WardenRecyclerAdapter(wardenImages, wardenNames, designations, phoneNumbers);
        wardenListRecyclerView.setAdapter(wardenRecyclerAdapter);
    }
}