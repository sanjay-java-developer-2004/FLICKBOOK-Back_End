package com.example.FLICKBOOK.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FLICKBOOK.Model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
