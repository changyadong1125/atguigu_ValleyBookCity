package dao;

import bean.User;

/**
 * project:atguigu_ValleyBookCity
 * package:dao
 * class:UserInterface
 *
 * @author: smile
 * @create: 2023/3/20-9:45
 * @Version: v1.0
 * @Description:
 */
public interface UserInterface {
    User getUser(String username, String password);

    User getUserBean(String username);

    void addUser(String username, String password, String emile);


}
