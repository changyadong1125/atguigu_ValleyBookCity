package servlet;

import bean.Book;
import bean.CommonResult;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import service.BookManagerService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * project:atguigu_ValleyBookCity
 * package:manager.servlet
 * class:ServletBookManager
 *
 * @author: smile
 * @create: 2023/3/23-20:48
 * @Version: v1.0
 * @Description:
 */
@SuppressWarnings("all")
@WebServlet("/ServletBookManager")
public class ServletBookManager extends ServletBase {
    private final BookManagerService bookManagerService = new BookManagerService();

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:使用thymeleaf渲染页面
     */
    protected void showBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //添加缓存
        Map<Integer, Book> bookMap = (Map<Integer, Book>) request.getServletContext().getAttribute("mapBooks");
        List<Book> bookList = null;
        if (bookMap != null) {
            System.out.println("从缓存中取");
           bookList = bookMap.values().stream().sorted(((o1, o2) -> o1.getBookId() - o2.getBookId())).collect(Collectors.toList());
            request.setAttribute("bookList", bookList);
        } else {
            System.out.println("从数据库取");
            bookList = bookManagerService.getAllBooks();
            request.setAttribute("bookList", bookList);
        }
        this.processTemplate("pages/manager/book_manager", request, response);
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:使用异步请求渲染页面
     */
    protected void showBook_Vue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取三个信息
        Map<Integer, Book> bookMap = (Map<Integer, Book>) request.getServletContext().getAttribute("mapBooks");
        List<Book> bookList = null;
        if (bookMap != null) {
            bookList = bookMap.values().stream().sorted(((o1, o2) -> o1.getBookId() - o2.getBookId())).collect(Collectors.toList());
           // request.setAttribute("bookList", bookList);
        } else {
            bookList = bookManagerService.getAllBooks();
           // request.setAttribute("bookList", bookList);
        }
        response.getWriter().write(new Gson().toJson(CommonResult.ok().setResultData(bookList)));
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void toBook_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processTemplate("pages/manager/book_add", request, response);
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void book_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Book book = new Book();
        try {
            BeanUtils.populate(book, parameterMap);
            bookManagerService.AddBook(book);
            //添加到缓存中
            Map<Integer, Book> bookMap = (Map<Integer, Book>) request.getServletContext().getAttribute("mapBooks");
            bookMap.put(book.getBookId(), book);
            request.getRequestDispatcher("/ServletBookManager?method=showBooks").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void toBook_edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String bookId = request.getParameter("bookId");
        Book book = bookManagerService.getBook(bookId);
        request.setAttribute("book", book);
        this.processTemplate("pages/manager/book_edit", request, response);
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void book_edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Integer, Book> bookMap = (Map<Integer, Book>) request.getServletContext().getAttribute("mapBooks");
        Map<String, String[]> parameterMap = request.getParameterMap();

        Book updateBook = new Book();
        try {
            BeanUtils.populate(updateBook, parameterMap);
            String imgPath = bookManagerService.getBook(String.valueOf(updateBook.getBookId())).getImgPath();
            updateBook.setImgPath(imgPath);
            bookManagerService.updateBook(updateBook);
            bookMap.put(updateBook.getBookId(),updateBook);
            request.getRequestDispatcher("/ServletBookManager?method=showBooks").forward(request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */

    protected void delBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从缓存中找到当前要删除的书
        Map<Integer, Book> bookMap = (Map<Integer, Book>) request.getServletContext().getAttribute("mapBooks");
        Book book = null;
        String bookId = request.getParameter("bookId");
        if (bookMap != null) {
            book = bookMap.get(Integer.parseInt(bookId));
            bookManagerService.delBook(book);
            //删除缓存中的数据
            bookMap.remove(Integer.parseInt(bookId));
        } else {
            book = bookManagerService.getBook(bookId);
            bookManagerService.delBook(book);
        }
        request.getRequestDispatcher("/ServletBookManager?method=showBooks").forward(request, response);
}



    protected void toBook_manager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processTemplate("pages/manager/book_manager", request, response);
    }

    protected void toOrder_manager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processTemplate("pages/manager/order_manager", request, response);
    }

}

