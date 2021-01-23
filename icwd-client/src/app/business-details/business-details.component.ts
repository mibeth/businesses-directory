import { Component, OnInit } from '@angular/core';
import { Business } from '../business';
import { ActivatedRoute } from '@angular/router';
import { BusinessService } from '../business.service';
import { Subscription } from "rxjs";
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-business-details',
  templateUrl: './business-details.component.html',
  styleUrls: ['./business-details.component.css'],
  providers: [NgbCarouselConfig]
})
export class BusinessDetailsComponent implements OnInit {

  business: Business
  private subscription: Subscription

  constructor(private route: ActivatedRoute,
              private businessService: BusinessService,
              config: NgbCarouselConfig) {
       config.interval = 3000;
       config.keyboard = false;
       config.pauseOnHover = true;
       config.pauseOnFocus = true;
       config.showNavigationArrows = true;
       config.showNavigationIndicators = true;
   }

  ngOnInit(): void {
    this.subscription = this.route.params.subscribe(
          (param: any) => this.business = JSON.parse(param['business'])
        );
  }

  ngOnDestroy() {
      this.subscription.unsubscribe();
    }
}
