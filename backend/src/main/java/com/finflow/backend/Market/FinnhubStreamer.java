//package com.finflow.backend.Market;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.socket.WebSocketMessage;
//import org.springframework.web.reactive.socket.WebSocketSession;
//import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.time.Duration;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
///**
// * Opens ONE upstream WebSocket to Finnhub and broadcasts every
// * tick it receives to all browser sessions registered by TradeWebSocketHandler.
// */
//@Component                       // managed by Spring
//@RequiredArgsConstructor
//@Slf4j
//
//public class FinnhubStreamer {
//
//    /** API token from application.yml or env var FINNHUB_TOKEN */
//    @Value("${finnhub.token}")
//    private String apiToken;
//
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    /** All live browser connections */
//    private final List<WebSocketSession> downstream = new CopyOnWriteArrayList<>();
//
//    //───────────────────────────────────────────────────────────────────────
//    // 1. Connect to Finnhub once when the bean starts
//    //───────────────────────────────────────────────────────────────────────
//    @PostConstruct
//    void connectUpstream() {
//
//        URI uri = URI.create("wss://ws.finnhub.io?token=" + apiToken);
//        ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient();
//
//        client.execute(uri, this::handleUpstream)
//                .retryBackoff(Long.MAX_VALUE, Duration.ofSeconds(5),
//                        Duration.ofMinutes(5))
//                .subscribe();                 // fire-and-forget reactive chain
//
//        log.info("FinnhubStreamer: connecting to {}", uri);
//    }
//
//    //───────────────────────────────────────────────────────────────────────
//    // 2. Upstream session handler
//    //───────────────────────────────────────────────────────────────────────
//    private Mono<Void> handleUpstream(WebSocketSession upstream) {
//
//        // Subscribe to the symbols you need (add more send(...) calls as required)
//        upstream.send(Mono.just(
//                upstream.textMessage("{\"type\":\"subscribe\",\"symbol\":\"AMZN\"}")
//        )).subscribe();
//
//        log.info("FinnhubStreamer: subscribed to AMZN");
//
//        // For *every* message from Finnhub …
//        return upstream.receive()
//                .map(WebSocketMessage::getPayloadAsText)
//                .doOnNext(this::broadcast)   // … fan out to browsers
//                .then();                     // keep the session open
//    }
//
//    //───────────────────────────────────────────────────────────────────────
//    // 3. Called by your WebSocket handler for each browser connection
//    //───────────────────────────────────────────────────────────────────────
//    public void register(WebSocketSession session) {
//        downstream.add(session);
//        log.debug("FinnhubStreamer: session {} added (total {})",
//                session.getId(), downstream.size());
//    }
//
//    //───────────────────────────────────────────────────────────────────────
//    // 4. Fan-out helper
//    //───────────────────────────────────────────────────────────────────────
//    private void broadcast(String json) {
//
//        downstream.removeIf(ws -> !ws.isOpen());      // prune closed sockets
//
//        downstream.forEach(ws -> {
//            try {
//                ws.sendMessage(
//                        new org.springframework.web.socket.TextMessage(json));
//            } catch (Exception e) {
//                log.warn("FinnhubStreamer: failed to send to {}", ws.getId());
//            }
//        });
//    }
//}
