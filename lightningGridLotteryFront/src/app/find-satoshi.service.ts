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

  ticketUrl = 'http://localhost:8082/findSatoshi/ticket';
  roundInfoUrl = 'http://localhost:8082/findSatoshi/round/current/info';

  constructor(private http: HttpClient) { }

  getNewTicket(ticketRequest : TicketRequest): Observable<TicketResponse>{
    return this.http.post<TicketResponse>(this.ticketUrl, ticketRequest);
  }

  getRoundInfo() : Observable<RoundInfo>{
    return this.http.get<RoundInfo>(this.roundInfoUrl);
  }

}
  