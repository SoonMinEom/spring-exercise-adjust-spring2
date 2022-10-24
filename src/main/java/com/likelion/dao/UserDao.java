package com.likelion.dao;


import com.likelion.domain.User;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Map;

public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy ss) {

        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = ss.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void add(User user) {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {

                PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());

                return pstmt;
            }
        });
    }

    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            // DB접속 (ex sql workbeanch실행)
            c = dataSource.getConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {

        //jdbcContextWithStatementStrategy(new DeleteAllStrategy());
        jdbcContextWithStatementStrategy((new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement("DELETE FROM users");
            }
        }));
    }

    public static void main(String[] args) {

//        UserDaoFactory udf = new UserDaoFactory();
//        UserDao userDao = udf.awsUserDao();
//        userDao.add(user);
    }
}
