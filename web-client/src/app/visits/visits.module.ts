import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisitListComponent } from './visit-list/visit-list.component';



@NgModule({
  declarations: [VisitListComponent],
  imports: [
    CommonModule
  ],
  exports: [VisitListComponent]
})
export class VisitsModule { }
