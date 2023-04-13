package dao;

import bean.Book;

import java.sql.*;
import java.util.List;

/**
 * project:atguigu_ValleyBookCity
 * package:dao
 * class:BookDao
 *
 * @author: smile
 * @create: 2023/3/24-9:59
 * @Version: v1.0
 * @Description:
 */
public class BookDao extends BaseDao<Book> implements BookInterface {
    @Override
    public List<Book> getAllBooks() {
        String sql = "select id bookId ,title bookName ,author,price,sales,stock,img_path imgPath from books";
        List<Book> list = this.getList(sql);
        close();
        return list;
    }

    @Override
    public void AddBook(Book book) {
        String sql = "insert into books values(null,?,?,?,?,?,?)";
        Connection connection = getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setObject(1, book.getBookName());
            pst.setObject(2, book.getAuthor());
            pst.setObject(3, book.getPrice());
            pst.setObject(4, book.getSales());
            pst.setObject(5, book.getStock());
            pst.setObject(6, book.getImgPath());
            pst.executeUpdate();
            ResultSet keys = pst.getGeneratedKeys();
            if (keys.next()) {
                book.setBookId(keys.getInt(1));
            }
            keys.close();
            pst.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    @Override
    public Book getBook(String bookId) {
        String sql = "select id bookId ,title bookName ,author,price,sales,stock,img_path imgPath from books where id=?";
        Book bean = getBean(sql, bookId);
        close();
        return bean;
    }

    @Override
    public void update(Book book) {
        String sql = "update books set title=?  ,author=?,price=?,sales=?,stock=? where id=?";
        this.setBean(sql, book.getBookName(), book.getAuthor(), book.getPrice(), book.getSales(), book.getStock(), book.getBookId());
    }

    @Override
    public void delBook(Book book) {
        String sql = "delete from books where id=?";
        this.setBean(sql, book.getBookId());
        close();
    }
}
