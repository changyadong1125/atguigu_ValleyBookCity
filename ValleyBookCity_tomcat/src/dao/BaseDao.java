package dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

  /**
 * project:atguigu_ValleyBookCity
 * package:dao
 * class:baseDAO
 *
 * @author: smile
 * @create: 2023/3/19-13:18
 * @Version: v1.0
 * @Description:
 */
public class BaseDao<T> {
    private static final DataSource DATA_SOURCE;
    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();
    private  final Class<?> clazz;
    private static final QueryRunner queryRunner = new QueryRunner();

    static {
        Properties properties = new Properties();
        try {
            properties.load(BaseDao.class.getClassLoader().getResourceAsStream("db.properties"));
            DATA_SOURCE = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BaseDao() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType Superclass = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = Superclass.getActualTypeArguments();
        String typeName = actualTypeArguments[0].getTypeName();
        try {
            clazz = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        Connection connection = THREAD_LOCAL.get();
        if (connection == null) {
            try {
                connection = DATA_SOURCE.getConnection();
                THREAD_LOCAL.set(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public void close() {
        Connection connection = THREAD_LOCAL.get();
        if (connection != null) {
            try {
                connection.close();
                THREAD_LOCAL.remove();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @SuppressWarnings("ALL")
    public List<T> getList(String sql, Object... params) {
        Connection connection = getConnection();
        System.out.println(clazz);
        try {
            List<?> query = queryRunner.query(connection, sql, new BeanListHandler<>(clazz), params);

            return (List<T>) query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressWarnings("ALL")
    public T getBean(String sql, Object... params) {
        Connection connection = getConnection();
        try {
            Object query = queryRunner.query(connection, sql, new BeanHandler<>(clazz), params);
            return (T)query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getObject(String sql, Object... params) {
        Connection connection = getConnection();
        try {
            return queryRunner.query(connection, sql, new ScalarHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBean(String sql, Object... params) {
        Connection connection = getConnection();
        try {
            queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
