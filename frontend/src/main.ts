import { bootstrapApplication } from '@angular/platform-browser';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';

import { ApiModule } from './app/services/api.module'; // adjust the path to ApiModule if needed

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    importProvidersFrom(
      HttpClientModule,
      ApiModule.forRoot({
        rootUrl: 'http://localhost:8088/api/v1' // <-- replace with your actual backend URL
      })
    ),
    ...(appConfig.providers || []) // keep your original providers from appConfig
  ]
}).catch((err) => console.error(err));
