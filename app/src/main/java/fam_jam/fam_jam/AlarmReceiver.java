package fam_jam.fam_jam;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fam_jam.fam_jam.model.Member;
import fam_jam.fam_jam.model.Mission;
import fam_jam.fam_jam.model.MissionTemplate;

import static android.content.Context.ALARM_SERVICE;
import static fam_jam.fam_jam.LoginActivity.famId;
import static fam_jam.fam_jam.LoginActivity.user;

public class AlarmReceiver extends BroadcastReceiver {

    // called when the AlarmReceiver reaches the scheduled time
    @Override
    public void onReceive(Context context, Intent i) {
        endWeek();
    }

    public static void setReset(Context context){

        // Intent to execute
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Sets the scheduled task for start of week
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        // Sets the time and interval
        long start = cal.getTimeInMillis();
        long interval = 7*AlarmManager.INTERVAL_DAY;

        // Starts the AlarmManager service to repeat at midnight every week
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(
                AlarmManager.RTC,
                start,
                interval,
                pendingIntent);
    }

    public static void endWeek(){

        final DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference famRef = fireRef.child("families").child(famId);

        // finds start of week
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        final long weekStart = cal.getTimeInMillis();

        // finds other members in family
        fireRef.child("families").child(famId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> members = new ArrayList<String>();
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    String member = (String)s.getValue();
                    members.add(member);
                }

                // makes daily missions
                for (int i=0; i<7; i++){
                    final int rand = (int)Math.floor(Math.random()*5+1);
                    final int type = 2;
                    DatabaseReference templateRef = fireRef.child("mission_templates").child(String.valueOf(type)).child(String.valueOf(rand));
                    final int finalI = i;
                    templateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            MissionTemplate mT = dataSnapshot.getValue(MissionTemplate.class);
                            long duration = mT.getTimeAllotted() * 1000 * 60 * 60;
                            long start = weekStart + (finalI *24*3600*1000);
                            long end = duration + start;
                            String d = famRef.child("missions").push().getKey();
                            Mission m = new Mission(d, user.getUid(), rand, type, start, end);
                            int pos = (int)Math.floor(Math.random()*(members.size()));
                            m.setWith(members.get(pos));
                            // adds mission
                            famRef.child("missions").child(d).setValue(m);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }

                // makes random daily missions
                for (int i=0; i<7; i++){
                    final int rand = (int)Math.floor(Math.random()*5+1);
                    final int type = 1;
                    DatabaseReference templateRef = fireRef.child("mission_templates").child(String.valueOf(type)).child(String.valueOf(rand));
                    final int finalI = i;
                    templateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            MissionTemplate mT = dataSnapshot.getValue(MissionTemplate.class);
                            long duration = mT.getTimeAllotted() * 1000 * 60 * 60;
                            // sets a random start time
                            long start = weekStart + (finalI *24*3600*1000) + (int)Math.floor(Math.random()*3600*24*50);
                            long end = duration + start;
                            String d = famRef.child("missions").push().getKey();
                            Mission m = new Mission(d, user.getUid(), rand, type, start, end);
                            int pos = (int)Math.floor(Math.random()*(members.size()));
                            m.setWith(members.get(pos));
                            // adds mission
                            famRef.child("missions").child(d).setValue(m);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }

                // weekly mission
                final int rand = (int)Math.floor(Math.random()*5+1);
                final int type = 3;
                DatabaseReference templateRef = fireRef.child("mission_templates").child(String.valueOf(type)).child(String.valueOf(rand));
                templateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MissionTemplate mT = dataSnapshot.getValue(MissionTemplate.class);
                        long duration = mT.getTimeAllotted() * 1000 * 60 * 60;
                        long start = weekStart;
                        long end = duration + start;
                        String d = famRef.child("missions").push().getKey();
                        Mission m = new Mission(d, user.getUid(), rand, type, start, end);
                        int pos = (int)Math.floor(Math.random()*(members.size()));
                        m.setWith(members.get(pos));
                        // adds mission
                        famRef.child("missions").child(d).setValue(m);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
