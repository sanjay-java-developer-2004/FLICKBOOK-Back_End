package com.example.FLICKBOOK.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Enum.MovieStatus;
import com.example.FLICKBOOK.Exception.HomeException;
import com.example.FLICKBOOK.Model.Movie;
import com.example.FLICKBOOK.Model.Show;
import com.example.FLICKBOOK.Model.Theatre;
import com.example.FLICKBOOK.Repository.MovieRepository;
import com.example.FLICKBOOK.Repository.ShowRepository;
import com.example.FLICKBOOK.Repository.TheatreRepository;

@Service
public class HomeServiceImplement implements HomeService {

        @Autowired
        private MovieRepository movierepository;

        @Autowired
        private TheatreRepository theatrerepository;

        @Autowired
        private ShowRepository showrepository;

        //NEW RELEASE

        @Override
        public List<Movie> getNewReleaseMovies(String location)
                        throws HomeException {

                List<Movie> movies = showrepository.findMoviesByLocationAndStatusIgnoreCase(
                                location,
                                MovieStatus.new_release);

                if (movies.isEmpty()) {
                        throw new HomeException(
                                        "No New Release Movies Found");
                }

                return movies;
        }

        //COMMING SOON

        @Override
        public List<Movie> getComingSoonMovies(String location)
                        throws HomeException {

                List<Movie> movies = showrepository.findMoviesByLocationAndStatusIgnoreCase(
                                location,
                                MovieStatus.upcoming);

                System.out.println("Comming : " + movies);

                if (movies.isEmpty()) {
                        throw new HomeException(
                                        "No Upcoming Movies Found");
                }

                return movies;
        }

      //SEARCH

        @Override
        public Map<String, Object> searchMovieAndTheatre(String keyword) throws HomeException {

                Map<String, Object> result = new HashMap<>();

                // Movie Search
                Optional<Movie> movieSearch = movierepository.findByMovienameIgnoreCase(keyword);

                if (movieSearch.isPresent()) {

                        result.put("movies", Collections.singletonList(movieSearch.get()));

                        System.out.println("Movie Shows Added : " + movieSearch);
                }

                // Theatre Search
                
                Optional<Theatre> theatreSearch = theatrerepository.findByTheaternameIgnoreCase(keyword);
       
                if (theatreSearch.isPresent()) {

                        List<Show> theatreShows = showrepository.findByTheatre_TheateridAndShowdate(
                                        theatreSearch.get().getTheaterid(),LocalDate.now());

                        List<Show> finaltheatreshows = new ArrayList<>();

                        for(Show show:theatreShows){
                        LocalDateTime showDateTime =
                        LocalDateTime.of(show.getShowdate(), show.getShowtime());

                        if (LocalDateTime.now().isBefore(showDateTime.plusMinutes(30))){
                                finaltheatreshows.add(show);
                        }
                        }

                        result.put("theatres", finaltheatreshows);

                        System.out.println("Theatre Shows Added : " + theatreShows.size());
                }

                if (result.isEmpty()) {

                        result.put("message", "No Movies Or Theatre Founded For " + keyword);

                }

                return result;
        }

}
