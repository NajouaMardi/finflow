// import { ApplicationConfig } from '@angular/core';
// import { provideRouter,withInMemoryScrolling, withEnabledBlockingInitialNavigation} from '@angular/router';
//
// import { routes } from './app.routes';
// import {HttpClient} from "@angular/common/http";
//
// export const appConfig: ApplicationConfig = {
//   providers: [provideRouter(routes,withEnabledBlockingInitialNavigation(),withInMemoryScrolling({
//     scrollPositionRestoration: 'enabled', // Scrolls to top on navigation
//     anchorScrolling: 'enabled'
//   })),HttpClient]
// };
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, HttpClient} from "@angular/common/http";
import {HttpTokenInterceptor} from "./services/interceptor/http-token.interceptor";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    HttpClient,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true
    }
  ]
};
