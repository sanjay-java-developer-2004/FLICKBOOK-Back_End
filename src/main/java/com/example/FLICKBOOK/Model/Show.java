package com.example.FLICKBOOK.Model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.FLICKBOOK.Enum.ShowStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="shows")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  Integer showid;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;

    @ManyToOne  
    @JoinColumn(name="theater_id")
    private Theatre theatre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a", locale = "en_US")
    private LocalTime showtime;
    private LocalDate showdate;

    @Enumerated(EnumType.STRING)
    private ShowStatus showStatus;



}
