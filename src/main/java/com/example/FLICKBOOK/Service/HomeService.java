package com.example.FLICKBOOK.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.HomeException;
import com.example.FLICKBOOK.Model.Movie;

@Service
public interface HomeService {

  // New Release
  List<Movie> getNewReleaseMovies(String location) throws HomeException;

  // Comming Soon
  List<Movie> getComingSoonMovies(String location) throws HomeException;

  // search
  public Map<String, Object> searchMovieAndTheatre(String keyword) throws HomeException;

}
