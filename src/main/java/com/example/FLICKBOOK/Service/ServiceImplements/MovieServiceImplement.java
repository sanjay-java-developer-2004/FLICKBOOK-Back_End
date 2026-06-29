package com.example.FLICKBOOK.Service.ServiceImplements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.FLICKBOOK.Enum.MovieStatus;
import com.example.FLICKBOOK.Exception.MovieException;
import com.example.FLICKBOOK.Exception.UserException;
import com.example.FLICKBOOK.Model.Movie;
import com.example.FLICKBOOK.Repository.MovieRepository;
import com.example.FLICKBOOK.Service.ServiceInter.MovieService;

import jakarta.annotation.PostConstruct;

@Service
public class MovieServiceImplement implements MovieService{

    @Autowired
    private MovieRepository movieRepository;


    //add
    @Override
    public String addMovie(Movie movies , MultipartFile files) throws IOException {
        
      if(movies.getMoviename()!= null && 
        movies.getReleasedate() != null &&
        files != null &&
        movies.getMovielanguage() != null &&
        movies.getMovieduration() != null && 
        movies.getMoviegenre()!= null && 
        movies.getCensorcertificate() != null && 
        movies.getOnelinestory() != null){

          Movie add = new Movie();

          add.setMoviename(movies.getMoviename());
          add.setMovielanguage(movies.getMovielanguage());
          add.setMovieduration(movies.getMovieduration());
          add.setMoviegenre(movies.getMoviegenre());
          add.setReleasedate(movies.getReleasedate());
          add.setCensorcertificate(movies.getCensorcertificate());
          add.setOnelinestory(movies.getOnelinestory());

          add.setMoviestatus(movies.getMoviestatus());     // SetMovieStatus(movies); //this is SetMovieStatus method

          add.setImgname(files.getOriginalFilename());
          add.setImgtype(files.getContentType());
          add.setImgdata(files.getBytes());
          
          movieRepository.save(add);
   
        return "Movie added successfully";
        
      }else{

         throw new UserException("Movie details is null");

    }
    
   }



 
   //getall
    @Override
    public List<Movie> getAllMovies() throws MovieException {

      List<Movie> tempmovies = movieRepository.findAll();

      if(tempmovies.isEmpty()){

        throw  new MovieException("Movies  Not Available At The Moments Pls Try Later ......") ;

      } else {

        return tempmovies;

      }
    
    }



 
//getname

 @Override

  public Optional<Movie> searchMovie(String name) throws MovieException {

      Optional<Movie> tempmovie = movieRepository.findByMovienameIgnoreCase(name.trim());

    if(tempmovie == null){

      throw new MovieException(" The Movie Is Currently Unavailable ");

    } else {

      return tempmovie ;

    }
  }


    
//deletename
    @Override
    public String deleteMovie(String name) throws MovieException {

      Optional<Movie> check = movieRepository.findByMovienameIgnoreCase(name.trim());

     if(movieRepository.existsById(check.get().getMovieid()) && check != null ){

          movieRepository.deleteById(check.get().getMovieid());

        return "Movie Removed Successfully";

     }else{

      throw  new MovieException("The Movie "+name+" Can't Available ");

     }
   
    }
   



    //enum status update
    public void SetMovieStatus(Movie movie) {


       if(movie.getReleasedate() == null) {
        return;
    }
      long days = ChronoUnit.DAYS.between(movie.getReleasedate(), LocalDate.now());

      if(days < 0){

        movie.setMoviestatus(MovieStatus.upcoming);

      }else if(days >= 0 && days <= 7){

        movie.setMoviestatus(MovieStatus.new_release);

      }else if(days > 7 && days <= 20){

        movie.setMoviestatus(MovieStatus.running);

      }else{

        movie.setMoviestatus(MovieStatus.expired);
        
      }

    }


    //app start aana automatic ah update aagum

    @PostConstruct
    public void UpDateAllMovieStatus(){
      List<Movie> temp = movieRepository.findAll();

       for(Movie x:temp){

        if(x.getReleasedate() == null) {
            continue;
        }

        SetMovieStatus(x); //this is SetMovieStatus method 
        movieRepository.save(x);
      }
    }



    //get by id
    @Override
    public Optional<Movie> getMovieById(Integer id) throws MovieException {

      Optional<Movie> tempmovie = movieRepository.findById(id);

      if(tempmovie.isPresent()){

        return tempmovie;

      } else {

      throw new MovieException("Movie not found");

    }

  }

}