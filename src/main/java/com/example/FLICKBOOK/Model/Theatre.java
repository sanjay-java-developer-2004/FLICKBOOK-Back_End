package com.example.FLICKBOOK.Model;

import jakarta.persistence.Entity;
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
@Table(name="Theater")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theatre {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer theaterid;

    private String theatername;
    private String theaterlocation;
    private Integer totalseats;

    @ManyToOne  
    @JoinColumn(name="user_id")
    private User user;



}
