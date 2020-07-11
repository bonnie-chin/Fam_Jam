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
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fam_jam.fam_jam.model.Mission;

import static android.content.Context.ALARM_SERVICE;
import static fam_jam.fam_jam.LoginActivity.famId;
import static fam_jam.fam_jam.LoginActivity.user;

// Custom class to schedule a water reset at midnight
public class AlarmReceiver extends BroadcastReceiver {

    // Called when the AlarmReceiver reaches the scheduled time
    @Override
    public void onReceive(Context context, Intent i) {
        endWeek();
    }

    // Class method that schedules a reset at midnight everyday
    public static void setReset(Context context){

        // Intent to execute
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Sets the scheduled task for midnight
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Sets the time and interval
        long midnight = calendar.getTimeInMillis();
        long interval = 7*AlarmManager.INTERVAL_DAY;

        // Starts the AlarmManager service to repeat at midnight every week
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(
                AlarmManager.RTC,
                midnight,
                interval,
                pendingIntent);
    }

    public static void endWeek(){



        // Firebase
        final DatabaseReference famRef = FirebaseDatabase.getInstance().getReference().child("families").child(famId);
        famRef.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {

                    // makes daily missions
                    for (int i=0; i<7; i++){
                        int rand = (int)Math.floor(Math.random()*3);
                        String d = famRef.child("missions").push().getKey();
                        Mission m = new Mission(d, rand, 2);
                        // adds mission
                        famRef.child("missions").child(d).setValue(m);

                        // TODO - add in time
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}