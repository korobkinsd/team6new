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
    public void showAllUsers() {
        UserDao userDao = mock(UserDao.class);
        UserUtil userUtil = generateUser();
        User user = new User();
        List<User> listUsers = new ArrayList<User>();
        listUsers.add(user);
        when(userDao.list(userUtil))
                .thenReturn(listUsers);

        controller.setUserDao(userDao);

        ResponseEntity<List<User>> users = controller.list(userUtil.email, userUtil.name, userUtil.surname,
                userUtil.listUserStatus, userUtil.sortColumnName, userUtil.order, userUtil.page, userUtil.pagesize);

        assertEquals(users.getBody().size(), 1);
        assertEquals(users.getBody().get(1), user);
    }

    private UserUtil generateUser() {
        UserUtil userUtil = new UserUtil();
        userUtil.name = "name";
        userUtil.surname = "surname";
        userUtil.email = "admin";
        userUtil.listUserStatus = new ArrayList<String>();
        userUtil.listUserStatus.add("Active");
        userUtil.listUserStatus.add("Archive");
        userUtil.sortColumnName = "name";
        userUtil.order = "ASC";
        userUtil.page = 1;
        userUtil.pagesize = 5;

        return userUtil;
    }
}
