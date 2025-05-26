package com.finflow.backend.Config;
import com.finflow.backend.Handler.TradeWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tradeWebSocketHandler(), "/stocks").setAllowedOriginPatterns("http://localhost:4200");
    }

    @Bean
    public WebSocketHandler tradeWebSocketHandler() {
        return new TradeWebSocketHandler();
    }
}