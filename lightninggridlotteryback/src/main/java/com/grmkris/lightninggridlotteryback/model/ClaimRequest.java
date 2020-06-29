package com.grmkris.lightninggridlotteryback.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClaimRequest{
    private Long roundID;
    private String secret;
    private String lightningInvoice;
}