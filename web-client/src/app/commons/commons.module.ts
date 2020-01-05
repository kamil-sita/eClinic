import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page/home-page.component';
import {AppRoutingModule} from "../app-routing.module";
import { LoginPageComponent } from './login-page/login-page.component';



@NgModule({
  declarations: [HomePageComponent, LoginPageComponent],
  imports: [
    CommonModule
  ],
  exports: [HomePageComponent, LoginPageComponent]
})
export class CommonsModule { }
