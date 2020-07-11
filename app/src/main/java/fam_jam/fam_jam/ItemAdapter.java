package fam_jam.fam_jam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import fam_jam.fam_jam.model.Mission;

// Generates the cards based on data from the Firebase (any requests within the last 30 minutes)

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private static final String TAG = ItemAdapter.class.getSimpleName();
    private List<Mission> missionList;
    public MainActivity main;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // views in card
        public TextView pointsTv, titleTv, timeTopTv;
        public ImageView iconImg;
        public android.widget.Button doneButton;

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            pointsTv = v.findViewById(R.id.card_points);
            titleTv = v.findViewById(R.id.card_title);
            timeTopTv = v.findViewById(R.id.card_time_top);
            iconImg = v.findViewById(R.id.mission_icon);
            doneButton = v.findViewById(R.id.done_button);
        }
    }

    // constructor
    public ItemAdapter(List<Mission> requests, MainActivity m) {
        missionList = requests;
        main = m;
    }

    // create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new card view
        View card = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mission_card, parent, false);
        MyViewHolder vh = new MyViewHolder(card);
        return vh;
    }

    // replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Mission m = missionList.get(position);

        fireRef.child("mission_templates").child(String.valueOf(m.getType())).child(m.gettId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // card header
        String header = "";
        switch (m.getType()){
            case 0:
                header = "WEEK | ";
                break;
            case 1:
                header = "DAY | ";
                break;
            case 2:
                header = "RIGHT NOW | ";
                break;
        }
        switch (m.getStatus()){
            case 0:
                header += m.getStringTimeLeft((long)m.getTimeCreated());
                break;
            case 1:
                header += "COMPLETED";
                break;
            case 2:
                header += "MISSED";
                break;
        }
        holder.timeTopTv.setText(header);

        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(main)
                        .setTitle("Mark complete")
                        .setMessage("Did you complete the mission?")
                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO - change status here
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }


    // returns size of list
    @Override
    public int getItemCount() {
        return missionList.size();
    }



}