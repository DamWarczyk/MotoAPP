import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Student} from "../interface/student";
import {HttpServiceService} from "../services/http-service.service";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthService} from "../services/auth.service";
import {Login} from "../interface/login";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {
  myForm: FormGroup;
  student: Student = {
    name: '',
    surname: '',
    email: '',
    password: '',
  }

  login: Login = {
    email: '',
    password: '',
  };

  constructor(private fb: FormBuilder, private httpService: HttpServiceService, private auth: AuthService, private route: Router) {
    this.myForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$')]]
    })
  }

  ngOnInit(): void {

  }

  onClick() {
    console.log("Zarejstrowano");
    this.student = this.myForm.value
    this.httpService.addStudent(this.student).subscribe(
      (response: Student) => {
        console.log(response);
        this.login.email = response.email
        this.login.password = this.myForm.get('password')?.value
        this.auth.login(this.login).subscribe(
          () => {
            this.route.navigate(['/strona'])
          }),
          (error: HttpErrorResponse) => {
            alert(error.message)
          }
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }


  get name() {
    return this.myForm.get('name');
  }

  get surname() {
    return this.myForm.get('surname');
  }

  get email() {
    return this.myForm.get('email');
  }

  get password() {
    return this.myForm.get('password')
  }


}
