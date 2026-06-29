package com.example.FLICKBOOK.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FLICKBOOK.Model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
