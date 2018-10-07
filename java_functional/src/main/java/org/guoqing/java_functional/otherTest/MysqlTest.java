package org.guoqing.java_functional.otherTest;

import java.sql.*;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-25
 */
public class MysqlTest {
    private static String url =
            "jdbc:mysql://localhost:3306/order?useUnicode=true&allowMultiQueries=true&characterEncoding=utf8&useSSL=true";
    private static String username = "root";
    private static String password = "123456";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName(driver);

        try (Connection connection = DriverManager
                .getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT order_status,create_time FROM " + "t_order")) {
            ResultSetMetaData data = resultSet.getMetaData();
            String columnName = data.getColumnName(1);
            System.out.println(columnName);
            System.out.println("=======");
            int columnType = data.getColumnType(1);
            System.out.println(columnType);
            String columnName2 = data.getColumnName(2);
            System.out.println(columnName2);
            System.out.println("=======");
            int columnType2 = data.getColumnType(2);
            System.out.println(columnType2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
