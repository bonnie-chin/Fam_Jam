package fam_jam.fam_jam.model;

public class Member {

    private String uId, name, email, famId;
    private String[] members;

    public Member(){}

    public Member(String userId, String userName, String fId){
        this.uId = userId;
        this.name = userName;
        this.famId = fId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String getFamId() {
        return famId;
    }

    public void setFamId(String famId) {
        this.famId = famId;
    }
}
