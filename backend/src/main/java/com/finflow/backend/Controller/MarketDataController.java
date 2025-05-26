//package com.finflow.backend.Controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class MarketDataController {
//    // This class will handle WebSocket connections and market data updates
//    // For example, you can use @MessageMapping to handle incoming messages
//    // and @SendTo to broadcast messages to subscribed clients.
//
//    // Example method to handle market data updates
//    // @MessageMapping("/updateMarketData")
//    // @SendTo("/topic/marketData")
//    // public MarketData updateMarketData(MarketData marketData) {
//    //     // Process the market data and return it to the subscribers
//    //     return marketData;
//    // }
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    // For testing: send a message every 5 seconds to clients subscribed to "/topic/market"
//    @Scheduled(fixedRate = 5000)
//    public void sendMarketData() {
//        String fakeData = "{\"symbol\":\"AAPL\",\"price\":" + (150 + Math.random() * 10) + "}";
//        messagingTemplate.convertAndSend("/topic/market", fakeData);
//    }
//
//}
