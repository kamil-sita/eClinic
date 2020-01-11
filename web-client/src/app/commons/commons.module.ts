import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomePageComponent} from './home-page/home-page.component';
import {LoginPageComponent} from './login-page/login-page.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [HomePageComponent, LoginPageComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [HomePageComponent, LoginPageComponent]
})
export class CommonsModule {
}
