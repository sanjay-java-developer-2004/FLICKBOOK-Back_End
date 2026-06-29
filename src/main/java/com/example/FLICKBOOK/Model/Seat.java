package com.example.FLICKBOOK.Model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.FLICKBOOK.Enum.SeatStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Seat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatid;
    private String seatnumber;
    private Double price;
    private LocalDateTime holdtime;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatstatus;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;



    
    @Transient     //used to send json response  only not save db
    private String movieName;

    @Transient
    private String theatreName;

    @Transient
    private LocalTime showTime;

    @Transient
    private Integer movieId;

}
