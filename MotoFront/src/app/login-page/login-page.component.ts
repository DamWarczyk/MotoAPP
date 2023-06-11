import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Login} from "../interface/login";
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  myForm: FormGroup;
  login: Login = {
    email: '',
    password: '',
  };

  constructor(private fb: FormBuilder, private auth: AuthService, private route: Router) {
    this.myForm = this.fb.group({
      email: ['', [Validators.required, Validators.email
      ]],
      password: ['', [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$')
      ]]
    })
  }

  onClick() {
    this.login = this.myForm.value;
    this.auth.login(this.login).subscribe(
      ()=>{this.route.navigate(['/strona'])}),
      (error: HttpErrorResponse) => {alert(error.message)}
  }

  get email() {
    return this.myForm.get('email');
  }

  get password() {
    return this.myForm.get('password');
  }
}
