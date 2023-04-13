package service;

import bean.User;

/**
 * project:atguigu_ValleyBookCity
 * package:service
 * class:RegisterServiceInterface
 *
 * @author: smile
 * @create: 2023/3/20-11:52
 * @Version: v1.0
 * @Description:
 */
public interface ServiceInterface {
    void register(User user);

    User login(User user);

    User selectByUserName(String username);
}
