package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    UserDao userDao;
    User user1;
    User user2;
    User user3;
    @BeforeEach
    void setup(){
        this.userDao = context.getBean("localUserDao",UserDao.class);
        this.user1 =new User("0","nana","123");
        this.user2 =new User("1","rara","456");
        this.user3 =new User("0","mimi","789");
    }

    @Test
    void addAndFindById() throws SQLException, ClassNotFoundException {
//        UserDao userDao = context.getBean("localUserDao",UserDao.class);
//        UserDao userDao = new UserDaoFactory().localUserDao();
        userDao.deleteAll();
        Assertions.assertEquals(0,userDao.getCount());
//        String id ="21";
//        User user =new User(id,"kahyun","123456");
        userDao.add(user1);
        Assertions.assertEquals(1,userDao.getCount());

        User findUser = userDao.findById(user1.getId());
        Assertions.assertEquals(user1.getName(),findUser.getName());
        Assertions.assertEquals(user1.getPassword(),findUser.getPassword());
    }

}