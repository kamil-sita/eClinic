import { TestBed } from '@angular/core/testing';

import { MedicalServiceService } from './medical-service.service';

describe('MedicalServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MedicalServiceService = TestBed.get(MedicalServiceService);
    expect(service).toBeTruthy();
  });
});
