package com.staff.controller;

import com.staff.dao.UserDao;
import com.staff.metamodel.User_;
import com.staff.model.User;
import com.staff.util.filtering.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

//    public UserDao getUserDao(){
//        return this.userDao;
//    }

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
                                              @RequestParam(value = "name", defaultValue = "")String name,
                                              @RequestParam(value = "surname", defaultValue = "")String surname,
                                              @RequestParam(value = "listUserState", defaultValue = "")List<String> listUserState,
                                              @RequestParam(value = "sortColumnName", defaultValue = User_.NAME)String sortColumnName,
                                              @RequestParam(value = "order", defaultValue = "ASC")String order,
                                              @RequestParam(value = "page", defaultValue = "1")Integer page,
                                              @RequestParam(value = "pagesize", defaultValue = "10")Integer pagesize
    ) {
        UserUtil userUtil =new UserUtil();
        userUtil.email = email;
        userUtil.name = name;
        userUtil.surname = surname;
        userUtil.listUserStatus = listUserState;
        userUtil.sortColumnName = sortColumnName;
        userUtil.order = order;
        userUtil.page = page;
        userUtil.pagesize = pagesize;

        List<User> users = userDao.list(userUtil);
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
