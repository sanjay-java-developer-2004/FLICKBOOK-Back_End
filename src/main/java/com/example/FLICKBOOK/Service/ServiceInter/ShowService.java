package com.example.FLICKBOOK.Service.ServiceInter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.ShowException;
import com.example.FLICKBOOK.Model.Show;

@Service
public interface ShowService {

      public String AddShows(Show show , String tname, String mname ,Integer tid ) throws ShowException ;

     public List<Map<String, Object>> getTheatresAndShows(Integer movieid ,LocalDate date) throws  ShowException ;

     
}
