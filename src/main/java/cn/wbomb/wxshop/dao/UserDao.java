package cn.wbomb.wxshop.dao;

import cn.wbomb.wxshop.generate.User;
import cn.wbomb.wxshop.generate.UserExample;
import cn.wbomb.wxshop.generate.UserMapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    private final UserMapper userMapper;

    @Autowired
    public UserDao(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    /**
     * Get User info by tel
     *
     * @param tel the phone number
     * @return User info model
     */
    public User getUserByTel(String tel) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTelEqualTo(tel);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0);
    }
}
