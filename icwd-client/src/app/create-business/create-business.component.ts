import { Component, OnInit } from '@angular/core';
import { Business } from '../business';
import { Address } from '../address';
import { BusinessService } from '../business.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-business',
  templateUrl: './create-business.component.html',
  styleUrls: ['./create-business.component.css']
})
export class CreateBusinessComponent implements OnInit {

  business: Business = new Business();

  constructor(private businessService: BusinessService,
    private router: Router) { }

  ngOnInit(): void {
    this.business.address = new Address();
  }

  saveBusiness(){
    this.businessService.createBusiness(this.business).subscribe( data =>{
      console.log(data);
      this.goToBusinessList();
    },
    error => console.log(error));
  }

  goToBusinessList(){
    this.router.navigate(['/businesses']);
  }

  onSubmit(){
    console.log(this.business);
    this.saveBusiness();
  }
}
