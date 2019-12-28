package com.kkb.service;

import com.kkb.dao.UserDao;
import com.kkb.exception.LoginException;
import com.kkb.pojo.User;

public class UserService {

    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    /**
     * 验证登入
     * @return
     */
    public User checkLogin(User user) throws LoginException {
        //判断用户名和密码是否为空
        if (validation(user.getUserName()) || validation(user.getPassword())) {
            throw new LoginException("用户名获密码不为空！");
        }
        return userDao.checkLogin(user);
    }

    /**
     * 判断字段是否为空
     * @param value
     * @return
     */
    private boolean validation(String value) {
        if (value != null && value.length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 注册用户信息
     * @param user
     */
    public boolean registerUser(User user) throws LoginException {
        //判断用户名和密码是否为空
        if (validation(user.getUserName()) || validation(user.getPassword())) {
            throw new LoginException("用户名获密码不为空！");
        }
        return userDao.insert(user);
    }
}
