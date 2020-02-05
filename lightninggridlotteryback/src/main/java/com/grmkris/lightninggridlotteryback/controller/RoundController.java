package com.grmkris.lightninggridlotteryback.controller;

import com.grmkris.lightninggridlotteryback.model.RoundInfoResponse;
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

    @RequestMapping(path = "/round/current/info", method = RequestMethod.GET)
    public RoundInfoResponse getRoundInfo() {
        return roundService.getRoundInfo();
    }

}