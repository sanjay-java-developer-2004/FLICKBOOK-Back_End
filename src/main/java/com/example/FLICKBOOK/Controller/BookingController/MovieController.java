package com.example.FLICKBOOK.Controller.BookingController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.FLICKBOOK.Exception.MovieException;
import com.example.FLICKBOOK.Model.Movie;
import com.example.FLICKBOOK.Service.ServiceInter.MovieService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;  


    //ADD MOVIE

    @PostMapping("/addmovie")
    public String addMovie(@RequestPart("movie")  Movie movies ,
                            @RequestPart("file") MultipartFile files)  throws IOException{
          return movieService.addMovie(movies, files); 
    }

    //getall movies
    @GetMapping("/showmovies")
    public List<Movie> getAllMovies() throws MovieException{
        return movieService.getAllMovies();

    }

    //get by id
    @GetMapping("/getmovie/{id}")
    public Optional<Movie> getMovieById(@PathVariable Integer id) throws MovieException{
        return movieService.getMovieById(id);
    }

    //get movie by name
    @GetMapping("/searchmovie")
    public Optional<Movie> searchMovie(@RequestParam String name) throws MovieException {
        return movieService.searchMovie(name);
    }

    //deleteMovies
    @DeleteMapping("/deletemovie")
    public  String deleteMovie(@RequestParam String name) throws MovieException{
      return  movieService.deleteMovie(name);
    }

}
