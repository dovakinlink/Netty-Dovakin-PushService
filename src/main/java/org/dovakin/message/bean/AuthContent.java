package org.dovakin.message.bean;


/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class AuthContent {

    // 用户名
    private String userId;
    // 密码
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
