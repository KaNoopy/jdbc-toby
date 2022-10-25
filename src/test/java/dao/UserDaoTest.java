package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    void addAndFindById() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user =new User("16","kahyun","123456");
        userDao.add(user);

        User findUser = userDao.findById("16");
        Assertions.assertEquals("kahyun",findUser.getName());
    }
}