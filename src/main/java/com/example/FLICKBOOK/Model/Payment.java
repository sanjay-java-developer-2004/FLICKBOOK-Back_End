package com.example.FLICKBOOK.Model;

import com.example.FLICKBOOK.Enum.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer paymentid;

    @ManyToOne
    private Booking booking;
    
    private String paymentmethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentstatus;

    private Double amount;
    
}
