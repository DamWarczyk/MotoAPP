import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {AuthService} from "../services/auth.service";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class isLoggedGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let isLog: boolean = false;
    this.auth.isLoggedIn$.subscribe(data => isLog = data.valueOf() )
    if(isLog){
      return true
    }
    else {
      this.router.navigate(['/login'])
      return false
    }
  }

}
