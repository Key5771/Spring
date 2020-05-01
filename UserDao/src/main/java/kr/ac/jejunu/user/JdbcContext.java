package kr.ac.jejunu.user;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    User jdbcContextForGet(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            // mysql
            // driver 로딩
            connection = dataSource.getConnection();
            // query
            preparedStatement = statementStrategy.makeStatement(connection);
            // 실행
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // 결과매핑
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
            // 자원해지
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    void jdbcContextForInsert(User user, StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void jdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    User get(String sql, Object[] param) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                preparedStatement.setObject(i + 1, param[i]);
            }
            return preparedStatement;
        };
        // 결과리턴
        return jdbcContextForGet(statementStrategy);
    }

    void insert(User user, Object[] param, String sql) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < param.length; i++) {
                preparedStatement.setObject(i + 1, param[i]);
            }
            return preparedStatement;
        };
        jdbcContextForInsert(user, statementStrategy);
    }

    void update(String sql, Object[] param) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                preparedStatement.setObject(i + 1, param[i]);
            }
            return preparedStatement;
        };
        jdbcContextForUpdate(statementStrategy);
    }
}