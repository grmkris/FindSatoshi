package com.grmkris.lightninggridlotteryback.repository;

import java.util.List;

import com.grmkris.lightninggridlotteryback.model.database.Round;
import com.grmkris.lightninggridlotteryback.model.database.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    // TODO
    List<Ticket> findByRaffle(Round round);
}