package cn.wbomb.wxshop.service;

import cn.wbomb.wxshop.generate.User;

public class UserContext {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }
}
