import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { delay, map, mergeMap } from 'rxjs/operators';
import * as jwt_decode from 'jwt-decode';
import * as moment from 'moment';

import { environment } from '../../../environments/environment';
import { of, EMPTY, BehaviorSubject, Observable } from 'rxjs';
import { User } from '../classes/user';
import { UserDetails } from '../classes/user-details';


@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    private currentUserSubject!: BehaviorSubject<User | null>;
    private currentUserDetailsSubject!: BehaviorSubject<UserDetails | null>;
    public currentUser: Observable<any>;
    public currentUserDetails: Observable<any>;

    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
        const localUser = this.localStorage.getItem('currentUser');

        this.currentUserSubject = new BehaviorSubject<any>(localUser ? JSON.parse(localUser) : null);
        this.currentUser = this.currentUserSubject.asObservable();
        this.currentUserDetailsSubject = new BehaviorSubject<any>(null);


        this.currentUserDetails = this.currentUserDetailsSubject.asObservable();
       
    }

    login(email: string, password: string) {
        const headers = new HttpHeaders().set('Authorization', 'Basic ' + btoa(`${email}:${password}`));
        //.set("Content-Type", "application/json")
        return this.http.post('/medley/api/authenticate', null, { headers: headers, responseType: 'text' }).pipe(map(response => {
            const user = new User(email, response);
            localStorage.setItem('currentUser', JSON.stringify(user));
            this.currentUserSubject.next(user);
            return user;
        }),
            mergeMap(response => this.http.get(`/medley/api/getUserDetails?emailId=${email}`), (responce, userDetails) => {
                this.currentUserDetailsSubject.next(new UserDetails(userDetails));
                return userDetails;
            }));
        // return of(true)
        // .pipe(delay(1000),
        // map((/*response*/) => {
        //     // set token property
        //     // const decodedToken = jwt_decode(response['token']);

        //     // store email and jwt token in local storage to keep user logged in between page refreshes
        //     this.localStorage.setItem('currentUser', JSON.stringify({
        //         token: 'aisdnaksjdn,axmnczm',
        //         isAdmin: true,
        //         email: 'john.doe@gmail.com',
        //         id: '12312323232',
        //         alias: 'john.doe@gmail.com'.split('@')[0],
        //         expiration: moment().add(1, 'days').toDate(),
        //         fullName: 'John Doe'
        //     }));

        //     return true;
        // }));
    }

    fetchUserDetails() {
       const user = this.getCurrentUser();
       this.http.get(`/medley/api/getUserDetails?emailId=${user.email}`).pipe(map(response => {
            this.currentUserDetailsSubject.next(new UserDetails(response));
            return response;
        })).subscribe();
           

    }

    logout(): void {
        // clear token remove user from local storage to log user out
        this.localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
        this.currentUserDetailsSubject.next(null);
    }

    getCurrentUser(): any {
        // TODO: Enable after implementation
        return this.currentUserSubject.value;
        // return {
        //     token: 'aisdnaksjdn,axmnczm',
        //     email: 'john.doe@gmail.com',
        // };
    }
    getCurrentUserDetails(): any {
        return this.currentUserDetailsSubject.value;
    }

    passwordResetRequest(email: string) {
        return of(true).pipe(delay(1000));
    }

    changePassword(email: string, currentPwd: string, newPwd: string) {
        // return of(true).pipe(delay(1000));
        const headers = new HttpHeaders().set('NewCredentials', 'Basic ' + btoa(`${email}:${currentPwd}:${newPwd}`));
        //.set("Content-Type", "application/json")
        return this.http.post('/medley/api/resetPassword', null, { headers: headers });
    }

    passwordReset(email: string, token: string, password: string, confirmPassword: string): any {
        return of(true).pipe(delay(1000));
    }
}
