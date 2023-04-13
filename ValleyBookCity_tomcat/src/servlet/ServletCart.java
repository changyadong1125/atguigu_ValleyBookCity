package servlet;
/**
 * project:atguigu_ValleyBookCity
 * package:${PACKAGE_NAME}
 * class:${NAME}
 *
 * @author: smile
 * @create: 2023/3/27-11:23
 * @Version: v1.0
 * @Description:
 */

import bean.Book;
import bean.Cart;
import bean.CartIterm;
import bean.CommonResult;
import com.google.gson.Gson;
import service.BookManagerService;
import service.BookManagerServiceInterface;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Cart")
public class ServletCart extends ServletBase {
    private final BookManagerServiceInterface<Book> bookManagerService = new BookManagerService();

    protected void addBookToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        Book book = bookManagerService.getBook(id);
        Cart cart = (Cart) session.getAttribute("cart");
        //如果是第一次点击添加则创建一个购物车对象，然后放进去
        PrintWriter writer = response.getWriter();
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        try {
            cart.addBookToCart(book);
            writer.write(new Gson().toJson(CommonResult.ok().setMessage(String.valueOf(cart.getCount()))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void toCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processTemplate("pages/cart/cart", request, response);
    }

    protected void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取三个信息
        List<Object> list = getList(request);
        response.getWriter().write(new Gson().toJson(CommonResult.ok().setResultData(list)));
    }


    private static List<Object> getList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart!=null){
            Double amount = cart.getAmount();
            Integer count = cart.getCount();
            List<CartIterm> cartIterm = cart.getCartIterm();
            List<Object> list = new ArrayList<>();
            list.add(cartIterm);
            list.add(count);
            list.add(amount);
            return list;
        }else {
            return null;
        }
    }

    protected void delBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Book book = bookManagerService.getBook(id);
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.delBook(book);
        List<Object> list = getList(request);
        response.getWriter().write(new Gson().toJson(CommonResult.ok().setResultData(list)));
    }

    protected void toCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processTemplate("pages/cart/checkout", request, response);
    }

    protected void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("cart");
        response.getWriter().write(new Gson().toJson(CommonResult.ok()));
    }
    protected void addCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.addCount(Integer.parseInt(id));
        List<Object> list = getList(request);
        response.getWriter().write(new Gson().toJson(CommonResult.ok().setResultData(list)));
    }
    protected void subCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.subCount(Integer.parseInt(id));
        List<Object> list = getList(request);
        response.getWriter().write(new Gson().toJson(CommonResult.ok().setResultData(list)));
    }

    protected void  changeCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String count = request.getParameter("count");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (Integer.parseInt(count)>0){
            cart.changeCount(Integer.parseInt(id), Integer.parseInt(count));
            List<Object> list = getList(request);
            response.getWriter().write(new Gson().toJson(CommonResult.ok().setResultData(list)));
        }else{
            List<Object> list = getList(request);
            response.getWriter().write(new Gson().toJson(CommonResult.error().setResultData(list)));
        }
    }
}
