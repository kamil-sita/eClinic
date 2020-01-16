import {Component, OnInit} from '@angular/core';
import {VisitsService} from "../../services/visits.service";
import {Visit} from "../../data/visits/visit";
import {Router} from "@angular/router";

@Component({
  selector: 'app-visit-list',
  templateUrl: './visit-list.component.html',
  styleUrls: ['./visit-list.component.css']
})
export class VisitListComponent implements OnInit {


  visits: Visit[];
  name: string = '';
  surname: string = '';

  constructor(private visitsService: VisitsService, private router: Router) { }

  ngOnInit() {
    this.visitsService.getVisits(this.name, this.surname).subscribe(visits => this.visits = visits);
  }

  toVisitDetails(userId) {
    //Tego nie do konca rozumiem xD
    this.router.navigate(['/visitlist/'+userId]);
  }

}
