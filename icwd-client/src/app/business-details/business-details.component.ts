import { Component, OnInit } from '@angular/core';
import { Business } from '../business';
import { ActivatedRoute } from '@angular/router';
import { BusinessService } from '../business.service';

@Component({
  selector: 'app-business-details',
  templateUrl: './business-details.component.html',
  styleUrls: ['./business-details.component.css']
})
export class BusinessDetailsComponent implements OnInit {

  id: string
  business: Business
  constructor(private route: ActivatedRoute, private businessService: BusinessService) { }

  ngOnInit(): void {
    this.business = JSON.parse(this.route.snapshot.params["business"]);
  }

}
