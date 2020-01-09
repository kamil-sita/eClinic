import { TestBed } from '@angular/core/testing';

import { DoctorListService } from './doctor-list.service';

describe('DoctorListService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DoctorListService = TestBed.get(DoctorListService);
    expect(service).toBeTruthy();
  });
});
