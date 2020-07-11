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

import static fam_jam.fam_jam.LoginActivity.famId;
import static fam_jam.fam_jam.MainActivity.fireRef;

public class FamilyFragment extends Fragment {

    private TextView famNameTv, passcodeTv;
    LayoutInflater layoutInflater;
    LinearLayout fam_members;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_family, null);

        famNameTv = v.findViewById(R.id.family_name_tv);
        passcodeTv = v.findViewById(R.id.passcode_tv);

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
        layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 5; i++) {
            View myview = layoutInflater.inflate(R.layout.familymembercard, null, false);
            fam_members.addView(myview);
        }
    }
}
