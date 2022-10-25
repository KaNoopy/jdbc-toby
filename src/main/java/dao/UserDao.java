package dao;

import domain.User;
import net.bytebuddy.agent.VirtualMachine;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {
    ConnectonMaker connectonMaker;

    public UserDao(LocalConnection localConnection) {
        this.connectonMaker = new LocalConnection();
    }
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt)throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = connectonMaker.makeConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    public void deleteAll() throws SQLException {
//        Connection c = null;
//        PreparedStatement ps = null;
//
//        try {
//            c = connectonMaker.makeConnection();
//            ps = new DeleteAllStrategy().makePreparedStatement(c);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
        jdbcContextWithStatementStrategy(new DeleteAllStrategy());
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int cnt = 0;
        try {
            c = connectonMaker.makeConnection();
            ps = c.prepareStatement("select count(*) from user");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                    }
                }
                if (c != null) {
                    try {
                        c.close();
                    } catch (SQLException e) {
                    }
                }
            }
        }
    }

    public void add(User user) throws SQLException {
        jdbcContextWithStatementStrategy(new AddStrategy(user));
//        Connection c = connectonMaker.makeConnection();
//        PreparedStatement ps = new AddStrategy(user).makePreparedStatement(c);
////        ps.setString(1, user.getId());
////        ps.setString(2, user.getName());
////        ps.setString(3, user.getPassword());
//        ps.executeUpdate();
//        ps.close();
//        c.close();
    }

    public User findById(String id) throws SQLException, ClassNotFoundException {
        Connection c = connectonMaker.makeConnection();

        Class.forName("com.mysql.cj.jdbc.Driver");

        PreparedStatement ps = c.prepareStatement(
                "select * from user where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return user;
    }
}
