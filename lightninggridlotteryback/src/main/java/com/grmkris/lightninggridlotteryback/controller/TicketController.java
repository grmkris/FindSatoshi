package com.grmkris.lightninggridlotteryback.controller;

import com.grmkris.lightninggridlotteryback.exception.RoundEndedException;
import com.grmkris.lightninggridlotteryback.exception.RoundNotFoundException;
import com.grmkris.lightninggridlotteryback.exception.RoundRunningException;
import com.grmkris.lightninggridlotteryback.model.ClaimRequest;
import com.grmkris.lightninggridlotteryback.model.ClaimResponse;
import com.grmkris.lightninggridlotteryback.model.TicketRequest;
import com.grmkris.lightninggridlotteryback.model.TicketResponse;
import com.grmkris.lightninggridlotteryback.model.OpenNode.ChargeHook;
import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.model.database.Round.RoundStatus;
import com.grmkris.lightninggridlotteryback.service.RoundService;
import com.grmkris.lightninggridlotteryback.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    RoundService roundService;

    @RequestMapping(path = "/ticket", method = RequestMethod.POST)
    public TicketResponse newTicket(@RequestBody TicketRequest ticketRequest) {
        return ticketService.newTicket(ticketRequest);
    }

    @RequestMapping(path = "/winnings", method = RequestMethod.GET)
    public ClaimResponse checkWinningTicket(@RequestBody ClaimRequest claimRequest) {
        return ticketService.claimWinnings(claimRequest);
    }

    @RequestMapping(path = "/success", method = RequestMethod.POST)
    public int success(ChargeHook chargeHook)
            throws RoundNotFoundException, RoundEndedException, RoundRunningException {
        ticketService.updateTicket(chargeHook.getId(), chargeHook.getStatus());
        Round round = roundService.checkAndEndRound();
        if ( round.getRoundStatus().equals(RoundStatus.COMPLETED)){
            ticketService.findWinners(round);
        }
        return 200;
    }






}