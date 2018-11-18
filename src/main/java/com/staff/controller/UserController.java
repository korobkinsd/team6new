package com.staff.controller;

import com.staff.dao.UserDao;
import com.staff.metamodel.User_;
import com.staff.model.Role;
import com.staff.model.User;
import com.staff.modelDto.UserDto;
import com.staff.util.filtering.UserUtil;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public UserDao getUserDao(){
        return this.userDao;
    }

    @PostMapping("/user")
    public ResponseEntity<?> save(@RequestBody User user) {
        long id = userDao.save(user);
        return ResponseEntity.ok().body("New User has been saved with ID:" + id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> get(@PathVariable("id") long id) {
        User user = userDao.get(id);

        UserDto userDto = this.generateUserDto(user);
        return ResponseEntity.ok().body(userDto);
    }
    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> list(@RequestParam(value = "email", defaultValue = "")String email,
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

        List<UserDto> userDtos = new ArrayList<UserDto>();
        for (User user: users) {
            userDtos.add(this.generateUserDto(user));
        }
        return ResponseEntity.ok().body(userDtos);
    }

    private UserDto generateUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.name = user.getName();
        userDto.email = user.getEmail();
        userDto.surname = user.getSurname();
        userDto.userState = user.getUserState();
        List<Role> roles = user.getRoles();
        if (roles != null && roles.size() > 0){
            userDto.roles = (List)new ArrayList<Role>();
            for (Role role: roles){
                userDto.roles.add(role.getName());
            }
        }
        return userDto;
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
