package service;

import bean.User;
import dao.UserDao;
import dao.UserInterface;
import utils.MD5Util;

/**
 * project:atguigu_ValleyBookCity
 * package:service
 * class:userService
 *
 * @author: smile
 * @create: 2023/3/19-14:52
 * @Version: v1.0
 * @Description:
 */
public class userService implements ServiceInterface {
    private static final UserInterface userDao = new UserDao();
    //验证用户登录

    public User login(User user) {
        UserDao userDao = new UserDao();
        String username = user.getUsername();
        String password = user.getPassword();
        return userDao.getUser(username, MD5Util.encode(password));
    }

    public void register(User user) {
        userDao.addUser(user.getUsername(), MD5Util.encode(user.getPassword()), user.getEmile());
    }

    public User selectByUserName(String username) {
        //验证用户名不能相同
        return userDao.getUserBean(username);
    }

}
