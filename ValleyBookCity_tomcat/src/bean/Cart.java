package bean;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * project:atguigu_ValleyBookCity
 * package:bean
 * class:Cart
 *
 * @author: smile
 * @create: 2023/3/27-11:18
 * @Version: v1.0
 * @Description:
 */
public class Cart {
    private final Map<Integer, CartIterm> map = new HashMap<>();

    public void addBookToCart(Book book) {
        //如果集合中存在
        if (map.containsKey(book.getBookId())) {
            CartIterm cartIterm = map.get(book.getBookId());
            cartIterm.setCount(cartIterm.getCount() + 1);
        } else {
            CartIterm cartIterm = new CartIterm(book, 1);
            map.put(book.getBookId(), cartIterm);
        }
    }

    public Integer getCount() {
        Set<Map.Entry<Integer, CartIterm>> entries = map.entrySet();
        Integer count = 0;
        for (Map.Entry<Integer, CartIterm> entry : entries) {
            count += entry.getValue().getCount();
        }
        return count;
    }

    public Double getAmount() {
        Set<Map.Entry<Integer, CartIterm>> entries = map.entrySet();
        BigDecimal amount = BigDecimal.valueOf(0.0);
        for (Map.Entry<Integer, CartIterm> entry : entries) {
            BigDecimal amountIterm = BigDecimal.valueOf(entry.getValue().getAmount());
            amount = amount.add(amountIterm);
        }
        return amount.doubleValue();
    }

    public List<CartIterm> getCartIterm() {
        return new ArrayList<>(map.values());
    }

    public void delBook(Book book) {
        map.remove(book.getBookId());
    }

    public void addCount(Integer id) {
        CartIterm cartIterm = map.get(id);
        cartIterm.setCount(cartIterm.getCount() + 1);
    }

    public void subCount(Integer id) {
        CartIterm cartIterm = map.get(id);
        cartIterm.setCount(cartIterm.getCount() - 1);
    }

    public void changeCount(Integer id, Integer count) {
        CartIterm cartIterm = map.get(id);
        cartIterm.setCount(count);
    }
}
