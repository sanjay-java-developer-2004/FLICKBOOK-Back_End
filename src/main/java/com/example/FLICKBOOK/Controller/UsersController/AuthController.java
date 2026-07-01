package com.example.FLICKBOOK.Controller.UsersController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FLICKBOOK.Exception.AuthException;
import com.example.FLICKBOOK.Service.ServiceInter.AuthService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/authControll")
public class AuthController {

@Autowired
private AuthService authservice;

//get dashboard

@GetMapping("/Dashboard/{theatreid}")
public Object getDashBoard(@PathVariable Integer theatreid) throws AuthException {

    return authservice.getDashBoard(theatreid);
    
}

//geting movies by ststus

@GetMapping("/movies")
public Map<String,Object> getMoviesByStatus(){
    return authservice.getMoviesByStatus();
}

}
