import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'market-analysis',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './market-analysis.component.html',
  styleUrls: ['./market-analysis.component.css']
})
export class MarketAnalysisComponent{
  title = 'WebSocketClient';
  stock: any = {};

  private webSocket: WebSocket;

  constructor() {
    this.webSocket = new WebSocket('ws://localhost:8088/api/v1/stocks');
    this.webSocket.onmessage = (event) => {
      this.stock = JSON.parse(event.data)
    };
  };
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
