package main.java.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC工具类（适配华为云RDS MySQL）
 * 设计原则：面向对象封装/单例模式，基于Druid连接池提升华为云部署性能
 * 注意：需提前引入Druid依赖（Maven/Gradle），适配华为云RDS的SSL/超时配置
 */
public class JDBCUtil {
    // 私有静态数据源（单例，面向对象封装特性）
    private static DruidDataSource dataSource;

    // 静态代码块：加载配置文件 + 初始化华为云RDS数据源（仅执行一次）
    static {
        try {
            // 1. 加载classpath下的db.properties配置文件（对应之前的数据库配置）
            Properties props = new Properties();
            props.load(JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties"));

            // 2. 初始化Druid数据源（适配华为云RDS连接特性）
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(props);

            // 华为云RDS专属配置（可选，增强连接稳定性）
            dataSource.setValidationQuery("SELECT 1"); // 连接有效性检测（华为云推荐）
            dataSource.setTestOnBorrow(true); // 获取连接时检测有效性
            dataSource.setTestOnReturn(false); // 归还连接时不检测（提升性能）
            dataSource.setTimeBetweenEvictionRunsMillis(60000); // 60秒检测一次空闲连接
            dataSource.setMinEvictableIdleTimeMillis(300000); // 空闲连接超时时间（5分钟）

        } catch (Exception e) {
            // 异常封装：适配华为云日志排查，抛出运行时异常终止初始化
            throw new RuntimeException("华为云RDS数据源初始化失败！", e);
        }
    }

    /**
     * 私有构造方法：禁止外部实例化（单例模式，面向对象封装）
     */
    private JDBCUtil() {
    }

    /**
     * 获取数据库连接（从Druid连接池获取，适配华为云RDS）
     * 
     * @return Connection 数据库连接对象
     * @throws SQLException 连接获取失败异常
     */
    public static Connection getConnection() throws SQLException {
        // 从连接池获取连接，而非新建连接（华为云生产环境推荐）
        Connection conn = dataSource.getConnection();
        // 华为云RDS MySQL 8.x 时区适配（防止时间字段偏移）
        conn.setAutoCommit(true); // 默认自动提交事务
        return conn;
    }

    /**
     * 关闭资源（ResultSet + Statement + Connection）
     * 面向对象重载特性：适配不同资源组合的关闭场景
     * 
     * @param rs   结果集对象
     * @param stmt 执行语句对象
     * @param conn 连接对象
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        // 关闭结果集
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭执行语句
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭连接（归还到连接池，非真正关闭）
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重载：关闭资源（Statement + Connection）
     * 
     * @param stmt 执行语句对象
     * @param conn 连接对象
     */
    public static void close(Statement stmt, Connection conn) {
        close(null, stmt, conn);
    }

    /**
     * 重载：关闭资源（仅Connection）
     * 
     * @param conn 连接对象
     */
    public static void close(Connection conn) {
        close(null, null, conn);
    }

    /**
     * 获取数据源对象（供框架/高级场景使用）
     * 
     * @return DruidDataSource 数据源
     */
    public static DruidDataSource getDataSource() {
        return dataSource;
    }

    /**
     * 销毁数据源（应用关闭时调用，释放华为云RDS连接资源）
     */
    public static void destroyDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
