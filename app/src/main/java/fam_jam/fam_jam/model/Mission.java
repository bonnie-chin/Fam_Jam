package fam_jam.fam_jam.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import fam_jam.fam_jam.MainActivity;

import static fam_jam.fam_jam.MainActivity.fireRef;

public class Mission implements Comparable<Mission>{

    private String id, member;
    private int status, type, tId;
    private long timeCreated, startTime, endTime;

    public Mission(){}

    public Mission(String missionId, String memberId, int templateId, int missionType, long start, long end){
        this.id = missionId;
        this.member = memberId;
        this.type = missionType;
        this.tId = templateId;
        // sets status to pending when created
        this.status = 0;
        // records time of request
        this.timeCreated = System.currentTimeMillis();
        this.startTime = start;
        this.endTime = end;
    }

    public int getTimeLeft(){
        // finds time difference in seconds
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
        if (status == 1 && o.status!=1){
            return 1;
        } else if (status!=1 && o.status==1) {
            return -1;
        } else {
            if (getTimeLeft() > o.getTimeLeft()){
                return 1;
            } else {
                return -1;
            }
        }
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

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
