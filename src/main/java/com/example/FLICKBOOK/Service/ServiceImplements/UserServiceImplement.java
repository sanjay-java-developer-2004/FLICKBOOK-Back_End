package com.example.FLICKBOOK.Service.ServiceImplements;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.UserException;
import com.example.FLICKBOOK.Model.Theatre;
import com.example.FLICKBOOK.Model.User;
import com.example.FLICKBOOK.Repository.TheatreRepository;
import com.example.FLICKBOOK.Repository.UserRepository;
import com.example.FLICKBOOK.Security.LoginSecurityJWT;
import com.example.FLICKBOOK.Service.ServiceInter.UserService;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TheatreRepository theatrreepository;

    @Autowired
    private LoginSecurityJWT loginsecurityjwt;

    // register
    @Override
    public Object registerUser(User registeruser) throws UserException {

        if (registeruser == null) {
            throw new UserException("User data must not be null");
        }

        System.out.println("Register user not null");

        // Check for real null, empty strings, and the literal string "null"
        if (registeruser.getUsername().trim().isEmpty() || registeruser.getUsername().equalsIgnoreCase("null")
                || registeruser.getPassword().trim().isEmpty() || registeruser.getPassword().equalsIgnoreCase("null")
                || registeruser.getEmail().trim().isEmpty() || registeruser.getEmail().equalsIgnoreCase("null")
                || registeruser.getPhonenumber().trim().isEmpty()
                || registeruser.getPhonenumber().equalsIgnoreCase("null")) {

            throw new UserException("The Value must not be null or empty");
        }

        System.out.println("details not null");

        Optional<User> temp = userRepository.findByUsername(registeruser.getUsername());

        if (!temp.isEmpty()) {
            throw new UserException("User already exists with this username. Try another username.");
        }

        System.out.println("username not contain ");

        User stored = userRepository.save(registeruser);
        System.out.println("saved");

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("userid", stored.getUserid());
        response.put("username", stored.getUsername());
        response.put("role", stored.getRole());

    
        return response;

    }

    // delete
    @Override
    public String deleteUser(Integer deleteUser) throws UserException {

        boolean check = userRepository.existsById(deleteUser);

        if (check) {
            userRepository.deleteById(deleteUser);
            return "User Account Deleted Successfully";
        } else {
            throw new UserException("Account Not Founded");
        }

    }


    
    @Override
    public Object LoginUser(User loginuser) throws UserException {

    User temp = userRepository.findByUsernameAndPassword(
                    loginuser.getUsername(), loginuser.getPassword());

    if (temp != null) {

        //jwt token generation

        loginsecurityjwt.GenerateToken(temp.getUsername());

        
        // find theatre linked to this user
        Theatre theatre = theatrreepository.findByUser_Userid(temp.getUserid());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("username", temp.getUsername());  // ✅ for context
        response.put("userid", temp.getUserid());      //
         response.put("role", temp.getRole());          // ✅ for redirect
        response.put("theatreid",
        theatre != null ? theatre.getTheaterid() : null);           // ✅ null if user or new admin

        response.put("theatrename", theatre !=null ? theatre.getTheatername() : null);

        System.out.println("Userid : " +temp.getUserid());
        System.out.println("Username : "+temp.getUsername());
        System.out.println("role : "+temp.getRole());

        return response;

    } else {
        throw new UserException("Username And Password Are Not Same");
    }
}

}
