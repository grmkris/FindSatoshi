package com.grmkris.lightninggridlotteryback;

import com.grmkris.lightninggridlotteryback.exception.RoundRunningException;
import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.service.RoundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class FindSatoshi {

	@Autowired
	static RoundService roundService;

	public static void main(String[] args) {
		SpringApplication.run(FindSatoshi.class, args);
		try {
			Round round = startGame();
			log.info("Started new ROUND: " + round.getStartDate().getTime());
		} catch (RoundRunningException e) {
			log.info("Round already running");
		}
	}

	public static Round startGame() throws RoundRunningException {
		return roundService.newRound();
	}
}
