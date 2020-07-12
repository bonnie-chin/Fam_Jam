package fam_jam.fam_jam;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fam_jam.fam_jam.model.Member;
import fam_jam.fam_jam.model.Mission;

import static fam_jam.fam_jam.LoginActivity.user;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    final static String TAG = MainActivity.class.getSimpleName();

    // Firebase
    public static DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public static Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sets up nav bar and fragments
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        final MissionsFragment mission = new MissionsFragment();
        loadFragment(mission);

        // finds family of user
        fireRef.child("members").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                member = dataSnapshot.getValue(Member.class);
                LoginActivity.famId = member.getFamId();
                mission.getMissions();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // generates new missions every week
        AlarmReceiver.setReset(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch(menuItem.getItemId()) {
            case R.id.navigation_missions:
                fragment = new MissionsFragment();
                break;
            case R.id.navigation_family:
                fragment = new FamilyFragment();
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.navigation_leaderboard:
                fragment = new LeaderboardFragment();
                break;
        }
        return loadFragment(fragment);
    }

    // set up listener for when data changed --> sends relevant notifications to user
    public void setUpNotifs(){
        DatabaseReference requestRef = fireRef.child("requests");
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // clears the list to fetch new data
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Mission m = itemSnapshot.getValue(Mission.class);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
    }

}