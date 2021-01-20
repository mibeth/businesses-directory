import { Component, OnInit } from '@angular/core';
import { Business } from '../business'
import { BusinessService } from '../business.service'
import { Router } from '@angular/router';
@Component({
  selector: 'app-business-list',
  templateUrl: './business-list.component.html',
  styleUrls: ['./business-list.component.css']
})
export class BusinessListComponent implements OnInit {

  businesses: Business[];

  constructor(private businessService: BusinessService,
    private router: Router) { }

  ngOnInit(): void {
    this.getBusinesses();
  }

  private getBusinesses(){
    this.businessService.getBusinessesList().subscribe(data => {
      this.businesses = data;
    });
  }

  businessDetails(id: string){
    this.router.navigate(['business-details', id]);
  }

  updateBusiness(id: string){
    this.router.navigate(['update-business', id]);
  }

  deleteBusiness(id: string){
    this.businessService.deleteBusiness(id).subscribe( data => {
      console.log(data);
      this.getBusinesses();
    })
  }
}
