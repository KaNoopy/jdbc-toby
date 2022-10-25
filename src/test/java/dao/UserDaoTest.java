package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    @Test
    void addAndFindById() throws SQLException, ClassNotFoundException {
        UserDao userDao = context.getBean("localUserDao",UserDao.class);
//        UserDao userDao = new UserDaoFactory().localUserDao();
        String id ="21";
        User user =new User(id,"kahyun","123456");
        userDao.add(user);

        User findUser = userDao.findById(id);
        Assertions.assertEquals("kahyun",findUser.getName());
    }
}