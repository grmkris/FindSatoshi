package com.grmkris.lightninggridlotteryback.model.OpenNode.transfers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawalRequest {
    private String type;
    private String amount;
    private String address;
    private String callbackUrl;
}