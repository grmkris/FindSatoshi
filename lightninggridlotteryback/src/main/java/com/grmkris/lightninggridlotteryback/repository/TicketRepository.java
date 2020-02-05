package com.grmkris.lightninggridlotteryback.repository;

import java.util.List;

import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    // TODO
    List<Ticket> findByRound(Round round);

    /*
    @Query(
        value= "SELECT * from ticket r WHERE r.roundID = max(r.roundID)"
    )
    List<Ticket> findCurrentRoundTickets();
*/

}