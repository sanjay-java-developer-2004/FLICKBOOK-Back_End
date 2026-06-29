package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Model.Theatre;

@Service
public interface TheatreService {

    //add
     public Object AddTheatre(Theatre theatre ,Integer userId) throws Exception;

     //delete
      public String DeleteTheatre (String tname) throws Exception ;

      //get by name
      public Optional<Theatre> GetTheatre(String tname) throws Exception ;

}
