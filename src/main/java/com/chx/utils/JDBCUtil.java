package com.chx.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 基于 Druid 连接池的 JDBC 工具类，读取 classpath 下的 druid.properties
 */
public class JDBCUtil {

    private static final DataSource DATA_SOURCE;

    static {
        try {
            Properties props = new Properties();
            try (InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties")) {
                if (in == null) {
                    throw new RuntimeException("找不到 druid.properties，请检查 src/main/resources 目录");
                }
                props.load(in);
            }
            DATA_SOURCE = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignored) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }
}
