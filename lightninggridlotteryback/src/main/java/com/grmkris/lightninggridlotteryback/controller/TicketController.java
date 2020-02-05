package com.grmkris.lightninggridlotteryback.controller;

import com.grmkris.lightninggridlotteryback.model.ClaimRequest;
import com.grmkris.lightninggridlotteryback.model.ClaimResponse;
import com.grmkris.lightninggridlotteryback.model.TicketRequest;
import com.grmkris.lightninggridlotteryback.model.TicketResponse;
import com.grmkris.lightninggridlotteryback.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("*")
@RestController
public class TicketController{

    @Autowired
    TicketService ticketService;

    @RequestMapping(path = "/ticket", method = RequestMethod.POST)
    public TicketResponse newTicket(@RequestBody TicketRequest ticketRequest) {
        return ticketService.newTicket(ticketRequest);
    }

    @RequestMapping(path = "/winnings", method = RequestMethod.GET)
    public ClaimResponse checkWinningTicket(@RequestBody ClaimRequest claimRequest) {
        return ticketService.claimWinnings(claimRequest);
    }






}