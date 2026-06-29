package com.example.FLICKBOOK.Model;



// import java.beans.Transient;
import java.time.LocalDate;

import com.example.FLICKBOOK.Enum.MovieStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="Movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer movieid;
    
    private  String moviename;
    private  String movielanguage;
    private  String movieduration;
    private  String moviegenre;
    private  LocalDate releasedate;
    private String censorcertificate;
    private String onelinestory;

    @Enumerated(EnumType.STRING)
    private MovieStatus moviestatus;


  private String imgname;
  private String imgtype;

  @Lob
  @Column(name = "imgdata", columnDefinition = "LONGBLOB")
  private byte[] imgdata;




}