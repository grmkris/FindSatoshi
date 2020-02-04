package com.grmkris.lightninggridlotteryback.model.database.Round;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Round{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roundID;

    //poglej okoli @Version in hibernate
    private Timestamp startDate;
    private Timestamp endDate;
    private RoundType roundType;
    private RoundStatus roundStatus;
    private String winner;

    /*
    @OneToMany(mappedBy = "raffle")
    private Set<Tickets> tickets;

    @OneToMany(mappedBy="raffle")
    private Set<Winners> winner;
    */
}