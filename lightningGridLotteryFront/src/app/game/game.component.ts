import { Component, OnInit } from '@angular/core';
import { RoundInfo } from '../round-info';
import { FindSatoshiService } from '../find-satoshi.service';
import { MatDialog } from '@angular/material/dialog';
import { TicketDialogComponent } from '../ticket-dialog/ticket-dialog.component';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  title = 'lightningGridLottery';
  gridX : number = 5;
  gridY : number = 5;
  grid: { [id: string] : number; } = {};
  roundInfo : RoundInfo;

  constructor(private findSatoshiService : FindSatoshiService, public dialog: MatDialog){
    var number = 1;
    var letter = 65;
    var entry = "";
    for (var y = 0; y < this.gridX; y++){
      letter = 65;
      for (var x =  0; x < this.gridY; x++ ){
        entry =  String.fromCharCode(letter) + number;
        this.grid[entry] = 0;
        letter++;
      }
      number++;
    }
      console.log(this.grid);
  }

  ngOnInit(): void {
    this.findSatoshiService.getRoundInfo().subscribe(response => {
      this.roundInfo = response;
      for(var i = 0; i < this.roundInfo.currentPredictions.length ; i++){
        this.grid[this.roundInfo.currentPredictions[i]]++;
      }
      console.log(this.grid);
    });
  }

  openDialog(predict : String): void {
    const dialogRef = this.dialog.open(TicketDialogComponent, {
      width: '450px',
      data: {predict: predict}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  getBackgroundColour(n : number){
    n = n * 100;
    var R = 246 //(255 * 5) / 100
    var G = (180 * (100 - n)) / 100 
    var B = 48
    var color = "rgb("+R + "," + G + "," + B + ")";
    return color;
  }
}
