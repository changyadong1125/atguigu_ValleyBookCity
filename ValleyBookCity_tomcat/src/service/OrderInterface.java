package service;

import bean.Cart;
import bean.Order;
import bean.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:service
 * class:OrderInterface
 *
 * @author: smile
 * @create: 2023/3/28-16:34
 * @Version: v1.0
 * @Description:
 */
public interface OrderInterface {
    String checkCart(User user, Cart cart, HttpServletRequest request);

    List<Order> showOrder(User user);
}
