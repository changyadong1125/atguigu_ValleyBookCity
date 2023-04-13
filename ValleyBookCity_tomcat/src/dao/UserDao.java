package dao;

import bean.User;

/**
 * project:atguigu_ValleyBookCity
 * package:dao
 * class:UserDao
 *
 * @author: smile
 * @create: 2023/3/19-14:58
 * @Version: v1.0
 * @Description:
 */
public class UserDao extends BaseDao<User> implements UserInterface {
    //通过密码和用户名获取单个用户信息
    public User getUser(String username, String password) {
        String sql = "select * from users where username = ? and password = ?";
        User bean = getBean(sql, username, password);
        close();
        return bean;
    }
    //通过用户名获取用户
    public User getUserBean(String username){
        String sql = "select * from users where username = ?";
        User bean = getBean(sql, username);
        close();
        return bean;
    }
    //注册用户
    public void addUser(String username, String password, String emile) {
        String sql = "insert into users values(null,?,?,?)";
        setBean(sql, username, password, emile);
        close();
    }
}
