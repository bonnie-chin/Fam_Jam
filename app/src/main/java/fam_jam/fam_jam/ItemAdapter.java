package fam_jam.fam_jam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import fam_jam.fam_jam.model.Member;
import fam_jam.fam_jam.model.Mission;
import fam_jam.fam_jam.model.MissionTemplate;

import static fam_jam.fam_jam.LoginActivity.famId;
import static fam_jam.fam_jam.LoginActivity.user;
import static fam_jam.fam_jam.MainActivity.member;

// Generates the cards based on data from the Firebase (any requests within the last 30 minutes)

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private static final String TAG = ItemAdapter.class.getSimpleName();

    private static int mPoints;

    private List<Mission> missionList;
    public MainActivity main;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // views in card
        public TextView pointsTv, titleTv, timeTopTv, andTv;
        public ImageView iconImg;
        public LinearLayout cardBack;
        public android.widget.Button doneButton;
        public CardView pointsCard;

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            pointsTv = v.findViewById(R.id.card_points);
            titleTv = v.findViewById(R.id.card_title);
            timeTopTv = v.findViewById(R.id.card_time_top);
            andTv = v.findViewById(R.id.card_sender);
            iconImg = v.findViewById(R.id.mission_icon);
            doneButton = v.findViewById(R.id.done_button);
            cardBack = v.findViewById(R.id.cardback);
            pointsCard = v.findViewById(R.id.top_card);
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
                .inflate(R.layout.mission_card_day, parent, false);
        MyViewHolder vh = new MyViewHolder(card);
        return vh;
    }

    // replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Mission m = missionList.get(position);
        final int status = m.getStatus();
        final int type = m.getType();

        // retrieves info from mission template
        fireRef.child("mission_templates").child(String.valueOf(m.getType())).child(String.valueOf(m.gettId())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MissionTemplate t = dataSnapshot.getValue(MissionTemplate.class);

                holder.titleTv.setText(t.getTitle());
                String points = "+ " + t.getPoints();
                holder.pointsTv.setText(points);
                if (status==0) {
                    Picasso.get().load(t.getActiveUrl()).into(holder.iconImg);
                } else {
                    Picasso.get().load(t.getInactiveUrl()).into(holder.iconImg);
                }
                mPoints = t.getPoints();

                if (m.getWith()!=null && !m.getWith().equals(user.getUid())){
                    fireRef.child("members").child(m.getWith()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Member mem = dataSnapshot.getValue(Member.class);
                            String and = "+ " + mem.getName();
                            holder.andTv.setText(and);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                // card header depending on type
                String header = "";
                switch (type){
                    case 3:
                        header = "WEEK  |  ";
                        break;
                    case 1:
                        header = "DAY  |  ";
                        break;
                    case 2:
                        header = "NOW  |  ";
                        break;
                }

                // changes colours / styling for each card
                switch (status){
                    case 0:
                        header += m.getStringTimeLeft((long)m.getTimeCreated());
                        holder.pointsCard.setCardBackgroundColor(Color.parseColor("#FAC16B"));
                        holder.doneButton.setText("MARK\nDONE");
                        if (type==1) {
                            holder.doneButton.setBackgroundResource(R.drawable.completemissionpink);
                            holder.timeTopTv.setTextColor(Color.parseColor("#E2978D"));
                            holder.titleTv.setTextColor(Color.parseColor("#E2978D"));
                            holder.andTv.setTextColor(Color.parseColor("#E2978D"));
                            holder.cardBack.setBackgroundResource(R.drawable.missioncardpink);
                        } else if (type==2) {
                            holder.doneButton.setBackgroundResource(R.drawable.completemissiongreen);
                            holder.timeTopTv.setTextColor(Color.parseColor("#6B9D97"));
                            holder.titleTv.setTextColor(Color.parseColor("#6B9D97"));
                            holder.andTv.setTextColor(Color.parseColor("#6B9D97"));
                            holder.cardBack.setBackgroundResource(R.drawable.missioncardgreen);
                        } else {
                            holder.doneButton.setBackgroundResource(R.drawable.completemissionblue);
                            holder.timeTopTv.setTextColor(Color.parseColor("#6B8B9D"));
                            holder.titleTv.setTextColor(Color.parseColor("#6B8B9D"));
                            holder.andTv.setTextColor(Color.parseColor("#6B8B9D"));
                            holder.cardBack.setBackgroundResource(R.drawable.missioncardblue);
                        }

                        // alert dialog when mission is completed
                        holder.doneButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AlertDialog.Builder(main)
                                        .setTitle("Mark complete")
                                        .setMessage("Did you complete the mission?")
                                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // saves status in database
                                                fireRef.child("families").child(famId).child("missions").child(m.getId()).child("status").setValue(1);
                                                int newPoints = mPoints + member.getPoints();
                                                fireRef.child("members").child(user.getUid()).child("points").setValue(newPoints);
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .show();
                            }
                        });
                        break;
                    case 1:
                        header += "COMPLETED";
                        holder.doneButton.setText("");
                        holder.doneButton.setOnClickListener(null);
                        holder.doneButton.setBackgroundResource(R.drawable.completemissiongrey);
                        holder.timeTopTv.setTextColor(Color.parseColor("#8D8D8D"));
                        holder.titleTv.setTextColor(Color.parseColor("#8D8D8D"));
                        holder.andTv.setTextColor(Color.parseColor("#8D8D8D"));
                        holder.cardBack.setBackgroundResource(R.drawable.missioncardgrey);

                        break;
                    case 2:
                        header += "MISSED";
                        holder.doneButton.setText("");
                        holder.doneButton.setOnClickListener(null);
                        holder.doneButton.setBackgroundResource(R.drawable.incompletemissiongrey);
                        holder.doneButton.setBackgroundResource(R.drawable.completemissiongrey);
                        holder.timeTopTv.setTextColor(Color.parseColor("#8D8D8D"));
                        holder.titleTv.setTextColor(Color.parseColor("#8D8D8D"));
                        holder.andTv.setTextColor(Color.parseColor("#8D8D8D"));
                        holder.cardBack.setBackgroundResource(R.drawable.missioncardgrey);
                        break;
                }
                holder.timeTopTv.setText(header);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(main.getApplicationContext(), "Oops! Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    // returns size of list
    @Override
    public int getItemCount() {
        return missionList.size();
    }



}