package kr.ac.jejunu.user;

import java.sql.*;

public class UserDao {
    private final JejuJdbcTemplate jdbcTemplate;

    public UserDao(JejuJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User get(Integer id) throws SQLException {
        Object[] params = new Object[] {id};
        String sql = "select * from userinfo where id = ?";
        return jdbcTemplate.query(sql, params, rs -> {
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        });
    }

    public Integer insert(User user) throws SQLException {
        Object[] params = new Object[] {user.getName(), user.getPassword()};
        String sql = "insert into userinfo (name, password) values (?, ?)";

        return jdbcTemplate.insert(sql, params);
    }

    public void update(User user) throws SQLException {
        Object[] params = new Object[] {user.getName(), user.getPassword(), user.getId()};
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        jdbcTemplate.update(sql, params);
    }

    public void delete(Integer id) throws SQLException {
        Object[] params = new Object[] {id};
        String sql = "delete userinfo where id = ?";
        jdbcTemplate.update(sql, params);
    }

}
