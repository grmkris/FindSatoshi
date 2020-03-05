package com.grmkris.lightninggridlotteryback;

import com.grmkris.lightninggridlotteryback.exception.RoundRunningException;
import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.service.RoundService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class FindSatoshi {

	public static void main(String[] args) {
		SpringApplication.run(FindSatoshi.class, args);

	}
	@Bean
	public CommandLineRunner init(RoundService roundService) {
		return (args) -> {
			try {
				Round round = roundService.newRound();
				log.info("Started new ROUND: " + round.getStartDate().getTime());
			} catch (RoundRunningException e) {
				log.info("Round already running");
			}
		};
	}
}