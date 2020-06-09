import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider';
import {MatGridListModule} from '@angular/material/grid-list';
import { HttpClientModule } from '@angular/common/http';
import { TicketDialogComponent } from './ticket-dialog/ticket-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { HistoryComponent } from './history/history.component';
import {MatTableModule} from '@angular/material/table';
import { RouterModule, Routes } from '@angular/router';
import { GameComponent } from './game/game.component';
import { ClaimWinningsDialogComponent } from './history/claim-winnings-dialog/claim-winnings-dialog.component';
import { TextFieldModule } from '@angular/cdk/text-field';

const appRoutes: Routes = [
  { path: 'history', component: HistoryComponent },
  { path: '', component: GameComponent }
];
@NgModule({
  declarations: [
    AppComponent,
    TicketDialogComponent,
    HistoryComponent,
    GameComponent,
    ClaimWinningsDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatGridListModule,
    HttpClientModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    MatTableModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    TextFieldModule
  ],
  entryComponents: [
    TicketDialogComponent,
    ClaimWinningsDialogComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
