import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Business } from './business';

@Injectable({
  providedIn: 'root'
})
export class BusinessService {

  private baseUrl = "http://localhost:8080/directory";

  constructor(private http: HttpClient) { }

  getBusinessById(id: string): Observable<Business> {
    return this.http.get<Business>(`${this.baseUrl}/${id}`);
  }

  createBusiness(business: Business): Observable<Object> {
    business.images = business.images.toString().split(',');
    business.tags = business.tags.toString().split(',');
    return this.http.post(`${this.baseUrl}/business`, business);
  }

  updateBusiness(id: string, business: Business): Observable<Object>{
    return this.http.put(`${this.baseUrl}/${id}`, business);
  }

  deleteBusiness(id: string): Observable<Object> {
    return this.http.delete(`${this.baseUrl}/businesses/${id}`);
  }

  getBusinessesList(): Observable<Business[]> {
    return this.http.get<Business[]>(`${this.baseUrl}/businesses`);
  }

}
