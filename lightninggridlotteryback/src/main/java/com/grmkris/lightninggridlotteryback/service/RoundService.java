package com.grmkris.lightninggridlotteryback.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.grmkris.lightninggridlotteryback.exception.ClaimException;
import com.grmkris.lightninggridlotteryback.exception.RoundEndedException;
import com.grmkris.lightninggridlotteryback.exception.RoundNotFoundException;
import com.grmkris.lightninggridlotteryback.exception.RoundRunningException;
import com.grmkris.lightninggridlotteryback.model.ClaimRequest;
import com.grmkris.lightninggridlotteryback.model.ClaimResponse;
import com.grmkris.lightninggridlotteryback.model.RoundResponse;
import com.grmkris.lightninggridlotteryback.model.OpenNode.transfers.WithdrawalResponse;
import com.grmkris.lightninggridlotteryback.model.database.Round.Round;
import com.grmkris.lightninggridlotteryback.model.database.Round.RoundStatus;
import com.grmkris.lightninggridlotteryback.model.database.Round.RoundType;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.Ticket;
import com.grmkris.lightninggridlotteryback.model.database.Ticket.TicketStatus;
import com.grmkris.lightninggridlotteryback.repository.LightningRepository;
import com.grmkris.lightninggridlotteryback.repository.RoundRepository;
import com.grmkris.lightninggridlotteryback.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.val;

@Service
public class RoundService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private LightningRepository lightningRepository;

    @Autowired
    private TicketService ticketService;

    public Round newRound() throws RoundRunningException {

        Round RoundCheck = roundRepository.findRunningRound();
        if (RoundCheck == null) {
            Date date = new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            Round round;
            round = Round.builder().endDate(null).startDate(ts).tickets(null).roundStatus(RoundStatus.RUNNING)
                    .roundType(RoundType._5x5).build();

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

    public Round endRound(Long RoundID) throws RoundNotFoundException, RoundEndedException, RoundRunningException {
        Optional<Round> currentRoundOpt = roundRepository.findById(RoundID);
        if (currentRoundOpt.isPresent()) {
            Round currentRound = currentRoundOpt.get();

            if (currentRound.getRoundStatus() == RoundStatus.COMPLETED
                    || currentRound.getRoundStatus() == RoundStatus.FAILED) {
                throw new RoundEndedException("Round already ended");
            }
            Date date = new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            currentRound.setEndDate(ts);
            currentRound.setWinner(generateRandomNumber(currentRound.getRoundType()));
            currentRound.setRoundStatus(RoundStatus.COMPLETED);
            roundRepository.save(currentRound);

            this.newRound();
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
    private String generateRandomNumber(RoundType roundType) {
        Random r = new Random();
        char character = (char) (r.nextInt(5) + 'a');
        Integer number = r.nextInt(5);
        return character + number.toString();
    }

    public RoundResponse getCurrentRound() {
        Round round = roundRepository.findRunningRound();
        List<Ticket> tickets = ticketRepository.findByRoundAndStatus(round, TicketStatus.PAID);

        List<String> predictions = tickets.stream().map(Ticket::getPredict).collect(Collectors.toList());
        RoundResponse roundInfoResponse = RoundResponse.builder().round(round)
                .predictions(predictions).build();
        return roundInfoResponse;
    }

    /**
     * Checks whether all tickets where bought. If true it ends current round
     * 
     * @throws RoundNotFoundException
     * @throws RoundEndedException
     * @throws RoundRunningException
     */
    public Round updateRound() throws RoundNotFoundException, RoundEndedException, RoundRunningException {
        Round round = roundRepository.findRunningRound();
        List<Ticket> ticketList = ticketRepository.findByRound(round);
        List<String> predictList = ticketList.stream().filter(p -> p.getStatus() == (TicketStatus.PAID))
                .map(Ticket::getPredict).collect(Collectors.toList());
        if (predictList.stream().distinct().count() == 25) {
            this.endRound(round.getRoundID());
            ticketService.findWinners(round);
        }
        return round;
    }

    /**
     * Returns past rounds with their's user predictions
     * @return
     */
    public List<RoundResponse> getPastRounds() {
        List<Round> roundList = this.roundRepository.findByRoundStatus(RoundStatus.COMPLETED);
        List<RoundResponse> roundResponseList = new ArrayList<RoundResponse>();
        roundList.forEach(round -> {
            val ticketList = this.ticketRepository.findByRound(round);
            roundResponseList.add(RoundResponse.builder()
                .round(round)
                .predictions(ticketList.stream()
                    .map(Ticket::getPredict)
                    .collect(Collectors.toList()))
                .build());
        });
        return roundResponseList;
    }

	public ClaimResponse claimWinnings(ClaimRequest claimRequest) throws ClaimException {
        Round round = this.roundRepository.findById(claimRequest.getRoundID()).get();
       /**
        * Round must have status completed, and ticket with same secret must exist with status WINNER
        */
        List<Ticket> ticketList = ticketRepository.findByRound(round);
        List<Ticket> winnerList = ticketList.stream().filter(p -> p.getStatus() == (TicketStatus.WINNER)).filter(ticket -> ticket.getSecret().equals(claimRequest.getSecret())).collect(Collectors.toList());

        if( winnerList.size() != 0 ){
            
        }
        this.lightningRepository.ticketWithdrawalOpenNode(claimRequest);
        return null;
        /*
                if(round.isPresent()){
            Ticket ticket = this.ticketRepository.findByCustomerDescriptionAndRound(claimRequest.getSecret(), round.get());
            switch (ticket.getStatus()){
                case CLAIM: 
                    throw new ClaimException("Claim already in progress");
                case CLAIM_PAID: 
                    throw new ClaimException("Claim already paid");
                case WINNER: 
                    this.lightningService.ticketWithdrawalOpenNode(claimRequest);
                case CLAIM_FAILED: 
                    this.lightningService.ticketWithdrawalOpenNode(claimRequest);
                    break;
                case PAID:
                    throw new ClaimException("Not a winner!"); 
                case FAILED:
                    throw new ClaimException("Not a winner! - ticket is unpaid"); 
                case UNPAID:
                    throw new ClaimException("Not a winner! - ticket is unpaid"); 
				default:
					break;
            }
        }
        */

        // TODO
        // TODO iniate openenode withdrawal and parse its response and return status to user
		//return null;
	}

	public ClaimResponse updateWinner(WithdrawalResponse withdrawalResponse) {
        // TODO
        // update winner on sucessfull withdrawal
		return null;
	}

}
