import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {UserHomeComponent} from "./pages/user-home/user-home.component";
import {authGuard} from "../../services/guard/auth.guard";
import {SetUpComponent} from "./pages/set-up/set-up.component";

const routes: Routes = [
  {
    path:'',
    component:MainComponent,
    canActivate:[authGuard],
    children: [

      {
        path:'',
        component: UserHomeComponent,
        canActivate:[authGuard]

      },
      {
        path:'set-up',
        component: SetUpComponent,
        canActivate:[authGuard]

      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssistantRoutingModule { }
