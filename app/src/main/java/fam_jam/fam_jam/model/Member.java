package fam_jam.fam_jam.model;

public class Member {

    private String uId, name, email;

    public Member(){}

    public Member(String userId, String userName, String userEmail){
        this.uId = userId;
        this.name = userName;
        this.email = userEmail;
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
}
