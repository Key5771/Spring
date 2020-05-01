package kr.ac.jejunu.user;

import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(Integer id) throws SQLException {
        Object[] param = new Object[] {id};
        String sql = "select id, name, password from userinfo where id = ?";
        return jdbcContext.get(sql, param);
    }

    public void insert(User user) throws SQLException {
        Object[] param = new Object[] {user.getName(), user.getPassword()};
        String sql = "insert into userinfo (name, password) values (?, ?)";
        jdbcContext.insert(user, param, sql);
    }

    public void update(User user) throws SQLException {
        Object[] param = new Object[] {user.getName(), user.getPassword(), user.getId()};
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        jdbcContext.update(sql, param);
    }

    public void delete(Integer id) throws SQLException {
        Object[] param = new Object[] {id};
        String sql = "delete from userinfo where id = ?";
        jdbcContext.update(sql, param);
    }


}
