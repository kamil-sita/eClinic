import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DoctorListComponent } from './doctor-list/doctor-list.component';
import {FormsModule} from '@angular/forms';
import { DoctorDetailsComponent } from './doctor-details/doctor-details.component';



@NgModule({
  declarations: [DoctorListComponent, DoctorDetailsComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [DoctorListComponent]
})
export class DoctorsModule { }
