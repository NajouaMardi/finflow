import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

interface TickerView {
  price: number;
  increased: boolean;
}

@Component({
  selector: 'market-analysis',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './market-analysis.component.html',
  styleUrls: ['./market-analysis.component.css']
})
export class MarketAnalysisComponent {

  /** one entry per symbol */
  stocks: Record<string, TickerView> = {};

  /** keep previous price so we can flag ↑ / ↓ */
  private lastPrice: Record<string, number> = {};

  private webSocket: WebSocket;

  constructor() {
    this.webSocket = new WebSocket('ws://localhost:8088/api/v1/stocks');

    this.webSocket.onmessage = (event) => {
      const frame = JSON.parse(event.data);

      /* ignore pings or other frame types */
      if (frame.type !== 'trade' || !frame.data) { return; }

      for (const t of frame.data) {
        const symbol   = t.s as string;
        const price    = t.p as number;

        const increased = price > (this.lastPrice[symbol] ?? 0);
        this.lastPrice[symbol] = price;

        this.stocks[symbol] = { price, increased };
      }
    };
  }
}

// import { Component } from '@angular/core';
// import { CommonModule } from '@angular/common';
//
// @Component({
//   selector: 'app-market-analysis',
//   standalone: true,
//   imports: [CommonModule],
//   templateUrl: './market-analysis.component.html',
//   styleUrls: ['./market-analysis.component.css']
// })
// export class MarketAnalysisComponent {}
