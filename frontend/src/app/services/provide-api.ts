import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ApiModule } from './api.module'; // adjust path if needed

export function provideApiModule() {
  return importProvidersFrom(
    HttpClientModule,
    ApiModule.forRoot({ rootUrl: 'http://localhost:8088/api/v1' })
  );
}
