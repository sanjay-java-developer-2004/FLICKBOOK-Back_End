package com.example.FLICKBOOK.Service.ServiceInter;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.UserException;
import com.example.FLICKBOOK.Model.User;

@Service
public interface UserService {

    // register user
    public Object registerUser(User registeruser) throws UserException;

    // //login user
    // public String LoginUser( User loginuser) throws UserException;

    // Service Interface — change String to Object
    
        Object LoginUser(User loginuser) throws UserException; // ✅ Object not String
   

    // delete user
    public String deleteUser(Integer deleteUser) throws UserException;

}
