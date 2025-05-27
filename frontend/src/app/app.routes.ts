import { Routes } from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {ActivateAccountComponent} from "./pages/activate-account/activate-account.component";
import {HomeComponent} from "./pages/home/home.component";
import {authGuard} from "./services/guard/auth.guard";
import {MarketAnalysisComponent} from "./modules/market/pages/market-analysis.component";

export const routes: Routes = [
  {
    path:'login',
    component:LoginComponent
  },

  {
    path:'register',
    component:RegisterComponent

  },

  {
    path:'activate-account',
    component:ActivateAccountComponent

  },

  {
    path:'home',
    component:HomeComponent

  },
  {
    path: "assistant",
    loadChildren:() => import('./modules/assistant/assistant.module').then(m => m.AssistantModule),
    canActivate:[authGuard]
  },
  {

    path: 'market-analysis',
    component: MarketAnalysisComponent
  }

];
