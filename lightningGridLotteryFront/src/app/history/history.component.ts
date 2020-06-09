import { Component, OnInit } from '@angular/core';
import { FindSatoshiService } from '../find-satoshi.service';
import { RoundInfo } from '../round-info';
import { MatDialog } from '@angular/material/dialog';
import { ClaimWinningsDialogComponent } from './claim-winnings-dialog/claim-winnings-dialog.component';


@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  displayedColumns: string[] = ['id', 'winner', 'claim'];
  dataSource: RoundInfo[];


  constructor(private findSatoshiService: FindSatoshiService, public dialog: MatDialog) { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.findSatoshiService.getPastRounds().subscribe(
      data => {
        this.dataSource = data;
      },
      error => {
        console.log("error retreiving history!");
      });
  }

  openDialog(roundInfo : RoundInfo) {
    this.dialog.open(ClaimWinningsDialogComponent, {
      data: roundInfo
    });
  }

}
