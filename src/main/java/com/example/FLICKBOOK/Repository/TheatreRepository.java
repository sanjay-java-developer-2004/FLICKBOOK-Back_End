package com.example.FLICKBOOK.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FLICKBOOK.Model.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Integer>{
   
Optional<Theatre> findByTheaternameIgnoreCase(String theatername);

Optional<Theatre> findByTheateridAndTheaternameIgnoreCase(Integer theaterid, String theatername);

Theatre findByUser_Userid(Integer userid);
  

}
