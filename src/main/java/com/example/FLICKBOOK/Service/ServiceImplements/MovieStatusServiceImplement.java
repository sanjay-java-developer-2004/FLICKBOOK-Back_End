// package com.example.FLICKBOOK.Service.ServiceImplements;

// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;

// import com.example.FLICKBOOK.Enum.MovieStatus;
// import com.example.FLICKBOOK.Model.Movie;
// import com.example.FLICKBOOK.Repository.MovieRepository;
// // import com.example.FLICKBOOK.Service.ServiceInter.MovieStatusService;

// @Service
// public class MovieStatusServiceImplement{

//     @Autowired
//     private MovieRepository movierepo;

//     // @Scheduled(cron="0 0 0 * * ?")       //this is update on every day at 12 am 
//     @Scheduled(fixedRate = 5000)             //it's update on every 5s 
//     public void UpdateMovieStatus(){
//         List<Movie> Movieslist = movierepo.findAll();

//         for(Movie temp :Movieslist){
//             long days = ChronoUnit.DAYS.between(temp.getReleasedate(),LocalDate.now());

//             if(days<0){
//                 temp.setMoviestatus(MovieStatus.upcoming);
//             }
//             else if(days<=7){
//                 temp.setMoviestatus(MovieStatus.new_release);
//             }else if(days<=20){
//                 temp.setMoviestatus(MovieStatus.running);
//             }else{
//                 temp.setMoviestatus(MovieStatus.expired);
//             }

//             movierepo.save(temp);
//         }
//     }

// }
