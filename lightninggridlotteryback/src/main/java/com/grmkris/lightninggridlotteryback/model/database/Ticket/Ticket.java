package com.grmkris.lightninggridlotteryback.model.database.Ticket;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.grmkris.lightninggridlotteryback.model.database.Round.Round;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketID;

    @ManyToOne
    @JoinColumn(name="roundID", nullable=false)
    private Round round;
    
    private String customerName;
    private String customerEmail;
    private String customerDescription;
    private String predict;

    private String openNodeID;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    
    private Double amount;
    private String lnPaymentRequest;
    private Long settledAt;

}