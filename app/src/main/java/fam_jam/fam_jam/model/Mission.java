package fam_jam.fam_jam.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static fam_jam.fam_jam.MainActivity.fireRef;

public class Mission implements Comparable<Mission>{

    private String id;
    private int status, type, tId;
    private long timeCreated, startTime, endTime;
    public Mission(){}

    public Mission(String missionId, int templateId, int missionType, long start){
        this.id = missionId;
        this.type = missionType;
        this.tId = templateId;
        // sets status to pending when created
        this.status = 0;
        // records time of request
        this.timeCreated = System.currentTimeMillis();
        this.startTime = start;

        fireRef.child("mission_templates").child(String.valueOf(type)).child(String.valueOf(tId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MissionTemplate mT = dataSnapshot.getValue(MissionTemplate.class);
                long duration = mT.getTimeAllotted() * 1000 * 60 * 60;
                endTime = duration + startTime;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public int getTimeLeft(){
        // finds time difference in minutes
        Long diff = endTime - System.currentTimeMillis();
        int secDiff = (int) (diff / 1000);
        return secDiff;
    }

    public String getStringTimeLeft(long time){
        long t = getTimeLeft();
        String timeLeft = "";
        if (t < 60){
            timeLeft =  t + " sec";
        } else if (t < 3600) {
            timeLeft += (t / 60) + " min";
        } else {
            timeLeft += (t/3600) + " hours";
        }
        timeLeft += " left";
        return timeLeft;
    }

    //Storing and obtaining the contents of the Firebase into objects
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    // Determining which requests are of higher priority based on their distance, and if distances are equal, how long ago it was sent
    @Override
    public int compareTo(Mission o) {
        // returns 0 (same), 1 (puts o higher), -1 (puts this higher)
        // TODO - compare based on time, type, status
        return 0;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
