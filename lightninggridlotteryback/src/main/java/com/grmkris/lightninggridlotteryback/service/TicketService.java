package com.grmkris.lightninggridlotteryback.service;

import java.io.IOException;
import java.util.List;

import com.grmkris.lightninggridlotteryback.model.TicketRequest;
import com.grmkris.lightninggridlotteryback.model.TicketResponse;
import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.TicketStatus;
import com.grmkris.lightninggridlotteryback.repository.LightningRepository;
import com.grmkris.lightninggridlotteryback.repository.RoundRepository;
import com.grmkris.lightninggridlotteryback.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private static Double TICKET_AMOUNT = 200.00;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private LightningRepository lightningRepository;

    /**
     * Requests new ticket from openNode API service
     * @param ticketRequest
     * @return
     */
    public TicketResponse newTicket(TicketRequest ticketRequest) {
        try {
            // contact openNode with new lightningID
            TicketResponse ticketResponse = lightningRepository.newTicketOpenNode(ticketRequest, TICKET_AMOUNT);

            // Insert ticket information to the database
            Round round = roundRepository.findRunningRound();
            Ticket ticket = Ticket.builder().amount(ticketResponse.getAmount())
                    .secret(ticketRequest.getSecret())
                    .lnPaymentRequest(ticketResponse.getLightningInvoice()).predict(ticketResponse.getNumbers())
                    .openNodeID(ticketResponse.getOpenNodeID()).round(round).settledAt(ticketResponse.getSettledAt())
                    .status(TicketStatus.valueOf(ticketResponse.getStatus())).ticketID(ticketResponse.getTicketID())
                    .build();
            ticket = ticketRepository.save(ticket);

            ticketResponse.setTicketID(ticket.getTicketID());
            return ticketResponse;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            // API excpetion poglej proper exception handling
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public Ticket getTicket(Long ticketID) {
        Ticket ticket = ticketRepository.getOne(ticketID);
        return ticket;
    }

    public Ticket updateTicket(String openNodeID, String status) {
        Ticket ticket = ticketRepository.findByOpenNodeID(openNodeID);
        if (status.equals("paid")) {
            ticket.setStatus(TicketStatus.PAID);
        }
        ticketRepository.save(ticket);
        return ticket;
    }

    public void findWinners(Round round) {
        List<Ticket> ticketList = ticketRepository.findByRound(round);
        ticketList.stream().forEach(ticket -> {
            if (ticket.getPredict().equals(round.getWinner())) {
                ticket.setStatus(TicketStatus.WINNER);
            }
        });
        ticketRepository.saveAll(ticketList);
    }

}