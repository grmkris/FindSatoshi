import { Component, OnInit, Inject } from '@angular/core';
import { FindSatoshiService } from '../find-satoshi.service';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { TicketRequest } from '../ticket-request';
import { TicketResponse } from '../ticket-response';


export interface DialogData {
  predict: string;
}

@Component({
  selector: 'app-ticket-dialog',
  templateUrl: './ticket-dialog.component.html',
  styleUrls: ['./ticket-dialog.component.css']
})
export class TicketDialogComponent implements OnInit {

  ticketRequest : TicketRequest;

  constructor(private findSatoshiService : FindSatoshiService, public dialogRef: MatDialogRef<TicketDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}
  ngOnInit() {
    this.ticketRequest = <TicketRequest>{};
  }

  getNewTicket(ticketRequest : TicketRequest, predict : string): void{
    ticketRequest.callbackUrl="";
    ticketRequest.successUrl="";
    ticketRequest.predict=this.data.predict;
    this.findSatoshiService.getNewTicket(ticketRequest)
    .subscribe((ticketResponse: TicketResponse) =>  {
        window.open("https://checkout.opennode.com/" + ticketResponse.openNodeID)
        location.reload();
      console.log(ticketResponse);
    });
  }

}
