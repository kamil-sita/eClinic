import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomePageComponent} from './commons/home-page/home-page.component';
import {LoginPageComponent} from './commons/login-page/login-page.component';
import {DoctorListComponent} from './doctors/doctor-list/doctor-list.component';
import {DoctorDetailsComponent} from './doctors/doctor-details/doctor-details.component';
import {DoctorScheduleComponent} from './doctors/doctor-schedule/doctor-schedule.component';



const routes: Routes = [
  {path: '', redirectTo: '/homepage', pathMatch: 'full'},
  {path: 'homepage', component: HomePageComponent},
  {path: 'loginpage', component: LoginPageComponent},
  {path: 'doctorlist', component: DoctorListComponent},
  {path: 'doctorlist/:id', component: DoctorDetailsComponent},
  {path: 'doctorlist/:id/schedule', component: DoctorScheduleComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule {
}
