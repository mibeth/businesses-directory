import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Business } from './business';

@Injectable({
  providedIn: 'root'
})
export class BusinessService {

  private baseUrl = environment.backendBaseUrl + "/directory";

  constructor(private http: HttpClient) { }

  getBusinessById(id: string): Observable<Business> {
    return this.http.get<Business>(`${this.baseUrl}/${id}`);
  }

  createBusiness(business: Business): Observable<Object> {
    this.fixArrays(business);
    return this.http.post(`${this.baseUrl}/business`, business);
  }

  updateBusiness(business: Business): Observable<Object>{
    return this.createBusiness(business);
  }

  deleteBusiness(id: string): Observable<Object> {
    return this.http.delete(`${this.baseUrl}/businesses/${id}`);
  }

  getBusinessesList(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/businesses`);
  }

  private fixArrays(business: Business) {
    business.images = business.images.toString().split(',');
    business.tags = business.tags.toString().split(',');
  }
}
