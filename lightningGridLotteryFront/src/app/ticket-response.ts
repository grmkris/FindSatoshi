export interface TicketResponse {

    ticketID : Number; //naš interni id, order_id za openNode
    openNodeID : String; //openNode id
    customerName: String; // name of customer, or custom text
    customerEmail: String; //
    customerDescription: String; // description
    numbers: String;
    status: String; // unpaid, paid, processing, win, lose
    amount: Number; //cena ticketa (200 satoshi)
    fiatValue: Number;
    lightningInvoice : String;
    setttledAt: Number;

}
