package com.grmkris.lightninggridlotteryback.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.grmkris.lightninggridlotteryback.exception.RoundEndedException;
import com.grmkris.lightninggridlotteryback.exception.RoundNotFoundException;
import com.grmkris.lightninggridlotteryback.exception.RoundRunningException;
import com.grmkris.lightninggridlotteryback.model.RoundInfoResponse;
import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.model.database.Round.RoundStatus;
import com.grmkris.lightninggridlotteryback.model.database.Round.RoundType;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.TicketStatus;
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

    public Round newRound() throws RoundRunningException {
        // TODO exception if RoundAlreadyRunning

        Round RoundCheck = roundRepository.findRunningRound();
        if (RoundCheck == null) {
            Date date = new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            Round round;
            round = Round.builder().endDate(null).startDate(ts).tickets(null).roundStatus(RoundStatus.RUNNING)
                    .build();

            roundRepository.save(round);
            return round;
        } else {
            throw new RoundRunningException("Round is already running");
        }

    }

    public Round getRound(Long roundID) {
        Optional<Round> roundOpt = roundRepository.findById(roundID);
        if (roundOpt.isPresent()) {
            return roundOpt.get();
        } else {
            // TODO
            return null;
            // throw new RoundNotFoundException();
        }
    }

    public List<Round> getRounds() {
        return roundRepository.findAll();
    }

    public Round endRound(Long RoundID) throws RoundNotFoundException, RoundEndedException {
        Optional<Round> currentRoundOpt = roundRepository.findById(RoundID);
        if (currentRoundOpt.isPresent()) {
            Round currentRound = currentRoundOpt.get();

            if (currentRound.getRoundStatus() == RoundStatus.COMPLETED || currentRound.getRoundStatus() == RoundStatus.FAILED) {
                throw new RoundEndedException("Round already ended");
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
            throw new RoundNotFoundException("Round not found");
        }
        // TODO excpetion RoundAllreadyEndedException
        // TODO Round doesn't exist
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
        List<Ticket> tickets = ticketRepository.findByRoundAndStatus(round, TicketStatus.PAID);

        List<String> predictions = tickets.stream().map(Ticket::getPredict).collect(Collectors.toList());
        RoundInfoResponse roundInfoResponse = RoundInfoResponse.builder().currentRound(round)
                .currentPredictions(predictions).build();
        return roundInfoResponse;
    }

    public void checkRoundStatus() throws RoundNotFoundException, RoundEndedException {
        Round round = roundRepository.findRunningRound();
        List<String> predictList = round.getTickets().stream().filter(p -> p.getStatus()==(TicketStatus.PAID)).map(Ticket::getPredict).collect(Collectors.toList());
        if ( predictList.stream().distinct().count() == 25 ){
            this.endRound(round.getRoundID());
        }

    }

}
