package com.staff.dao;

import com.staff.model.User;
import com.staff.util.filtering.UserUtil;

import java.util.List;

public interface UserDao {

    long save(User user);

    User get(long id);

    List<User> list(UserUtil userUtil);

    void update(long id, User vacancy);

    void delete(long id);
}
