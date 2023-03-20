import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerInterface } from './customer.interface';

@Injectable()
export class CustomerService {
    selectedCustomer!:CustomerInterface;
    headers = new HttpHeaders().set("Content-Type", "application/json");
    constructor(private http: HttpClient) {

     }

    
     fetchCustomers(request:any): Observable<any>{
        return this.http.post('/medley/api/customers', request, {headers:this.headers})
     }

     addCustomer(request:any): Observable<any>{
        return this.http.post('/medley/api/customer', request, {headers:this.headers, responseType: 'text' });
     }
     editCustomer(request:any): Observable<any>{
        return this.http.put('/medley/api/customer', request, {headers:this.headers, responseType: 'text' });
     }
     approveCustomer(request:any): Observable<any>{
        return this.http.put(`/medley/api/approveRejectCustomer`, request, {headers:this.headers, responseType: 'text' });
     }
    
}