package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    void addAndFindById() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDaoFactory().localUserDao();
        String id ="20";
        User user =new User(id,"kahyun","123456");
        userDao.add(user);

        User findUser = userDao.findById(id);
        Assertions.assertEquals("kahyun",findUser.getName());
    }
}