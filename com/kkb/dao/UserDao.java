package com.kkb.dao;

import com.kkb.pojo.User;

import java.util.List;
import java.util.Map;

public class UserDao {

    private DButil dbUtil;

    public UserDao() {
        dbUtil = new DButil();
    }

    /**
     * 登入验证
     * @return
     */
    public User checkLogin(User user) {
        String sql = "select * from user where uname = ? and password = ?";
        List<Map<String, Object>> maps = dbUtil.executeQuery(sql, user.getUserName(), user.getPassword());
        if (!maps.isEmpty()) {
            for (int i = 0;i < maps.size();i++) {
                Map<String, Object> userMap = maps.get(i);
                User user1 = new User();
                user1.setUserName((String) userMap.get("uname"));
                user1.setPassword((String) userMap.get("password"));
                user1.setId((Integer) userMap.get("uid"));
                if (user1 != null) {
                    return user1;
                }
            }
        }
        return null;
    }

    /**
     * 添加
     */
    public boolean insert(User user) {
        String sql = "insert into user values (?,?,?)";
        boolean b = dbUtil.executeUpdate(sql, user.getId(), user.getUserName(), user.getPassword());
        if(b) {
            return true;
        }
        return false;
    }

    /**
     * 删除
     * @param user
     * @return
     */
    public boolean delete(User user) {
        String sql = "delete from user where uid = ?";
        boolean b = dbUtil.executeUpdate(sql, user.getId());
        if (b) {
            return true;
        }
        return false;
    }

    /**
     * 更新
     * @param user
     */
    public boolean update(User user) {
        String sql = "update table set uname = ? where uid = ?";
        boolean b = dbUtil.executeUpdate(sql, user.getUserName(), user.getId());
        if(b) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户列表
     * @return
     */
    public List<Map<String, Object>> getUserList() {
        String sql = "select * from user";
        List<Map<String, Object>> maps = dbUtil.executeQuery(sql, null);
        return maps;
    }

    public static void main(String[] args) {

        UserDao userDao = new UserDao();

        /**
         * 登入验证
         */
        /*User user = new User();
        user.setUserName("小天");
        user.setPassword("123456");
        User user1 = userDao.checkLogin(user);
        System.out.println(user1.toString());*/

        /**
         * 新增
         */
        /*User user = new User("小天", "123456");
        boolean insert = userDao.insert(user);
        System.out.println(insert);*/

        /**
         * 删除
         */
        /*User user = new User();
        user.setId(3);
        boolean delete = userDao.delete(user);
        System.out.println(delete);*/

        /**
         * 获取用户列表
         */
        List<Map<String, Object>> userList = userDao.getUserList();
        for (Map<String,Object> map : userList) {
            for (String key : map.keySet()) {
                System.out.println("key:   " + key);
            }
        }
    }

}
