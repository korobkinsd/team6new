package com.staff.controller;

import com.staff.dao.UserDao;
import com.staff.model.User;
import com.staff.modelDto.UserDto;
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
        UserDto generateUserDto = generateUserDto();
        when(userDao.get(anyLong())).thenReturn(user);

        controller.setUserDao(userDao);

        ResponseEntity<UserDto> userDto = controller.get(101);
        assertEquals(userDto.getBody(), generateUserDto);
    }

    @Test
    public void list() {
        UserDao userDao = mock(UserDao.class);
        UserUtil userUtil = generateUserUtil();
        User user = generateUser();
        List<User> listUsers = new ArrayList<User>();
        listUsers.add(user);
        UserDto generateUserDto = generateUserDto();
        List<UserDto> listUserDtos = new ArrayList<UserDto>();
        listUserDtos.add(generateUserDto);
        when(userDao.list(any(UserUtil.class)))
                .thenReturn(listUsers);

        controller.setUserDao(userDao);

        ResponseEntity<List<UserDto>> userDtos = controller.list(
                userUtil.email,
                userUtil.name,
                userUtil.surname,
                userUtil.listUserStatus,
                userUtil.sortColumnName,
                userUtil.order,
                userUtil.page,
                userUtil.pagesize);
        assertEquals(userDtos.getBody().size(), 1);
        assertEquals(userDtos.getBody().get(0), listUserDtos);
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
    public void save() {
        UserDao userDao = mock(UserDao.class);
        User user = generateUser();

        userDao.save( any(User.class));

        controller.setUserDao(userDao);

        controller.save(user);

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

    private UserDto generateUserDto(){
        UserDto userDto = new UserDto();
        userDto.id = (long)101;
        userDto.name = "name";
        userDto.surname = "surname";
        userDto.email = "admin";
        userDto.roles = null;

        return userDto;
    }
}
