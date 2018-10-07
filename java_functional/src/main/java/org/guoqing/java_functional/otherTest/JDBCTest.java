package org.guoqing.java_functional.otherTest;

import java.sql.*;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-25
 */
public class JDBCTest {
    private static String url = "jdbc:oracle:thin:@10.0.5.24:1521:orcl";
    private static String username = "anytxn_v2_dev";
    private static String password = "jrx123";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try (Connection connection = DriverManager
                .getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT STATEMENT_DATE,CREATE_TIME FROM " +
                             "ACCOUNT_STATEMENT_INFO")) {
            ResultSetMetaData data = resultSet.getMetaData();
            String columnName = data.getColumnName(1);
            System.out.println(columnName);
            System.out.println("======");
            int columnType = data.getColumnType(1);
            System.out.println(columnType);
            String columnName2 = data.getColumnName(2);
            System.out.println(columnName2);
            System.out.println("======");
            int columnType2 = data.getColumnType(2);
            System.out.println(columnType2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
