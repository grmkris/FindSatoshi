import { TestBed } from '@angular/core/testing';

import { FindSatoshiService } from './find-satoshi.service';

describe('FindSatoshiService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FindSatoshiService = TestBed.get(FindSatoshiService);
    expect(service).toBeTruthy();
  });
});
