import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomePageComponent} from './commons/home-page/home-page.component';
import {LoginPageComponent} from "./commons/login-page/login-page.component";


const routes: Routes = [
  {path: 'homepage', component: HomePageComponent},
  {path: 'loginpage', component: LoginPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule {
}
