package com.zwm;

import com.zwm.mapper.UserMapper;
import com.zwm.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class App20 {
    public static void main(String[] args) throws IOException {
        String mybatisConfig = "mybatis.xml";
        InputStream mybatisInputStream = Resources.getResourceAsStream(mybatisConfig);
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(mybatisInputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.selectAllUsers();
        for (User user : userList) System.out.println(user.toString());
    }
}
