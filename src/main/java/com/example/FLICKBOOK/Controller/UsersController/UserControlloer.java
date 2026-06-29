package com.example.FLICKBOOK.Controller.UsersController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FLICKBOOK.Exception.UserException;
import com.example.FLICKBOOK.Model.User;
import com.example.FLICKBOOK.Service.ServiceInter.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserControlloer {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register")
    public Object registerUser(@RequestBody User registeruser) throws UserException {
        return userService.registerUser(registeruser);

    }

    // //login User
    // @PostMapping("/login")
    // public String LoginUser(@RequestBody User loginuser) throws UserException{
    // return userService.LoginUser(loginuser);

    // }

    // Controller — change String to Object
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) throws UserException {
        return ResponseEntity.ok(userService.LoginUser(user)); // ✅
    }

    // deleteUser
    @DeleteMapping("/delete/{deleteUser}")
    public String deleteUser(@PathVariable Integer deleteUser) throws UserException {
        return userService.deleteUser(deleteUser);

    }

}
