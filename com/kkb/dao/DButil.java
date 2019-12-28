package com.kkb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DButil {

    private String ip = "127.0.0.1";
    private int port = 3306;
    private String userName = "root";
    private String password = "root";
    private String charset = "utf-8";
    private String dbName = "hello";

    public DButil(String ip, int port, String userName, String password, String charset, String dbName) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.charset = charset;
        this.dbName = dbName;
    }

    public DButil() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("驱动加载成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动程序失败！");
            e.printStackTrace();
        }
    }

    /**
     * 获取连接对象
     * @return
     */
    private Connection getConnection() {
        String jdbcStr = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", ip, port, dbName, userName, password);
        System.out.println(jdbcStr);
        try {
            return DriverManager.getConnection(jdbcStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行查询,返回List集合
     * @return
     */
    public List<Map<String,Object>> executeQuery(String sql,Object ...args) {
        ArrayList<Map<String,Object>> result = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement prepareStatement = getPrepareStatement(connection, sql, args);
        ResultSet resultSet = null;
        try {
            resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                HashMap<String, Object> hashMap = new HashMap<>();
                for (int i = 1;i <= columnCount;i++) {
                    hashMap.put(resultSet.getMetaData().getColumnName(i),resultSet.getObject(i));
                }
                result.add(hashMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                if(prepareStatement != null) {
                    prepareStatement.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 获取预处理函数对象
     * @param connection
     * @param sql
     * @param args
     */
    public PreparedStatement getPrepareStatement(Connection connection, String sql, Object[] args) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int count = sql.length() - sql.replace("?","").length();
            if(count != args.length) {
                throw new SQLException("DBUtil Error 参数个数不匹配！");
            }
            for(int i = 0;i < args.length;i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新查询语句
     * @return
     */
    public boolean executeUpdate(String sql, Object ...args) {
        try {
            Connection connection = getConnection();
            PreparedStatement prepareStatement = getPrepareStatement(connection, sql, args);
            int i = prepareStatement.executeUpdate();
            if(i > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
