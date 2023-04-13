package service;

import bean.Book;
import dao.BookDao;
import dao.BookInterface;

import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:service
 * class:BookManagerService
 *
 * @author: smile
 * @create: 2023/3/24-9:58
 * @Version: v1.0
 * @Description:
 */
public class BookManagerService implements BookManagerServiceInterface<Book> {
    BookDao bookDao = new BookDao();
    @Override
    public List<Book> getAllBooks() {

        return bookDao.getAllBooks();
    }
    @Override
    public void AddBook(Book book) {
        bookDao.AddBook(book);
    }

    @Override
    public Book getBook(String bookId) {
        return bookDao.getBook(bookId);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Override
    public void delBook(Book book) {
        bookDao.delBook(book);
    }
}
