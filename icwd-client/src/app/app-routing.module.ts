import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BusinessListComponent } from './business-list/business-list.component';
import { CreateBusinessComponent } from './create-business/create-business.component';
import { UpdateBusinessComponent } from './update-business/update-business.component';
import { BusinessDetailsComponent } from './business-details/business-details.component';

const routes: Routes = [
  {path: 'businesses', component: BusinessListComponent},
  {path: 'create-business', component: CreateBusinessComponent},
  {path: '', redirectTo: 'businesses', pathMatch: 'full'},
  {path: 'update-business/:business', component: UpdateBusinessComponent},
  {path: 'business-details/:business', component: BusinessDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
