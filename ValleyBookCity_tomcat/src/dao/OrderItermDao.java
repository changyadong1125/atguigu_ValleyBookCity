package dao;

import bean.OrderIterm;
import bean.User;
import dao.BaseDao;

import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:bean
 * class:OrderItermDao
 *
 * @author: smile
 * @create: 2023/3/28-17:56
 * @Version: v1.0
 * @Description:
 */
public class OrderItermDao extends BaseDao<OrderIterm> implements OrderItermInterface {
    @Override
    public void addOrderIterm(OrderIterm orderIterm) {
        String sql = "insert into t_order_item values(null,?,?,?,?,?,?)";
        this.setBean(sql, orderIterm.getBookName(), orderIterm.getPrice(), orderIterm.getImgPath(), orderIterm.getItemCount(), orderIterm.getItemAmount(), orderIterm.getOrderId());
    }

    public  List<OrderIterm> getOrderItemsList(String orderId) {
        String sql = "select book_name bookName ,price ,img_path imgPath,item_count itemCount ,item_amount itemAmount ,order_id orderId from  t_order_item where order_id=?";
       return this.getList(sql, orderId);
    }
}
