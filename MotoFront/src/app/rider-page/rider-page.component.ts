import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Rider} from "../interface/rider";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";
import {HttpServiceService} from "../services/http-service.service";
import {HttpErrorResponse} from "@angular/common/http";
import { saveAs } from 'file-saver';
@Component({
  selector: 'app-rider-page',
  templateUrl: './rider-page.component.html',
  styleUrls: ['./rider-page.component.css']
})
export class RiderPageComponent implements OnInit{

  ngOnInit(): void {
    this.getAllRider()
  }
  myForm: FormGroup;

  rider: Rider = {
    number: 0,
    name: '',
    team: ''
  };

  ridersList: Rider[] = []

  constructor(private fb: FormBuilder, private auth: AuthService, private route: Router, private http: HttpServiceService) {
    this.myForm = this.fb.group({
      number: ['', [Validators.required]],
      name: ['', [Validators.required]],
      team: ['', [Validators.required]]
    })
  }

  onClick() {
    this.rider = this.myForm.value;
    this.http.addRider(this.rider).subscribe(() => this.getAllRider())
  }

  get name() {
    return this.myForm.get('name');
  }

  get team() {
    return this.myForm.get('team');
  }

  get number(){
    return this.myForm.get('number');
  }

  exportToCSV() {
    this.http.exportRidersToCSV().subscribe( (data) => {saveAs(data, `drivers.csv`)})
  }

  exportToJSON() {
    console.log("WHERE JSON?")
    this.http.exportRidersToJSON().subscribe( (data) => {saveAs(data, `drivers.json`)})
  }

  getAllRider(){
    this.http.getAllRider().subscribe( (data) => this.ridersList = data)
  }


}
