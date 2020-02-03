package com.grmkris.lightninggridlotteryback.repository;

import com.grmkris.lightninggridlotteryback.model.database.Round;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long>{

    // TODO
    @Query(
        value = "SELECT * FROM round r ORDER BY r.start_date DESC LIMIT 1",
        nativeQuery = true)
    Round findRunningRound();

    @Query(
        value = "SELECT * FROM round r WHERE r.start_date IS NOT NULL AND r.end_date IS NOT NULL and r.winning_numbers IS NOT NULL ORDER BY r.start_date DESC LIMIT 1",
        nativeQuery = true)
        Round findCompletedRound();
    

}