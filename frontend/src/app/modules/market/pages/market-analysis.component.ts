import { Component, OnInit } from '@angular/core';
import { MarketDataService } from '../services/market-data.service';

@Component({
  selector: 'app-market-analysis',
  templateUrl: './market-analysis.component.html',
  styleUrls: ['./market-analysis.component.css']
})
export class MarketDataComponent implements OnInit {
  stockQuote: any;

  constructor(private marketDataService: MarketDataService) {}

  ngOnInit(): void {
    this.marketDataService.getStockQuote('AAPL').subscribe(data => {
      this.stockQuote = data;
    });
  }
}
