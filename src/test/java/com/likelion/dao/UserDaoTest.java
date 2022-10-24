package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
        userDao.deleteAll();

        userDao.add(user1);
        User user = userDao.findById(user1.getId());

        assertEquals("1111", user1.getName());
        assertEquals("1111", user1.getPassword());

    }

    @Test
    void getAll() {
        userDao.deleteAll();
        List<User> users = userDao.getAll();
        assertEquals(0, users.size());

        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        users = userDao.getAll();
        assertEquals(3, users.size());
    }

    @Test
    void getCount() {
        userDao.deleteAll();
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        assertEquals(3,userDao.getCount());

    }
}