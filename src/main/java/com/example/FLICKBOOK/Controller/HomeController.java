package com.example.FLICKBOOK.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FLICKBOOK.Exception.HomeException;
import com.example.FLICKBOOK.Model.Movie;
import com.example.FLICKBOOK.Service.HomeService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;


//NEW RELEASE

  @GetMapping("/latestmovies/{location}")
    public ResponseEntity<List<Movie>> getNewReleaseMovies(
            @PathVariable String location)
            throws HomeException {

        return ResponseEntity.ok(
              homeService.getNewReleaseMovies(location)
        );
    }


    //COMMING SOON


    @GetMapping("/commingsoon/{location}")
    public ResponseEntity<List<Movie>> getComingSoonMovies(
            @PathVariable String location)
            throws HomeException {

        return ResponseEntity.ok(
              homeService.getComingSoonMovies(location)
        );
    }
 

   //get movies or theatres by search
   @GetMapping("/search")
   public Map<String,Object> searchMovieAndTheatre(@RequestParam String keyword) throws HomeException{
    return homeService.searchMovieAndTheatre(keyword);

   }
   

   
}



