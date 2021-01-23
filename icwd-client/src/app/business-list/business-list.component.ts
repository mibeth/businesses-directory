import { Component, OnDestroy, OnInit } from '@angular/core';
import { Business } from '../business'
import { BusinessService } from '../business.service'
import { Router } from '@angular/router';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-business-list',
  templateUrl: './business-list.component.html',
  styleUrls: ['./business-list.component.css']
})
export class BusinessListComponent implements OnDestroy, OnInit {

  businesses: Business[];
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();

  constructor(private businessService: BusinessService,
    private router: Router) { }

  ngOnInit(): void {
    this.getBusinesses();
    this.dtOptions = {
          pagingType: 'full_numbers'
        };
  }

  private getBusinesses(){
    this.businessService.getBusinessesList().subscribe(data => {
      this.businesses = data;
      this.dtTrigger.next();
    });
  }

  businessDetails(business: Business){
    this.router.navigate(['business-details', JSON.stringify(business)]);
  }

  updateBusiness(business: Business){
    this.router.navigate(['update-business', JSON.stringify(business)]);
  }

  deleteBusiness(id: string){
    this.businessService.deleteBusiness(id).subscribe( data => {
      console.log(data);
      this.getBusinesses();
    })
  }

  ngOnDestroy(): void {
      this.dtTrigger.unsubscribe();
    }
}
