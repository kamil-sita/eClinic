import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DoctorListComponent } from './doctor-list/doctor-list.component';



@NgModule({
  declarations: [DoctorListComponent],
  imports: [
    CommonModule
  ],
  exports: [DoctorListComponent]
})
export class DoctorsModule { }
