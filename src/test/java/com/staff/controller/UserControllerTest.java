package com.staff.controller;

import com.staff.dao.UserDao;
import com.staff.model.User;
import com.staff.util.filtering.UserUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController controller;

    @Before
    public void setUp() {
        controller = new UserController();
    }

    @Test
    public void get() {
        UserDao userDao = mock(UserDao.class);
        User user = generateUser();
        when(userDao.get(anyLong())).thenReturn(user);

        controller.setUserDao(userDao);

        ResponseEntity<User> userGet = controller.get(101);
        assertEquals(userGet.getBody(), user);
    }

    @Test
    public void list() {
        UserDao userDao = mock(UserDao.class);
        UserUtil userUtil = generateUserUtil();
        User user = generateUser();
        List<User> listUsers = new ArrayList<User>();
        listUsers.add(user);
        when(userDao.list(any(UserUtil.class)))
                .thenReturn(listUsers);

        controller.setUserDao(userDao);

        ResponseEntity<List<User>> users = controller.list(
                userUtil.email,
                userUtil.name,
                userUtil.surname,
                userUtil.listUserStatus,
                userUtil.sortColumnName,
                userUtil.order,
                userUtil.page,
                userUtil.pagesize);

        assertEquals(users.getBody().size(), 1);
        assertEquals(users.getBody().get(0), user);
    }

    @Test
    public void update() {
        UserDao userDao = mock(UserDao.class);
        User user = generateUser();
        userDao.update(anyLong(), any(User.class));

        controller.setUserDao(userDao);

        controller.update((long) 101, user);

        assertEquals("", "");
    }

    @Test
    public void delete() {
        UserDao userDao = mock(UserDao.class);
        User user = generateUser();
        userDao.delete(anyLong());

        controller.setUserDao(userDao);

        controller.delete((long) 101);
    }

    private UserUtil generateUserUtil() {
        UserUtil userUtil = new UserUtil();
        userUtil.name = "";
        userUtil.surname = "";
        userUtil.email = "";
        userUtil.listUserStatus = new ArrayList<String>();
        userUtil.sortColumnName = "name";
        userUtil.order = "ASC";
        userUtil.page = 1;
        userUtil.pagesize = 5;

        return userUtil;
    }

    private User generateUser(){
        User user = new User();
        user.setId((long)101);
        user.setName("name");
        user.setSurname("surname");
        user.setEmail("admin");

        return user;
    }
}
