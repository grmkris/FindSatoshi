import { Component } from '@angular/core';
import { FindSatoshiService } from './find-satoshi.service';
import { TicketDialogComponent } from './ticket-dialog/ticket-dialog.component'
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { RoundInfo } from './round-info';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

}
