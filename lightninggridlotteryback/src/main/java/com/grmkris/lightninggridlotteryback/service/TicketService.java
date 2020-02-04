package com.grmkris.lightninggridlotteryback.service;

import java.util.List;

import com.grmkris.lightninggridlotteryback.model.ClaimRequest;
import com.grmkris.lightninggridlotteryback.model.ClaimResponse;
import com.grmkris.lightninggridlotteryback.model.TicketRequest;
import com.grmkris.lightninggridlotteryback.model.TicketResponse;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;
import com.grmkris.lightninggridlotteryback.repository.RoundRepository;
import com.grmkris.lightninggridlotteryback.repository.TicketRepository;

import org.brunocvcunha.opennode.api.OpenNodeService;
import org.brunocvcunha.opennode.api.OpenNodeServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoundRepository roundRepository;

    public TicketResponse newTicket(TicketRequest ticketRequest) {
        // TODO
        return null;
    }

    public Ticket getTicket(Long ticketID) {
        Ticket ticket = ticketRepository.getOne(ticketID);
        return ticket;
    }


    public ClaimResponse claimWinnings(ClaimRequest claimRequest){
        return null;
    }

    public List<TicketResponse> getTickets() {
        //return ticketRepository.findAll();
        // TODO
        return null;
    }
}