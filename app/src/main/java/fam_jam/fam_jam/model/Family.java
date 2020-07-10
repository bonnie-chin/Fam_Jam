package fam_jam.fam_jam.model;

public class Family {


    private String fId, code, password;

    public Family(){}

    public Family(String famId, String famCode, String famPassword){
        this.fId = famId;
        this.code = famCode;
        this.password = famPassword;
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

}
