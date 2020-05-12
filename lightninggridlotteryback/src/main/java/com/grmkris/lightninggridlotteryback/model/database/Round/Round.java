package com.grmkris.lightninggridlotteryback.model.database.Round;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roundID;

    //poglej okoli @Version in hibernate
    private Timestamp startDate;
    private Timestamp endDate;
    @Enumerated(EnumType.STRING)
    private RoundType roundType;
    @Enumerated(EnumType.STRING)
    private RoundStatus roundStatus;
    private String winner;

    
    @JsonIgnore
    @OneToMany(mappedBy = "round")
    @Fetch(value=FetchMode.SELECT)
    private Set<Ticket> tickets;

}