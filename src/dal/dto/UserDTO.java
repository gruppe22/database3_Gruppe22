package dal.dto;

import java.io.Serializable;
import java.util.List;

public class UserDTO {

    private int userId;
    private String userName;
    private String ini;
    private List<String> roles;

    public UserDTO(int userid, String userName, String ini, List<String> roles) {
        this.userId = userid;
        this.userName = userName;
        this.ini = ini;
        this.roles = roles;
    }
    public UserDTO(){}

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIni() {
        return ini;
    }
    public void setIni(String ini) {
        this.ini = ini;
    }

    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO [userId= " + userId + ", userName= " + userName + ", ini= " + ini + ", roles= " + roles + "]";
    }
}