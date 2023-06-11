import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./main-page/main-page/main-page.component";
import {LoginPageComponent} from "./login-page/login-page.component";
import {isLoggedGuard} from "./guards/is-logged.guard";
import {RiderPageComponent} from "./rider-page/rider-page.component";




const routes: Routes = [
  { path: '', redirectTo: '/strona', pathMatch: 'full' },
  { path: 'strona', component: MainPageComponent, canActivate: [isLoggedGuard] },
  { path: 'login', component: LoginPageComponent},
  { path: 'rider', component:RiderPageComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes)],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
