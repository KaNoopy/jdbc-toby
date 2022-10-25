package dao;

import domain.User;
import net.bytebuddy.agent.VirtualMachine;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from user");
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("select count(*) from user;",Integer.class);
    }


    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update("insert into user(id,name,password) values(?,?,?);",
                user.getId(), user.getName(),user.getPassword());
    }

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
            return user;
        }
    };
    public User findById(String id) throws SQLException, ClassNotFoundException {
        String sql="select * from user where id =?";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<User> getAll(){
        String sql="select * from user order by id";
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}
