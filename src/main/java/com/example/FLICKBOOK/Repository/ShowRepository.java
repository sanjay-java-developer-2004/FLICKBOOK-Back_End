package com.example.FLICKBOOK.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.FLICKBOOK.Enum.MovieStatus;
import com.example.FLICKBOOK.Model.Movie;
import com.example.FLICKBOOK.Model.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    List<Show> findByMovie_MovieidAndShowdate(Integer movieid, LocalDate date);

      List<Show> findByTheatre_TheateridAndShowdate(Integer theaterid, LocalDate now);

    List<Show> findByMovie_Movieid(Integer movieid);



    @Query("""
                SELECT DISTINCT s.movie
                FROM Show s
                WHERE LOWER(s.theatre.theaterlocation) = LOWER(:location)
                AND s.movie.moviestatus = :status
            """)
    List<Movie> findMoviesByLocationAndStatusIgnoreCase(
            @Param("location") String location,
            @Param("status") MovieStatus status);

    List<Show> findByTheatre_Theaterid(Integer theatreid);

    

  

}
