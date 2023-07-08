package cn.wbomb.wxshop.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Service
public class UserLoginInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Autowired
    public UserLoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object tel = SecurityUtils.getSubject().getPrincipal();
        if (tel != null) {
            userService.getUserByTel(tel.toString()).ifPresent(UserContext::setCurrentUser);
        }
        System.out.println("pre!");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //清空线程数据，因为线程是会被复用的
        UserContext.setCurrentUser(null);
        System.out.println("Post!");
    }
}
