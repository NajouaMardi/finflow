package com.finflow.backend.Controller;

import com.finflow.backend.Market.CorrelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
@CrossOrigin(origins = "http://localhost:4200")
public class RecommendationController {
    private final CorrelationService svc;
    public RecommendationController(CorrelationService svc) {
        this.svc = svc;
    }

    @GetMapping("/{symbol}")
    public List<String> recs(@PathVariable String symbol) {
        String base = symbol.contains(":")
                ? symbol.split(":", 2)[1]
                : symbol;
        return svc.getRecommendations().getOrDefault(base, List.of());
    }
}
