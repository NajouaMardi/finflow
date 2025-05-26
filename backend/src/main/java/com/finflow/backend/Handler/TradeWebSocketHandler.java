package com.finflow.backend.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Same class, same package, same bean method.
 * Only the inner logic changed:
 *   • opens ONE upstream socket to Finnhub when the first browser connects
 *   • relays every tick to all connected browsers
 */
@Component
public class TradeWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    /* ——— Finnhub stuff ——————————————————————————————— */
    @Value("${finnhub.token}")           // Spring injects the token value
    private String apiToken;

    private final OkHttpClient http = new OkHttpClient();
    private okhttp3.WebSocket upstream;
    private final AtomicBoolean started = new AtomicBoolean(false);
    /* ———————————————————————————————————————————————— */

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        startUpstreamIfNeeded();         // make sure Finnhub is streaming
    }

    /* ------------------------------------------------------------------ */
    private void startUpstreamIfNeeded() {

        if (!started.compareAndSet(false, true)) return; // already running

        Request req = new Request.Builder()
                .url("wss://ws.finnhub.io?token=" + apiToken)
                .build();

        upstream = http.newWebSocket(req, new WebSocketListener() {

            @Override public void onOpen(okhttp3.WebSocket ws, Response res) {

                String[] symbols = {
                        "BINANCE:BTCUSDT",
                        "BINANCE:ETHUSDT",
                        "BINANCE:XRPUSDT",
                        "BINANCE:SOLUSDT",
                        "BINANCE:AVAXUSDT",
                        "BINANCE:LTCUSDT",
                        "BINANCE:BCHUSDT",
                        "BINANCE:ADAUSDT",
                        "BINANCE:DOGEUSDT",
                        "BINANCE:MATICUSDT"
                        // … add up to ~30 symbols on the free tier
                };

                for (String s : symbols) {
                    ws.send("{\"type\":\"subscribe\",\"symbol\":\"" + s + "\"}");
                }
                System.out.println("♦ Subscribed to " + symbols.length + " crypto pairs");
            }


            @Override public void onMessage(okhttp3.WebSocket ws, String text) {
                broadcast(text);                       // relay to browsers
            }

            @Override public void onFailure(okhttp3.WebSocket ws,
                                            Throwable t, Response res) {
                t.printStackTrace();
                started.set(false);                    // allow retry
            }
        });
    }

    /* ------------------------------------------------------------------ */
    private void broadcast(String rawJson) {
        sessions.removeIf(s -> !s.isOpen());

        TextMessage msg = new TextMessage(rawJson);    // Finnhub frame as-is
        for (WebSocketSession s : sessions) {
            try { s.sendMessage(msg); }
            catch (IOException ignored) { }
        }
    }
}
