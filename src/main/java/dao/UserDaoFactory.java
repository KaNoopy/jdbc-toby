package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao localUserDao(){
        return new UserDao(new LocalConnection());
    }
}
