package cn.wbomb.wxshop.entity;

import cn.wbomb.wxshop.generate.User;

public class LoginResponse {
    private boolean login;
    private User user;

    public LoginResponse(boolean login, User user) {
        this.login = login;
        this.user = user;
    }

    public static LoginResponse notLogin() {
        return new LoginResponse(false, null);
    }

    public static LoginResponse login(User user) {
        return new LoginResponse(true, user);
    }

    public LoginResponse() {
    }

    public User getUser() {
        return user;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
