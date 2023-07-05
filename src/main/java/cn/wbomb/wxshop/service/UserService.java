package cn.wbomb.wxshop.service;

import cn.wbomb.wxshop.dao.UserDao;
import cn.wbomb.wxshop.generate.User;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author George
 */
@Service
@Slf4j
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUserIfNotExist(String tel) {
        User user = new User();
        user.setName("george");
        user.setTel(tel);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        try {
            userDao.insertUser(user);
        }catch (PersistenceException e){
            return userDao.getUserByTel(tel);
        }
        return user;
    }

}
