package dal;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private int	userId;
    private String userName;
    private String ini;
    private List<String> role;

    public User(int userId, String username, String ini, List<String> role ){
        this.userId = userId;
        this.userName = username;
        this.ini = ini;
        this.role = role;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setIni(String ini) {
        this.ini = ini;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }


    public int getUserId() {
        return userId;
    }

    public String getIni() {
        return ini;
    }

    public List<String> getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }
}
