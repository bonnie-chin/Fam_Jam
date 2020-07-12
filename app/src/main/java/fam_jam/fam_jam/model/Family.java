package fam_jam.fam_jam.model;

import com.google.firebase.database.ServerValue;

import java.util.Date;

public class Family {

    private String fId, code, password, adminId;
    private long creationTime;

    public Family(){}

    public Family(String famId, String famCode, String famPassword, String aId){
        this.fId = famId;
        this.code = famCode;
        this.password = famPassword;
        this.adminId = aId;
        this.creationTime = System.currentTimeMillis();
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    // finds how long ago the family was created
    public int secAgo(){
        Long diff = new Date().getTime() - creationTime;
        int secDiff = (int) (diff / 1000);
        return secDiff;
    }
}
