package fam_jam.fam_jam.model;

import com.google.firebase.database.ServerValue;

import java.util.Date;

public class Mission implements Comparable<Mission>{

    private String id;
    private int status, type, tId;
    private Object timeCreated;
    public Mission(){}

    public Mission(String missionId, int templateId, int missionType){
        this.id = missionId;
        this.type = missionType;
        this.tId = templateId;
        // sets status to pending when created
        this.status = 0;
        // records time of request
        this.timeCreated = ServerValue.TIMESTAMP;
    }

    public int getTimeLeft(long time){

        // finds time difference in minutes
        Long diff = new Date().getTime() - time;
        // TODO - find template here
        int secDiff = (int) (diff / 1000);
        return secDiff;
    }

    public String getStringTimeLeft(long time){
        // finds time difference in minutes
        long t = getTimeLeft(time);
        String timeLeft = "";
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

    public Object getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Object timeCreated) {
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
}
