import { HttpClient, HttpEvent, HttpErrorResponse, HttpEventType } from  '@angular/common/http';
import { map } from  'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UploadBusinessesService {

  SERVER_URL: string = "http://localhost:8080";

  constructor(private httpClient: HttpClient) { }

  public upload(formData) {
      return this.httpClient.post<any>(this.SERVER_URL + "/upload-csv-file", formData, {
        reportProgress: true,
        observe: 'events'
      });
  }
}
