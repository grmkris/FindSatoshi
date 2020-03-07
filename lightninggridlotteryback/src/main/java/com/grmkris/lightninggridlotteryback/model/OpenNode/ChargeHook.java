package com.grmkris.lightninggridlotteryback.model.OpenNode;

import lombok.Data;

@Data
public class ChargeHook{
    String id;
    String callback_url;
    String success_url;
    String status;
    String order_id;
    String description;
    String price;
    String fee;
    String auto_settle;
    String hashed_order;
}