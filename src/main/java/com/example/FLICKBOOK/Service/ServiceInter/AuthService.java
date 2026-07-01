package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.AuthException;

@Service
public interface AuthService {

    public Object getDashBoard(Integer theatreid) throws AuthException ;

    //get movies
    public Map<String,Object> getMoviesByStatus() ;

}
