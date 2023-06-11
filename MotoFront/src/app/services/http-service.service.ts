import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MotorDriversData} from "../interface/motor-drivers-data";
import {Token} from "../interface/token";
import {Login} from "../interface/login";
import {Rider} from "../interface/rider";
@Injectable({
  providedIn: 'root'
})
export class HttpServiceService {

  constructor(private http: HttpClient) { }

  public getMotoSportData(): Observable<MotorDriversData>{
    return this.http.get<any>(`http://localhost:8080/api/riders/grid`)
  }

  public login(login: Login): Observable<Token>{
    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };
    return this.http.post<any>(`http://localhost:8080/login`, `email=${login.email}&password=${login.password}`, options)
  }

  public addRider(rider: Rider): Observable<Rider>{
    return this.http.post<any>(`http://localhost:8080/api/riders`, rider)
  }

  public exportRidersToCSV(): Observable<Blob>{
    return this.http.get(`http://localhost:8080/api/riders/riderCSV`, { responseType: 'blob' })
  }

  public exportRidersToJSON(): Observable<Blob>{
    return this.http.get(`http://localhost:8080/api/riders`, { responseType: 'blob' })
  }

  public getAllRider(): Observable<Rider[]>{
    return this.http.get<any>(`http://localhost:8080/api/riders`)
  }
}
