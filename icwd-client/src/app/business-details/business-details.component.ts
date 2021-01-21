import { Component, OnInit } from '@angular/core';
import { Business } from '../business';
import { ActivatedRoute } from '@angular/router';
import { BusinessService } from '../business.service';
import { Subscription } from "rxjs";

@Component({
  selector: 'app-business-details',
  templateUrl: './business-details.component.html',
  styleUrls: ['./business-details.component.css']
})
export class BusinessDetailsComponent implements OnInit {

  business: Business
  private subscription: Subscription

  constructor(private route: ActivatedRoute, private businessService: BusinessService) { }

  ngOnInit(): void {
    this.subscription = this.route.params.subscribe(
          (param: any) => this.business = JSON.parse(param['business'])
        );
  }

  ngOnDestroy() {
      this.subscription.unsubscribe();
    }
}
