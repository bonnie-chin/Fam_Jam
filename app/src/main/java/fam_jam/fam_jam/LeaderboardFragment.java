package fam_jam.fam_jam;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.widget.LinearLayout;
import android.widget.TextView;

import fam_jam.fam_jam.model.Member;

import static fam_jam.fam_jam.LoginActivity.famId;
import static fam_jam.fam_jam.MainActivity.fireRef;

public class LeaderboardFragment extends Fragment {



    LayoutInflater layoutInflater;
    LinearLayout leaders;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_leaderboard, null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leaders = getView().findViewById(R.id.leaderboard);

        fireRef.child("families").child(famId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leaders.removeAllViews();
                int i =0;
                for (DataSnapshot s : dataSnapshot.getChildren()){

                    String m = (String) s.getValue();
                    fireRef.child("members").child(m).addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             Member m = snapshot.getValue(Member.class);
                             // creates view for each member
                             layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                             View myview = layoutInflater.inflate(R.layout.leaderboardcard, null, false);
                             TextView name = myview.findViewById(R.id.name_tv);
                             TextView pts = myview.findViewById(R.id.score_tv);
                             name.setText(m.getName());
                             String ranking = m.getPoints() + " pts";
                             pts.setText(ranking);
                             leaders.addView(myview);
                         }
                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     }
                    );
                    i++;
                    if (i>=4){
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        /*layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 4; i++) {
            View myview = layoutInflater.inflate(R.layout.leaderboardcard, null, false);
            leaders.addView(myview);


        }*/
    }
}