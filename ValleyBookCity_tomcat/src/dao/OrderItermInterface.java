package dao;

import bean.OrderIterm;
import bean.User;

import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:dao
 * class:OrderItermInterface
 *
 * @author: smile
 * @create: 2023/3/28-17:57
 * @Version: v1.0
 * @Description:
 */
public interface OrderItermInterface {
    void addOrderIterm(OrderIterm orderIterm);

    List<OrderIterm> getOrderItemsList(String orderId);
}
