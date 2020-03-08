import { Component, OnInit } from '@angular/core';



export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'C2', weight: 25000},
  {position: 2, name: 'A5', weight: 46500},
  {position: 3, name: 'E2', weight: 34343},
  {position: 4, name: 'C3', weight: 34321},
  {position: 5, name: 'G2', weight: 222222},
  {position: 6, name: 'B2', weight: 150000},
  {position: 7, name: 'G2', weight: 23222},
  {position: 8, name: 'E5', weight: 23255},
  {position: 9, name: 'E2', weight: 23112},
  {position: 10, name: 'B4', weight: 32432},
];

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  displayedColumns: string[] = ['position', 'name', 'weight', 'claim'];
  dataSource = ELEMENT_DATA;

  constructor() { }

  ngOnInit() {
  }

}
