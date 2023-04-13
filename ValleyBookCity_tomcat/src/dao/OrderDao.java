package dao;

import bean.Cart;
import bean.Order;
import bean.User;

import java.sql.*;
import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:dao
 * class:OrderDao
 *
 * @author: smile
 * @create: 2023/3/28-16:50
 * @Version: v1.0
 * @Description:
 */
public class OrderDao extends BaseDao<Order> implements OrderInterface {
    @SuppressWarnings("all")
    @Override
    public void addOrder(Order order) {
        String sql = "insert into t_order values(null,?,?,?,?,?,?)";
        Connection connection = getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setObject(1, order.getOrderSequence());
            pst.setObject(2, order.getCreatTime());
            pst.setObject(3, order.getTotalCount());
            pst.setObject(4, order.getTotalAmount());
            pst.setObject(5, order.getOrderStatus());
            pst.setObject(6, order.getUserId());
            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                int keyId = generatedKeys.getInt(1);
                order.setOrderId(keyId);
            }
            pst.close();
            generatedKeys.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getOrderList(User user) {
        String sql = "select order_id orderId, order_sequence orderSequence,create_time creatTime,total_count totalCount,total_amount totalAmount,order_status orderStatus from t_order where user_id =? ";
        return this.getList(sql, user.getId());
    }

}
