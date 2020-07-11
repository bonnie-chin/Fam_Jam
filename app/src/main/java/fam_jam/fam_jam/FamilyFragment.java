package fam_jam.fam_jam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
}
