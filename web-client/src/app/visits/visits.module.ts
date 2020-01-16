import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisitListComponent } from './visit-list/visit-list.component';
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [VisitListComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [VisitListComponent]
})
export class VisitsModule { }
