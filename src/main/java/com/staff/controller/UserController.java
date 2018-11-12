package com.staff.controller;

import com.staff.dao.UserDao;
import com.staff.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @PostMapping("/user")
    public ResponseEntity<?> save(@RequestBody User user) {
        long id = userDao.save(user);
        return ResponseEntity.ok().body("New User has been saved with ID:" + id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable("id") long id) {
        User user = userDao.get(id);
        return ResponseEntity.ok().body(user);
    }
    @GetMapping("/user")
    public ResponseEntity<List<User>> list(@RequestParam(value = "email", defaultValue = "")String email,
                                              @RequestParam(value = "name", defaultValue = "0")String name,
                                              @RequestParam(value = "surname", defaultValue = "0")String surname,
                                              @RequestParam(value = "listUserState", defaultValue = "")List<String> listUserState
    ) {
        User userFilter =new User();
        userFilter.setEmail(email);
        userFilter.setName(name);
        userFilter.setSurname(surname);
        userFilter.setListUserStatus(listUserState);
        List<User> users = userDao.list(userFilter);
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody User user) {
        userDao.update(id, user);
        return ResponseEntity.ok().body("User has been updated successfully.");
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        userDao.delete(id);
        return ResponseEntity.ok().body("User has been deleted successfully.");
    }
}
