package service;

import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:service
 * class:BookManagerServiceInterface
 *
 * @author: smile
 * @create: 2023/3/24-9:58
 * @Version: v1.0
 * @Description:
 */
public interface BookManagerServiceInterface<Book> {
    List<Book> getAllBooks();
    void AddBook(Book book);
    Book getBook(String bookId);
    void updateBook(Book book);
    void delBook(Book book);
}
