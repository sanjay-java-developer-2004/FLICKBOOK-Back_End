package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    public Object getDashBoard(Integer theatreid) throws Exception ;

    //get movies
    public Map<String,Object> getMoviesByStatus() ;

}
