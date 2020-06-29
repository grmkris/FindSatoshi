package com.grmkris.lightninggridlotteryback.model.OpenNode.transfers;

import lombok.Data;

@Data
public class WithdrawalResponse {
    private String id;
    private String type;
    private Long amount;
    private String reference;
    private Long fee;
    private String status;
    private Long processed_at;
}