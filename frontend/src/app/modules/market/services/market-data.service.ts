import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MarketDataService {
  private apiKey = 'd0po8n9r01qgccuacd90d0po8n9r01qgccuacd9g';
  private baseUrl = 'https://finnhub.io/api/v1';

  constructor(private http: HttpClient) {}

  getStockQuote(symbol: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/quote?symbol=${symbol}&token=${this.apiKey}`);
  }

  // Add other methods like getNews(), getMarketSymbols(), etc.
}
