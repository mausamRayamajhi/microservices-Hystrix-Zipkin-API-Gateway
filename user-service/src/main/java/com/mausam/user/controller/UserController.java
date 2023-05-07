package com.mausam.user.controller;

import com.mausam.user.entity.User;
import com.mausam.user.service.UserService;
import com.mausam.user.util.API;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API.DEFAULT)
@Slf4j
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(API.USER)
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<>(userService.findByEmailAndStatus("noEmail", 1), HttpStatus.OK);
    }


    @PutMapping(API.UPDATE_USER)
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        System.out.println("upadate user = " + user.getUserId());
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @GetMapping(API.BY_ID_USER)
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserInfo(id);
    }

    @GetMapping(API.VERIFY_USER)
    public boolean verifyUser(@PathVariable("id") Integer id) {
        return userService.verifyUser(id);
    }

    @GetMapping(API.BY_ROLE_USER)
    public ResponseEntity<Page<User>> findByRole(@PathVariable("role") String role, @PathVariable("page") Integer page, @PathVariable("maxData") Integer maxData, @PathVariable("sort") String sort) {
        return new ResponseEntity<>(userService.findByRole("ROLE_" + role, PageRequest.of(page, maxData, Sort.Direction.ASC, sort)), HttpStatus.OK);
    }

    @GetMapping(API.BY_STATUS_USER)
    public ResponseEntity<Page<User>> verifyUser(@PathVariable("status") Integer status, @PathVariable("page") Integer page, @PathVariable("maxData") Integer maxData, @PathVariable("sort") String sort) {
        return new ResponseEntity<>(userService.findByStatus(status, PageRequest.of(page, maxData, Sort.Direction.ASC, sort)), HttpStatus.OK);
    }

    @PostMapping(API.ADD_USER_GENERAL)
    public long addUser(@RequestBody User user) {
        return userService.addNewCustomer(user);
    }

    @PostMapping(API.ADD_USER_BY_ADMIN)
    public long addUserAdmin(@RequestBody User user) {
        return userService.addNewAdminStaffDelivery(user);
    }

    @GetMapping(API.BY_PAGE_USER)
    public ResponseEntity<Page<User>> findAllUser(@PathVariable("page") Integer page, @PathVariable("maxData") Integer maxData, @PathVariable("sort") String sort) {
        return new ResponseEntity<>(userService.findAllUser(PageRequest.of(page, maxData, Sort.Direction.ASC, sort)), HttpStatus.OK);
    }


//    @PostMapping("/")
//    public User saveUser(@RequestBody User user) {
//        log.info("Inside saveUser of UserController");
//        return userService.saveUser(user);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId) {
//        log.info("Inside getUserWithDepartment of UserController");
//        return userService.getUserWithDepartment(userId);
//    }
//
//    @GetMapping("/user/{id}")
//    public User getUser(@PathVariable("id") Long userId) {
//        return userService.getUser(userId);
//    }
//
//    @GetMapping("/all")
//    public List<User> getAllUwr() {
//        return userService.getAllUser();
//    }

}
