package qqcommon;

import java.io.Serializable;

/**
 * 用户信息
 */
public class User implements Serializable {
    private String userId;//用户名
    private String passward;//用户密码
    public User(){

    }

    public User(String userId, String passward) {
        this.userId = userId;
        this.passward = passward;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }
}
