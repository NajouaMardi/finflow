// import { Component } from '@angular/core';
// import { CommonModule } from '@angular/common';
//
// interface TickerView { price: number; increased: boolean; }
//
// @Component({
//   selector: 'market-analysis',
//   standalone: true,
//   imports: [CommonModule],
//   templateUrl: './market-analysis.component.html',
//   styleUrls: ['./market-analysis.component.css']
// })
// export class MarketAnalysisComponent {
//
//   stocks: Record<string, TickerView> = {};
//   private last: Record<string, number> = {};
//   private ws = new WebSocket('ws://localhost:8088/api/v1/stocks');
//
//   /* simple static icon map */
//   icon(symbol: string): string {
//     const base = symbol.split(':')[1].replace('USDT','');
//     return ({
//       BTC:  'https://cryptologos.cc/logos/bitcoin-btc-logo.svg',
//       ETH:  'https://cryptologos.cc/logos/ethereum-eth-logo.svg',
//       XRP:  'https://cryptologos.cc/logos/xrp-xrp-logo.svg',
//       SOL:  'https://cryptologos.cc/logos/solana-sol-logo.svg',
//       AVAX: 'https://cryptologos.cc/logos/avalanche-avax-logo.svg'
//     } as Record<string,string>)[base] || '/assets/default.svg';
//   }
//
//   constructor() {
//     this.ws.onmessage = (e) => {
//       const f = JSON.parse(e.data);
//       if (f.type !== 'trade') { return; }
//
//       for (const t of f.data) {
//         const sym  = t.s as string;
//         const p    = t.p as number;
//         const inc  = p > (this.last[sym] ?? 0);
//
//         this.last[sym]   = p;
//         this.stocks[sym] = { price: p, increased: inc };
//       }
//     };
//   }
// }
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

interface TickerView { price: number; increased: boolean; }

@Component({
  selector: 'market-analysis',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './market-analysis.component.html',
  styleUrls: ['./market-analysis.component.css']
})
export class MarketAnalysisComponent {

  stocks: Record<string, TickerView> = {};
  private last: Record<string, number> = {};
  private ws = new WebSocket('ws://localhost:8088/api/v1/stocks');

  /** robust icon map — works for BTCUSDT, BTC/USD, BTC, etc. */
  icon(symbol: string): string {
    // 1️⃣ isolate the part after the “:”, then strip quote-currency
    let base = symbol.includes(':') ? symbol.split(':')[1] : symbol;
    base = base.replace(/USDT|USD|_USD/gi, '').toUpperCase();

    // 2️⃣ map to a PNG icon (CoinCap serves correct headers for <img>)
    const map: Record<string,string> = {
      BTC : 'assets/icons/btc.png',
      ETH : 'assets/icons/eth.png',
      XRP : 'assets/icons/xrp.png',
      SOL : 'assets/icons/sol.png',
      AVAX: 'assets/icons/avax.png',
      LTC:  'assets/icons/ltc.png',
      BCH:  'assets/icons/bch.png',
      ADA:  'assets/icons/ada.png',
      DOGE: 'assets/icons/doge.png'
    };

    return map[base] ?? '/assets/default.svg';
  }

  constructor() {
    this.ws.onmessage = (e) => {
      const frame = JSON.parse(e.data);
      if (frame.type !== 'trade' || !frame.data) { return; }

      for (const t of frame.data) {
        const sym  = t.s as string;
        const price = t.p as number;
        const inc   = price > (this.last[sym] ?? 0);

        this.last[sym]   = price;
        this.stocks[sym] = { price, increased: inc };
      }
    };
  }
}
