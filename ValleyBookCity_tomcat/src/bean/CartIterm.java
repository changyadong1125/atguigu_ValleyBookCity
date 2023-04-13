package bean;

import java.math.BigDecimal;

/**
 * project:atguigu_ValleyBookCity
 * package:bean
 * class:CartIterm
 *
 * @author: smile
 * @create: 2023/3/27-11:18
 * @Version: v1.0
 * @Description:
 */
public class CartIterm {
    private Book book;
    private Integer count;
    private Double amount;

    public CartIterm() {
    }

    public CartIterm(Book book, Integer count) {
        this.book = book;
        this.count = count;
        BigDecimal bigDecimal = BigDecimal.valueOf(book.getPrice());
        BigDecimal countBig = new BigDecimal(count);
        amount=bigDecimal.multiply(countBig).doubleValue();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        amount=count*book.getPrice();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        BigDecimal bigDecimal = BigDecimal.valueOf(book.getPrice());
        BigDecimal countBig = new BigDecimal(count);
        amount=bigDecimal.multiply(countBig).doubleValue();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartIterm{" +
                "book=" + book +
                ", count=" + count +
                ", Amount=" + amount +
                '}';
    }
}
