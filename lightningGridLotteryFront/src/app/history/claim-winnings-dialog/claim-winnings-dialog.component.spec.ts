import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClaimWinningsDialogComponent } from './claim-winnings-dialog.component';

describe('ClaimWinningsPopUpComponent', () => {
  let component: ClaimWinningsDialogComponent;
  let fixture: ComponentFixture<ClaimWinningsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClaimWinningsDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClaimWinningsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
