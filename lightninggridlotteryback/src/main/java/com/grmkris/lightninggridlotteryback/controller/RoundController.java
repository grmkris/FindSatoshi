package com.grmkris.lightninggridlotteryback.controller;

import java.util.List;

import com.grmkris.lightninggridlotteryback.model.RoundResponse;
import com.grmkris.lightninggridlotteryback.service.RoundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class RoundController{

    @Autowired
    RoundService roundService;

    @RequestMapping(path = "/rounds/current/", method = RequestMethod.GET)
    public RoundResponse getCurrentRoundo() {
        return roundService.getCurrentRound();
    }


    @RequestMapping(path = "/rounds/past/", method = RequestMethod.GET)
    public List<RoundResponse> getPastRounds() {
        return roundService.getPastRounds();
    }

}