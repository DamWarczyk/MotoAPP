import { Component } from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigator',
  templateUrl: './navigator.component.html',
  styleUrls: ['./navigator.component.css']
})
export class NavigatorComponent {

  constructor(
    private router: Router,
    public auth: AuthService,
  ) {}

  hasRole(role: string): boolean {
    return this.auth.user?.roles.includes(role) || false;
  }

  ngOnInit(): void {
  }

  logOut() {
    this.auth.logout();
  }
}
