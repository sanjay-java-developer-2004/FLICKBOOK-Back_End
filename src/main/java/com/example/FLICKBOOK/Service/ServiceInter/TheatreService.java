package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.TheatreException;
import com.example.FLICKBOOK.Model.Theatre;

@Service
public interface TheatreService {

    //add
     public Object AddTheatre(Theatre theatre ,Integer userId) throws TheatreException;

     //delete
      public String DeleteTheatre (String tname) throws TheatreException ;

      //get by name
      public Optional<Theatre> GetTheatre(String tname) throws TheatreException ;

}
