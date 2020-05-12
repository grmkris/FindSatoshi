package com.grmkris.lightninggridlotteryback.model;

import java.util.List;

import com.grmkris.lightninggridlotteryback.model.database.Round.Round;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoundResponse {
    Round round;
    List<String> predictions;
}