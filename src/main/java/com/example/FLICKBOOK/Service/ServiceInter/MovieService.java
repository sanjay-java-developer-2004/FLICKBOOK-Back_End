package com.example.FLICKBOOK.Service.ServiceInter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.FLICKBOOK.Exception.MovieException;
import com.example.FLICKBOOK.Model.Movie;

@Service
public interface MovieService {

    //add movies
     public String addMovie(Movie movies ,MultipartFile files)  throws IOException;

     //get all movies
      public List<Movie> getAllMovies()throws MovieException;

      //search movie by name
       public Optional<Movie> searchMovie(String name) throws MovieException ;

       //deletemovies
        public  String deleteMovie(String name) throws MovieException;

        //getby id
         public Optional<Movie> getMovieById(Integer id) throws MovieException;
        
}
