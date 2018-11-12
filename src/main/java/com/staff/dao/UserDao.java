package com.staff.dao;

import com.staff.model.User;

import java.util.List;

public interface UserDao {

    long save(User vacancy);

    User get(long id);

    List<User> list(User vacancy);

    void update(long id, User vacancy);

    void delete(long id);
}
