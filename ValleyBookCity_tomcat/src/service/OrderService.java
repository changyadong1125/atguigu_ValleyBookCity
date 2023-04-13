package service;

import bean.*;
import dao.BookDao;
import dao.OrderDao;
import dao.OrderItermDao;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * project:atguigu_ValleyBookCity
 * package:service
 * class:OrderService
 *
 * @author: smile
 * @create: 2023/3/28-16:35
 * @Version: v1.0
 * @Description:
 */
public class OrderService implements OrderInterface {
    private final OrderDao orderDao = new OrderDao();
    private final OrderItermDao orderItermDao = new OrderItermDao();

    BookDao bookDao = new BookDao();
    private static final Integer STATUS_OK = 1;
    private static final Integer STATUS_NO = 0;

    @SuppressWarnings("all")
    @Override
    public String checkCart(User user, Cart cart, HttpServletRequest request) {
        //将订单信息保存到t_order表中
        Map<Integer, Book> bookMap = (Map<Integer, Book>) request.getServletContext().getAttribute("mapBooks");
        //生成订单号   String orderSequence;
        String orderSequence = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        String creatTime = now.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
        Order order = new Order(orderSequence, creatTime, cart.getCount(), cart.getAmount(), STATUS_OK, user.getId());
        orderDao.addOrder(order);
        //把购物车中的图书取出来
        List<CartIterm> cartIterm = cart.getCartIterm();
        for (CartIterm iterm : cartIterm) {
            OrderIterm orderIterm = new OrderIterm(iterm.getBook().getBookName(), iterm.getBook().getPrice(), iterm.getBook().getImgPath(), iterm.getCount(), iterm.getAmount(),order.getOrderId());
            orderItermDao.addOrderIterm(orderIterm);
            //修改图书的库存和销量
            Book book = iterm.getBook();
            book.setSales(book.getSales() + iterm.getCount());
            book.setStock(book.getStock() - iterm.getCount());
            bookDao.update(book);
            //修改缓存
            bookMap.put(book.getBookId(), book);
        }
        return orderSequence;
    }

    public List<Order> showOrder(User user) {
        return orderDao.getOrderList(user);
    }

    public List<OrderIterm> showOrderItems(String orderId) {
        return orderItermDao.getOrderItemsList(orderId);
    }
}
