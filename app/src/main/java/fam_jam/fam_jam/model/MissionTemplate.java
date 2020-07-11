package fam_jam.fam_jam.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MissionTemplate {

    private String id, title, description, imgUrl;
    private long timeAllotted;
    private int points;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public MissionTemplate(){}

    public MissionTemplate(String tId){
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

    public void setTimeAllotted(long timeAllotted) {
        this.timeAllotted = timeAllotted;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}