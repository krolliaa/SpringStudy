package com.zwm;

import java.sql.*;

public class App19 {
    public static void main(String[] args) throws SQLException {
        //1.注册驱动
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        //2.创建连接
        String url = "jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";
        //3.获取连接对象
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL
        String sql = "select * from student";
        //5.处理结果集
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println(id + " " + name + " " + age);
        }
        //6.关闭资源
        if (resultSet != null) resultSet.close();
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
    }
}
