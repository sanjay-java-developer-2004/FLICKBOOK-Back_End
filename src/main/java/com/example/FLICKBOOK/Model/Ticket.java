package com.example.FLICKBOOK.Model;

import com.example.FLICKBOOK.Enum.TicketStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Ticket")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  Integer ticketid;

    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;

    private String ticketnumber;

    private String paymenttype;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Lob
    private byte[] qrcode;

}
