import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SupplierInterface } from './supplier.interface';
import { map } from 'rxjs/operators';

@Injectable()
export class SupplierService{
    selectedSupplier!:SupplierInterface;
    headers = new HttpHeaders().set("Content-Type", "application/json");
    constructor(private http: HttpClient) {

     }

    
     fetchSuppliers(request:any): Observable<any>{
        return this.http.post('/medley/api/suppliers', request, {headers:this.headers})
     }

     addSupplier(request:any): Observable<any>{
        return this.http.post('/medley/api/supplier', request, {headers:this.headers, responseType: 'text' });
     }
     editSupplier(request:any): Observable<any>{
        return this.http.put('/medley/api/supplier', request, {headers:this.headers, responseType: 'text' });
     }
     approveSupplier(request:any): Observable<any>{
        return this.http.put(`/medley/api/approveRejectSupplier`, request, {headers:this.headers, responseType: 'text' });
     }

     fetchPendingSuppliers(request:any): Observable<any>{
      return this.http.post('/medley/api/suppliers', request, {headers:this.headers})
   }
     
    
}