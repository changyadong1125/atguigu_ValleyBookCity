package dao;

import bean.Book;
import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:dao
 * class:BookInterface
 *
 * @author: smile
 * @create: 2023/3/24-10:00
 * @Version: v1.0
 * @Description:
 */
public interface BookInterface {
    List<Book> getAllBooks();
    void AddBook(Book book);
    Book getBook(String bookId);
    void update(Book book);
    void delBook(Book book);
}
