package com.grmkris.lightninggridlotteryback.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.grmkris.lightninggridlotteryback.model.ClaimRequest;
import com.grmkris.lightninggridlotteryback.model.ClaimResponse;
import com.grmkris.lightninggridlotteryback.model.RoundInfoResponse;
import com.grmkris.lightninggridlotteryback.model.TicketRequest;
import com.grmkris.lightninggridlotteryback.model.TicketResponse;
import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;
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
    private LightningService lightningService;

    public TicketResponse newTicket(TicketRequest ticketRequest) {
        try {

            if(ticketRequest.getCustomerEmail().equals("")){
                ticketRequest.setCustomerEmail("kristjan.grm1@gmail.com");
            }
            if(ticketRequest.getCustomerName().equals("")){
                ticketRequest.setCustomerName("Anonymous");
            }
            if(ticketRequest.getDescription().equals("Anonymous")){
                ticketRequest.setDescription("Anonymous");
            }

            
            // contact openNode with new lightningID
            TicketResponse ticketResponse = lightningService.newTicketOpenNode(ticketRequest, TICKET_AMOUNT);

            // Insert ticket information to the database
            Round round = roundRepository.findRunningRound();
            Ticket ticket = Ticket.builder()
                .amount(ticketResponse.getAmount())
                .customerDescription(ticketResponse.getCustomerDescription())
                .customerEmail(ticketResponse.getCustomerEmail())
                .customerName(ticketResponse.getCustomerName())
                .lnPaymentRequest(ticketResponse.getLightningInvoice())
                .predict(ticketResponse.getNumbers())
                .openNodeID(ticketResponse.getOpenNodeID())
                .round(round)
                .settledAt(ticketResponse.getSettledAt())
                .status(ticketResponse.getStatus())
                .ticketID(ticketResponse.getTicketID())
                .build();
                ticket = ticketRepository.save(ticket);
                
            ticketResponse.setTicketID(ticket.getTicketID());
            return ticketResponse;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            // API excpetion poglej proper exception handling
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    public Ticket getTicket(Long ticketID) {
        Ticket ticket = ticketRepository.getOne(ticketID);
        return ticket;
    }


    public ClaimResponse claimWinnings(ClaimRequest claimRequest){
        return null;
    }


}