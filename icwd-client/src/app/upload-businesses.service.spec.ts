import { TestBed } from '@angular/core/testing';

import { UploadBusinessesService } from './upload-businesses.service';

describe('UploadBusinessesService', () => {
  let service: UploadBusinessesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UploadBusinessesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
