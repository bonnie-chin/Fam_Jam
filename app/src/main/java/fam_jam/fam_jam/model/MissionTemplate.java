package fam_jam.fam_jam.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MissionTemplate {

    private String title, description, activeUrl, inactiveUrl;
    private long timeAllotted;
    private int id, points;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public MissionTemplate(){}

    public MissionTemplate(int tId){
        this.id = tId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getTimeAllotted() {
        return timeAllotted;
    }

    public void setTimeAllotted(int timeAllotted) {
        this.timeAllotted = timeAllotted;
    }

    public String getActiveUrl() {
        return activeUrl;
    }

    public void setActiveUrl(String activeUrl) {
        this.activeUrl = activeUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInactiveUrl() {
        return inactiveUrl;
    }

    public void setInactiveUrl(String inactiveUrl) {
        this.inactiveUrl = inactiveUrl;
    }
}
