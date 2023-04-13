package servlet; /**
 * project:atguigu_ValleyBookCity
 * package:${PACKAGE_NAME}
 * class:${NAME}
 *
 * @author: smile
 * @create: 2023/3/21-10:27
 * @Version: v1.0
 * @Description:
 */

import bean.Book;
import service.BookManagerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "ServletIndex", value = "/index.html")
public class ServletIndex extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    @SuppressWarnings("all")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookManagerService bookManagerService = new BookManagerService();
        Map<Integer, Book> bookMap = (Map<Integer, Book>) request.getServletContext().getAttribute("mapBooks");
        List<Book> bookList = null;
        if (bookMap == null) {
            System.out.println("从数据库中取");
            bookList = bookManagerService.getAllBooks();
            bookMap = bookList.stream().collect(Collectors.toConcurrentMap(book -> book.getBookId(), book -> book));
            request.getServletContext().setAttribute("mapBooks", bookMap);
        } else {
            System.out.println("从缓存取");
            bookList = bookMap.values().stream().sorted((book1, book2) -> book1.getBookId() - book2.getBookId()).collect(Collectors.toList());
            request.setAttribute("bookList", bookList);
        }
        request.setAttribute("book",bookList);
        this.processTemplate("index",request,response);
    }
}
