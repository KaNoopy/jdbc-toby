package dao;

public class UserDaoFactory {
    public UserDao localUserDao(){
        return new UserDao(new LocalConnection());
    }
}
