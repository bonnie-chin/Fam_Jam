package fam_jam.fam_jam.model;

public class Family {


    private String fId, code, password, adminId;

    public Family(){}

    public Family(String famId, String famCode, String famPassword, String aId){
        this.fId = famId;
        this.code = famCode;
        this.password = famPassword;
        this.adminId = aId;
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
}
