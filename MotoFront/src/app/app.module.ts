import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import { MainPageComponent } from './main-page/main-page/main-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgChartsModule } from 'ng2-charts';
import { BarChartComponent } from './main-page/charts/bar-chart/bar-chart.component';
import { LineChartComponent } from './main-page/charts/line-chart/line-chart.component';
import {MatRadioModule} from "@angular/material/radio";
import {HttpClientModule} from "@angular/common/http";
import { LoginPageComponent } from './login-page/login-page.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import {JwtModule} from "@auth0/angular-jwt";
import { NavigatorComponent } from './navigator/navigator.component';
import {CommonModule} from "@angular/common";
import { TableComponent } from './main-page/charts/table/table.component';
import { RiderPageComponent } from './rider-page/rider-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';

export function tokenGetter() {
  return localStorage.getItem("access_token");
}


@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    BarChartComponent,
    LineChartComponent,
    LoginPageComponent,
    NavigatorComponent,
    TableComponent,
    RiderPageComponent,
    RegisterPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    CommonModule,
    NgChartsModule.forRoot(),
    NgChartsModule,
    MatRadioModule,
    HttpClientModule,
    FormsModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatOptionModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        headerName: "Authorization",
        authScheme: "",
        allowedDomains: ["localhost:8080"],
        disallowedRoutes: ["http://example.com/examplebadroute/"],
      },
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
