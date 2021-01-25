import { HttpClient, HttpEvent, HttpErrorResponse, HttpEventType } from  '@angular/common/http';
import { map } from  'rxjs/operators';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UploadBusinessesService {

  private baseUrl = environment.backendBaseUrl;

  constructor(private httpClient: HttpClient) { }

  public upload(formData) {
      return this.httpClient.post<any>(this.baseUrl + "/upload-csv-file", formData, {
        reportProgress: true,
        observe: 'events'
      });
  }
}
