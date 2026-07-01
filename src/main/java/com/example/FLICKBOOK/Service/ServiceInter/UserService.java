package com.example.FLICKBOOK.Service.ServiceInter;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.UserException;
import com.example.FLICKBOOK.Model.User;

@Service
public interface UserService {

    // register user
    public Object registerUser(User registeruser) throws UserException;

    //Login User
    Object LoginUser(User loginuser) throws UserException; 

    // delete user
    public String deleteUser(Integer deleteUser) throws UserException;

}
