package com.grmkris.lightninggridlotteryback.service;

import java.io.IOException;
import java.util.ArrayList;
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
import org.brunocvcunha.opennode.api.model.OpenNodeCharge;
import org.brunocvcunha.opennode.api.model.OpenNodeCreateCharge;
import org.brunocvcunha.opennode.api.model.OpenNodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private static Double TICKET_AMOUNT = 200.00;

    private OpenNodeService service = OpenNodeServiceFactory.buildClient("b95d29ac-4ce9-45c9-ab9e-8767b35a01de");

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoundRepository roundRepository;

    public TicketResponse newTicket(TicketRequest ticketRequest) {
        // TODO
        return null;
    }

    public List<TicketResponse> getAllTicketsOpenNode() {
        List<TicketResponse> ticketResponses = new ArrayList<TicketResponse>();

        OpenNodeResponse<List<OpenNodeCharge>> charges;
        try {
            charges = service.listCharges().execute().body();
            for (OpenNodeCharge charge : charges.getData()) {

                System.out.println(charge.getDescription() + " - " + charge.getAmount() + " - " + charge.getStatus());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ticketResponses;
    }

    public TicketResponse getTicketOpenNode(Long ticketId) {

        Ticket ticket = ticketRepository.getOne(ticketId);

        OpenNodeCharge charge;

        // TODO
        /*
         * charge =
         * service.getCharge(ticket.getOpenNodeID()).execute().body().getData();
         * TicketResponse ticketResponse = TicketResponse.builder()
         * .amount(charge.getAmount())
         * .customerDescription(ticket.getCustomerDescription())
         * .customerEmail(ticket.getCustomerEmail())
         * .customerName(ticket.getCustomerName()) .fiatValue(ticket.getFiatValue())
         * .lightningInvoice(ticket.getLnPaymentRequest()) .numbers(ticket.getNumbers())
         * .ticketID(ticket.getTicketID())
         * .settledAt(charge.getLightningInvoice().getSettledAt())
         * .status(charge.getStatus().name()) .build();
         * 
         * //if charge.status and ticket.status differ that means that change in payment
         * occured. (user either paid or didn't pay)
         * if(!ticketResponse.getStatus().equals(charge.getStatus().name())){
         * ticket.setStatus(charge.getStatus().name());
         * ticket.setSettledAt(charge.getLightningInvoice().getSettledAt());
         * ticketRepository.save(ticket); }
         * 
         * return ticketResponse;
         */
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