import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientListComponent } from './patient-list/patient-list.component';
import {FormsModule} from '@angular/forms';
import { PatientDetailsComponent } from './patient-details/patient-details.component';
import { IllnessHistoryComponent } from './illness-history/illness-history.component';



@NgModule({
  declarations: [PatientListComponent, PatientDetailsComponent, IllnessHistoryComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports:[PatientListComponent]
})
export class PatientsModule { }
