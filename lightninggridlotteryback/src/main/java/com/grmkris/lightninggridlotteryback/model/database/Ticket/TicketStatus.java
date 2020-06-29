package com.grmkris.lightninggridlotteryback.model.database.Ticket;

public enum TicketStatus{
    /** user request ticket and paid it */
    PAID,
    /** ticket requested but failed to pay */
    FAILED,
    /** ticket request, waiting for payment */
    UNPAID,
    /** round ended, this ticke */
    WINNER,
    /** if status was winner, user can claim their winnings */
    CLAIM,
    /** if ticket was winner, and user claimed their winnings and they got paid*/
    CLAIM_PAID,
    /** if ticket was winner and user claimed their winnings but claim somehow failed */
    CLAIM_FAILED
}