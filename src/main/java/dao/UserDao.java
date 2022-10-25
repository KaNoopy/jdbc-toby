package dao;

import domain.User;
import net.bytebuddy.agent.VirtualMachine;

import java.sql.*;
import java.util.Map;

public class UserDao {
    ConnectonMaker connectonMaker;

    public UserDao(LocalConnection localConnection) {
        this.connectonMaker = new LocalConnection();
    }

    public void deleteAll() throws SQLException {
        Connection c = connectonMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("delete from user");
        ps.executeUpdate();
        ps.close();
        c.close();
    }
    public int getCount() throws SQLException {
        Connection c = connectonMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("select count(*) from user");
        ResultSet rs =ps.executeQuery();
        rs.next();
        int cnt = rs.getInt(1);
        rs.close();
        ps.close();
        c.close();
        return cnt;
    }
    public void add(User user) throws SQLException {
        Connection c = connectonMaker.makeConnection();
        PreparedStatement ps =c.prepareStatement("insert into user(id,name,password) values(?,?,?);");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public User findById(String id) throws SQLException, ClassNotFoundException {
        Connection c = connectonMaker.makeConnection();

        Class.forName("com.mysql.cj.jdbc.Driver");

        PreparedStatement ps = c.prepareStatement(
                "select * from user where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
//jdbc:mysql://127.0.0.1:3306/?user=root