package fam_jam.fam_jam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

import fam_jam.fam_jam.model.Family;
import fam_jam.fam_jam.model.Member;

import static fam_jam.fam_jam.LoginActivity.famId;
import static fam_jam.fam_jam.MainActivity.fireRef;
import static fam_jam.fam_jam.MainActivity.member;

public class FamilyFragment extends Fragment {

    private TextView famNameTv, passcodeTv, countTv, memberCount;
    LayoutInflater layoutInflater;
    LinearLayout fam_members;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_family, null);

        famNameTv = v.findViewById(R.id.family_name_tv);
        passcodeTv = v.findViewById(R.id.passcode_tv);
        countTv = v.findViewById(R.id.count_tv);
        memberCount = v.findViewById(R.id.membercount);

        fireRef.child("families").child(famId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Family f = dataSnapshot.getValue(Family.class);
                famNameTv.setText(f.getCode());
                passcodeTv.setText(f.getPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fam_members = getView().findViewById(R.id.fam_members);

        fireRef.child("families").child(famId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fam_members.removeAllViews();
                countTv.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                int i=0;
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    i++;
                    String m = (String) s.getValue();
                    fireRef.child("members").child(m).addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                              Member m = snapshot.getValue(Member.class);
                              // creates view for each member
                              layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                              View myview = layoutInflater.inflate(R.layout.familymembercard, null, false);
                              //add an onclick listener
                              myview.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      Intent intent = new Intent(getActivity(), FamilyMemberProfile.class);
                                      getActivity().startActivity(intent);
                                  }
                              });

                              TextView name = myview.findViewById(R.id.name_tv);
                              TextView pts = myview.findViewById(R.id.points_tv);
                              name.setText(m.getName());
                              pts.setText(String.valueOf(m.getPoints()));
                              fam_members.addView(myview);
                          }
                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      }
                    );
                }
                memberCount.setText(String.valueOf(i));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
