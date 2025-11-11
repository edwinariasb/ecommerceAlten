import { TestBed } from '@angular/core/testing';

import { CartWhislistService } from './cart-whislist.service';

describe('CartWhislistService', () => {
  let service: CartWhislistService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CartWhislistService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
