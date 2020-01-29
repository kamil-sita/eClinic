import { Component, OnInit } from '@angular/core';
import {Visit} from "../../data/visits/visit";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {VisitsService} from "../../services/visits.service";
import {Location} from "@angular/common";
import {MedicalService} from "../../data/services/medical-service";


@Component({
  selector: 'app-visit-details',
  templateUrl: './visit-details.component.html',
  styleUrls: ['./visit-details.component.css']
})
export class VisitDetailsComponent implements OnInit {

  visit: Visit;
  visitId;

  constructor(private visitsService: VisitsService, private route: ActivatedRoute, private location: Location, private router: Router) {
  }

  ngOnInit() {
    this.visitId = this.route.snapshot.paramMap.get('id');
    this.visitsService.getVisit(this.visitId).subscribe(vis => this.visit = vis);
  }

  backClicked() {
    this.location.back();
  }
}
