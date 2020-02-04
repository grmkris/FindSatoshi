package com.grmkris.lightninggridlotteryback.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.grmkris.lightninggridlotteryback.exception.RoundEndedException;
import com.grmkris.lightninggridlotteryback.exception.RoundNotFoundException;
import com.grmkris.lightninggridlotteryback.exception.RoundRunningException;
import com.grmkris.lightninggridlotteryback.model.RoundInfoResponse;
import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.model.database.Round.RoundStatus;
import com.grmkris.lightninggridlotteryback.model.database.Round.RoundType;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;
import com.grmkris.lightninggridlotteryback.repository.RoundRepository;
import com.grmkris.lightninggridlotteryback.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoundService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private LightningService lightningService;

    public Round newRaffle() throws RoundRunningException {
        // TODO exception if raffleAlreadyRunning

        Round raffleCheck = roundRepository.findRunningRound();
        if (raffleCheck == null) {
            Date date = new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            Round raffle = Round.builder().endDate(null).startDate(ts).tickets(null).roundStatus(RoundStatus.RUNNING)
                    .build();
            roundRepository.save(raffle);
            return raffle;
        } else {
            throw new RoundRunningException("Raffle is already running");
        }

    }

    public Round getRaffle(Long roundID) {
        Optional<Round> raffleOpt = roundRepository.findById(roundID);
        if (raffleOpt.isPresent()) {
            return raffleOpt.get();
        } else {
            // TODO
            return null;
            // throw new RaffleNotFoundException();
        }
    }

    public List<Round> getRounds() {
        return roundRepository.findAll();
    }

    public Round endRound(Long raffleID) throws RoundNotFoundException, RoundEndedException {
        Optional<Round> currentRoundOpt = roundRepository.findById(raffleID);
        if (currentRoundOpt.isPresent()) {
            Round currentRound = currentRoundOpt.get();

            if (currentRound.getRoundStatus() == RoundStatus.COMPLETED || currentRound.getRoundStatus() == RoundStatus.FAILED) {
                throw new RoundEndedException("Raffle already ended");
            }
            Date date = new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            currentRound.setEndDate(ts);
            currentRound.setWinner(generateRandomNumber(currentRound.getRoundType()));
            currentRound.setRoundStatus(RoundStatus.COMPLETED);
            roundRepository.save(currentRound);
            return currentRound;
        } else {
            throw new RoundNotFoundException("Raffle not found");
        }
        // TODO excpetion raffleAllreadyEndedException
        // TODO raffle doesn't exist
    }

    /**
     * naključno generira 5 števil z random <br>
     * 
     * @return String 5-ih števil 0-9
     */
    private String generateRandomNumber(RoundType roundType){
        String finalStr = "";
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            finalStr += rnd.nextInt(9);
        }
        return "C5"; // TODO true random generator!
    }

    public RoundInfoResponse getRoundInfo() {
        Round round = roundRepository.findRunningRound();
        List<Ticket> tickets = ticketRepository.findByRound(round);

        List<String> predictions = tickets.stream().map(Ticket::getPredict).collect(Collectors.toList());
        RoundInfoResponse roundInfoResponse = RoundInfoResponse.builder().currentRound(round)
                .currentPredictions(predictions).build();
        return roundInfoResponse;
    }

}
