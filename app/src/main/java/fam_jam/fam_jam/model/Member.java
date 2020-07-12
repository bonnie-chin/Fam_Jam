package fam_jam.fam_jam.model;

public class Member {

    private String uId, name, imgUrl, famId;
    private String[] members;
    private int points;

    public Member(){}

    public Member(String userId, String userName, String fId, String photo){
        this.uId = userId;
        this.name = userName;
        this.famId = fId;
        this.points = 0;
        this.imgUrl = photo;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
