package com.example.approve.warden;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.approve.R;

import java.util.ArrayList;

public class PendingApplicationsRecyclerAdapter extends RecyclerView.Adapter<PendingApplicationsRecyclerAdapter.PendingApplicationsViewHolder> {

    ArrayList<String> studentID, studentName, studentRoll, fromDate, toDate, place, purpose;
    String mode;
    Context context;

    public PendingApplicationsRecyclerAdapter(ArrayList<String> studentID, ArrayList<String> studentName, ArrayList<String> studentRoll,
                                              ArrayList<String> fromDate, ArrayList<String> toDate,
                                              ArrayList<String> place, ArrayList<String> purpose,
                                              String mode, Context context)
    {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentRoll = studentRoll;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.place = place;
        this.purpose = purpose;
        this.mode = mode;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingApplicationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_requests_layout, parent, false);

        return new PendingApplicationsViewHolder(view, context, studentID, mode);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingApplicationsViewHolder holder, int position)
    {
        String identification = studentName.get(position) + " " + studentRoll.get(position);
        String date = fromDate.get(position);

        holder.pendingRequestNameTextView.setText(identification);
        holder.pendingRequestFromDateTextView.setText(date);
    }


    @Override
    public int getItemCount()
    {
        return studentName.size();
    }

    public static class PendingApplicationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView pendingRequestNameTextView, pendingRequestFromDateTextView;
        Context context;
        ArrayList<String> studentID;
        String mode;

        public PendingApplicationsViewHolder(@NonNull View itemView, Context context, ArrayList<String> studentID, String mode)
        {
            super(itemView);

            this.context = context;
            this.studentID = studentID;
            this.mode = mode;
            pendingRequestFromDateTextView = itemView.findViewById(R.id.pendingRequestFromDateTextView);
            pendingRequestNameTextView = itemView.findViewById(R.id.pendingRequestNameTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context, PendingApplication.class);
            intent.putExtra("Student ID", studentID.get(getAdapterPosition()));
            intent.putExtra("Review Mode", mode);
            context.startActivity(intent);
        }
    }
}
