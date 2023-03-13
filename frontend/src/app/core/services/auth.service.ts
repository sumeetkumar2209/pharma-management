import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { delay, map } from 'rxjs/operators';
import * as jwt_decode from 'jwt-decode';
import * as moment from 'moment';

import { environment } from '../../../environments/environment';
import { of, EMPTY, BehaviorSubject, Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    private currentUserSubject!: BehaviorSubject<any>;
    public currentUser: Observable<any>;

    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
            const localUser = this.localStorage.getItem('currentUser');
            
            this.currentUserSubject = new BehaviorSubject<any>(localUser?JSON.parse(localUser):null);
            this.currentUser = this.currentUserSubject.asObservable();

    }

    login(email: string, password: string) {
        const headers = new HttpHeaders().set("Content-Type", "application/json").set('Authorization', 'Bearer ' + btoa(`${email}:${password}`));
        // return this.http.get('/authenticate', { headers: headers }).pipe(map(user => {
        //     // user['authdata'] = window.btoa(username + ':' + password);
        //     localStorage.setItem('currentUser', JSON.stringify(user));
        //     this.currentUserSubject.next(user);
        //     return user;
        // }));
        return of(true)
        .pipe(delay(1000),
        map((/*response*/) => {
            // set token property
            // const decodedToken = jwt_decode(response['token']);

            // store email and jwt token in local storage to keep user logged in between page refreshes
            this.localStorage.setItem('currentUser', JSON.stringify({
                token: 'aisdnaksjdn,axmnczm',
                isAdmin: true,
                email: 'john.doe@gmail.com',
                id: '12312323232',
                alias: 'john.doe@gmail.com'.split('@')[0],
                expiration: moment().add(1, 'days').toDate(),
                fullName: 'John Doe'
            }));

            return true;
        }));
    }

    logout(): void {
        // clear token remove user from local storage to log user out
        this.localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }

    getCurrentUser(): any {
        // TODO: Enable after implementation
        // return this.currentUserSubject.value;
        return {
            token: 'aisdnaksjdn,axmnczm',
            isAdmin: true,
            email: 'john.doe@gmail.com',
            id: '12312323232',
            alias: 'john.doe@gmail.com'.split('@')[0],
            expiration: moment().add(1, 'days').toDate(),
            fullName: 'John Doe'
        };
    }

    passwordResetRequest(email: string) {
        return of(true).pipe(delay(1000));
    }

    changePassword(email: string, currentPwd: string, newPwd: string) {
        return of(true).pipe(delay(1000));
    }

    passwordReset(email: string, token: string, password: string, confirmPassword: string): any {
        return of(true).pipe(delay(1000));
    }
}
