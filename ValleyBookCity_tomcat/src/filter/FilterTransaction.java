package filter; /**
 * project:atguigu_ValleyBookCity
 * package:${PACKAGE_NAME}
 * class:${NAME}
 *
 * @author: smile
 * @create: 2023/3/29-11:47
 * @Version: v1.0
 * @Description:
 */

import bean.Order;
import dao.BaseDao;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(filterName = "FilterTransaction", urlPatterns = "/Order")
public class FilterTransaction extends BaseDao<Object> implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Connection connection=null;
        try {
            //获取数据库连接
            connection = getConnection();

            //设置为手动提交
            connection.setAutoCommit(false);

            //没有异常就放行
            chain.doFilter(request, response);

            //发生异常就回滚
            connection.commit();


        } catch (Exception e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
