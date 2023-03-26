import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { delay, map, switchMap } from 'rxjs/operators';
import * as jwt_decode from 'jwt-decode';
import * as moment from 'moment';

import { environment } from '../../../environments/environment';
import { of, EMPTY, BehaviorSubject, Observable, forkJoin } from 'rxjs';
import { User } from '../classes/user';
import { UserDetails } from '../classes/user-details';
import { countryMeta, currencyMeta, MetadataInterface, OptionsInterface, qualificationStatusMeta, reviewStatusMeta, statusMeta } from '../interfaces/interface';
import { STATUS_OPTION } from '../constants/constant';


@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    private currentUserSubject!: BehaviorSubject<User | null>;
    private currentUserDetailsSubject!: BehaviorSubject<UserDetails | null>;
    private currentMetadaSubject!: BehaviorSubject<OptionsInterface | null>;
    public currentUser: Observable<any>;
    public currentUserDetails: Observable<any>;
    public currentMetadata: Observable<any>;

    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
        const localUser = this.localStorage.getItem('currentUser');

        this.currentUserSubject = new BehaviorSubject<any>(localUser ? JSON.parse(localUser) : null);
        this.currentUser = this.currentUserSubject.asObservable();
        this.currentUserDetailsSubject = new BehaviorSubject<any>(null);
        this.currentUserDetails = this.currentUserDetailsSubject.asObservable();
        this.currentMetadaSubject = new BehaviorSubject<any>(null);
        this.currentMetadata = this.currentMetadaSubject.asObservable();

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
            switchMap(response => {
                console.log(response);
                const userdetails = this.http.get(`/medley/api/getUserDetails?emailId=${email}`);
                const metadata = this.http.get('/medley/api/metadata').pipe(map(res=>res as MetadataInterface));
                return forkJoin([userdetails, metadata]).pipe(map(results => {
                    console.log(results);
                    this.currentUserDetailsSubject.next(new UserDetails(results[0]));
                    this.currentMetadaSubject.next(this.generateMetadata(results[1]));
                    return results[0];
                }))
            })
        );




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
    generateMetadata(metadata: MetadataInterface): OptionsInterface {
        return {
            statusList: metadata.statusList.map((o: statusMeta) => {
                return {
                    label: o.statusName,
                    value: o.statusCode
                };
            }),
            reviewStatusList: metadata.reviewStatusList.map((o: reviewStatusMeta) => {
                return {
                    label: o.reviewName,
                    value: o.reviewCode
                };
            }),
            currencyList: metadata.currencyList.map((o: currencyMeta) => {
                return {
                    label: o.currencyName,
                    value: o.currencyCode
                };
            }),
            qualificationStatusList: metadata.qualificationStatusList.map((o: qualificationStatusMeta) => {
                return {
                    label: o.qualificationName,
                    value: o.qualificationCode
                };
            }),
            countryList: metadata.countryList.map((o: countryMeta) => {
                return {
                    label: o.countryName,
                    value: o.countryCode
                };
            })
        };
    };



    fetchUserDetails() {
        const user = this.getCurrentUser();
        const userdetails = this.http.get(`/medley/api/getUserDetails?emailId=${user.email}`);
        const metadata = this.http.get('/medley/api/metadata').pipe(map(res=>res as MetadataInterface));
        forkJoin([userdetails, metadata]).pipe(map(results => {
            this.currentUserDetailsSubject.next(new UserDetails(results[0]));
            this.currentMetadaSubject.next(this.generateMetadata(results[1]));
            return results[0];
        })).subscribe();


    }

    logout(): void {
        // clear token remove user from local storage to log user out
        this.localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
        this.currentUserDetailsSubject.next(null);
        this.currentMetadaSubject.next(null);
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

    getCurrentMetadata(): any{
        return this.currentMetadaSubject.value;
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
