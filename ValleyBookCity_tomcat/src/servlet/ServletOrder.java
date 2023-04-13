package servlet;

import bean.*;
import com.google.gson.Gson;
import service.OrderService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:${PACKAGE_NAME}
 * class:${NAME}
 *
 * @author: smile
 * @create: 2023/3/28-16:22
 * @Version: v1.0
 * @Description:
 */

@WebServlet(name = "ServletOrder", value = "/Order")
public class ServletOrder extends ServletBase {
    private final OrderService orderService = new OrderService();

    protected void toCheckCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理结账的请求
        //获取请求参数
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        if (cart != null) {
            //调用业务处理请求
            String orderId = orderService.checkCart(user, cart, request);
            //清空购物车
            session.removeAttribute("cart");
            //将订单编号响应回去
            request.setAttribute("orderId", orderId);
            //转发到结账页面
            this.processTemplate("pages/cart/checkout", request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/Cart?method=toCart");
        }

    }

    protected void toOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processTemplate("pages/order/order", request, response);
    }

    protected void showOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //通过数据库查询到所有订单  获取到当前登录用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        PrintWriter writer = response.getWriter();
        //通过用户ID查询到他的订单
        List<Order> orders = orderService.showOrder(user);
        if (orders.size() == 0) {
            writer.write(new Gson().toJson(CommonResult.error().setMessage("没有相关订单，你小子是不是没有买东西？")));
        } else {
            writer.write(new Gson().toJson(CommonResult.ok().setResultData(orders)));
        }
    }

    protected void toOrderItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        request.getSession().setAttribute("orderId", orderId);
        //response.sendRedirect(request.getContextPath()+"/pages/order/orderItem");
       // response.getWriter().write(new Gson().toJson(CommonResult.ok().setMessage(orderId)));
    }


    protected void showOrderItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //通过数据库查询到所有订单  获取到当前登录用户
        PrintWriter writer = response.getWriter();


        String orderId = (String) request.getSession().getAttribute("orderId");
        request.getSession().removeAttribute("orderId");

        //通过用户ID查询到他的订单
        List<OrderIterm> orderItems = orderService.showOrderItems(orderId);
        if (orderItems.size() == 0) {
            writer.write(new Gson().toJson(CommonResult.error().setMessage("订单竟然是空的")));
        } else {
            writer.write(new Gson().toJson(CommonResult.ok().setResultData(orderItems)));
        }
    }
}
