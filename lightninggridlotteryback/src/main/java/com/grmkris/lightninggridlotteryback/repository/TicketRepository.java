package com.grmkris.lightninggridlotteryback.repository;

import java.util.List;

import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.TicketStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

    List<Ticket> findByRound(Round round);

    List<Ticket> findByRoundAndStatus(Round round, TicketStatus status);

    Ticket findByOpenNodeID(String openNodeID);

    Ticket findBySecretAndRound(String secret, Round round);


    /*
    @Query(
        value= "SELECT * from ticket r WHERE r.roundID = max(r.roundID)"
    )
    List<Ticket> findCurrentRoundTickets();
*/

}