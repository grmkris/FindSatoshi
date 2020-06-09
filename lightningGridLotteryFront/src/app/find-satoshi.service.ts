import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TicketRequest } from './ticket-request';
import { RoundInfo } from './round-info';
import { TicketResponse } from './ticket-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FindSatoshiService {

  ticketUrl = 'http://localhost:8081/findSatoshi/ticket';
  currentRoundUrl = 'http://localhost:8081/findSatoshi/rounds/current/';
  pastRoundsUrl = 'http://localhost:8081/findSatoshi/rounds/past/';
  

  constructor(private http: HttpClient) { }

  getNewTicket(ticketRequest : TicketRequest): Observable<TicketResponse>{
    return this.http.post<TicketResponse>(this.ticketUrl, ticketRequest);
  }

  getCurrentRound() : Observable<RoundInfo>{
    return this.http.get<RoundInfo>(this.currentRoundUrl);
  }

  getPastRounds() : Observable<Array<RoundInfo>>{
    return this.http.get<Array<RoundInfo>>(this.pastRoundsUrl);
  }

}
  