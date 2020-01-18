import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisitListComponent } from './visit-list/visit-list.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { VisitAddComponent } from './visit-add/visit-add.component';



@NgModule({
  declarations: [VisitListComponent, VisitAddComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [VisitListComponent, VisitAddComponent]
})
export class VisitsModule { }
