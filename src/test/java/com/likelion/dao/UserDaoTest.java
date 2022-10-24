package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        userDao = context.getBean("awsUserDao", UserDao.class);
        user1 = new User("1","1111","1111");
        user2 = new User("2","2222","2222");
        user3 = new User("3","3333","3333");
    }

    @Test
    void addAndGet() {
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.add(user1);
        User user = userDao.findById(user1.getId());

        assertEquals("1111", user.getName());
        assertEquals("1111", user.getPassword());

        userDao.deleteAll();
    }
}