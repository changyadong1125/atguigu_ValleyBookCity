package dao;

import bean.Cart;
import bean.Order;
import bean.User;

import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:dao
 * class:OrderInterface
 *
 * @author: smile
 * @create: 2023/3/28-16:50
 * @Version: v1.0
 * @Description:
 */
public interface OrderInterface {
    //新建订单
    void addOrder(Order order);

    List<Order> getOrderList(User user);
}
