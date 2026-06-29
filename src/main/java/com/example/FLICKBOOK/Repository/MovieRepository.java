package com.example.FLICKBOOK.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FLICKBOOK.Enum.MovieStatus;
import com.example.FLICKBOOK.Model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

  Optional<Movie> findByMovienameIgnoreCase(String moviename);

  List<Movie> findByMoviestatus(MovieStatus newRelease);

  
}
