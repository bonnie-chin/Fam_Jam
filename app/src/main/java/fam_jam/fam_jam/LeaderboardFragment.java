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
import android.widget.LinearLayout;
import android.widget.TextView;

import fam_jam.fam_jam.model.Member;

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
        layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 4; i++) {
            View myview = layoutInflater.inflate(R.layout.leaderboardcard, null, false);
            leaders.addView(myview);


        }
    }
}