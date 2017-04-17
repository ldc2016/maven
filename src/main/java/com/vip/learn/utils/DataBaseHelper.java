package com.vip.learn.utils;

import com.sun.corba.se.spi.copyobject.CopyobjectDefaults;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by dacheng.liu on 2017/4/17.
 */
public final class DataBaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);

    private static final QueryRunner QUERY_RUNNER ;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER ;  // 确保一个线程只有一个connection,隔离线程的容器
    private static final BasicDataSource DATA_SOURCE;                 // 使用数据库连接池，优化高并发时的数据库IO瓶颈

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static{
        QUERY_RUNNER = new QueryRunner();
        CONNECTION_HOLDER = new ThreadLocal<>();

        Properties props = PropertiesUtil.loadProps("dbConfig.properties");
        DRIVER = props.getProperty("jdbc.driver");
        URL = props.getProperty("jdbc.url");
        USERNAME = props.getProperty("jdbc.username");
        PASSWORD = props.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

    }

    /**
     * 获取数据库连接并将其绑定到线程隔离容器ThreadLocal
     * 通过threadLocal实现connection对调用者的透明化
     * @return
     */
    public static Connection getConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if(connection == null){
            try{
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection error!",e);
                throw new RuntimeException();
            } finally {
                CONNECTION_HOLDER.set(connection); // 放入线程隔离容器，确保一个线程只有一个数据库连接
            }
        }

        return connection;
    }

    /**
     * 从ThredaLocal中获取connection并关闭数据库连接
     */
    public static void closeConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if(connection != null){
            try{
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection error!",e);
                throw new RuntimeException();
            }finally {
                CONNECTION_HOLDER.remove(); // 当当前线程使用完了connection后，需要移除ThreadLocal中的Connection
            }
        }

    }


    /**
     * 查询实体List
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
        List<T> entryList;
        try {
            Connection conn = getConnection();
            entryList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        }catch (SQLException e){
            LOGGER.error("query entity list failure!",e);
            throw new RuntimeException();
        }finally {
            closeConnection();
        }

        return entryList;
    }

    /**
     * 查询实体对象
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        }catch (SQLException e){
            LOGGER.error("query entity failure!",e);
            throw new RuntimeException();
        }finally {
            closeConnection();
        }

        return entity;
    }

}
