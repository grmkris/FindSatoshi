package com.grmkris.lightninggridlotteryback.controller;

import java.util.List;

import com.grmkris.lightninggridlotteryback.exception.ClaimException;
import com.grmkris.lightninggridlotteryback.model.ClaimRequest;
import com.grmkris.lightninggridlotteryback.model.ClaimResponse;
import com.grmkris.lightninggridlotteryback.model.RoundResponse;
import com.grmkris.lightninggridlotteryback.model.OpenNode.transfers.WithdrawalResponse;
import com.grmkris.lightninggridlotteryback.service.RoundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class RoundController {

    @Autowired
    RoundService roundService;

    /**
     * Returns current round information
     * @return RoundResponse
     */
    @RequestMapping(path = "/rounds/current/", method = RequestMethod.GET)
    public RoundResponse getCurrentRound() {
        return roundService.getCurrentRound();
    }

    /**
     * Returns list of all past rounds as list
     * @return
     */
    @RequestMapping(path = "/rounds/past/", method = RequestMethod.GET)
    public List<RoundResponse> getPastRounds() {
        return roundService.getPastRounds();
    }

    /**
     * Sends funds to provided lnurl if user's secret won
     * @param claimRequest
     * @return
     * @throws ClaimException
     */
    @RequestMapping(path = "/rounds/claim/", method = RequestMethod.POST)
    public ClaimResponse claimRoundWinnings(@RequestBody ClaimRequest claimRequest) throws ClaimException {
        return roundService.claimWinnings(claimRequest);
    }

    /**
     * Callback endpoint for openNode withdrawal sucess
     * @param withdrawalResponse
     * @return
     */
    @RequestMapping(path = "/rounds/claim/success/", method = RequestMethod.POST)
    public ClaimResponse claimRoundWinningsSucces(@RequestBody WithdrawalResponse withdrawalResponse) {
        return roundService.updateWinner(withdrawalResponse);
    }

}