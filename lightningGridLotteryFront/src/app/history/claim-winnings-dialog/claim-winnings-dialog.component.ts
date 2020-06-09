import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RoundInfo } from 'src/app/round-info';

@Component({
  selector: 'app-claim-winnings-dialog',
  templateUrl: './claim-winnings-dialog.component.html',
  styleUrls: ['./claim-winnings-dialog.component.css']
})
export class ClaimWinningsDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: RoundInfo) {}

  ngOnInit() {
  }

}
