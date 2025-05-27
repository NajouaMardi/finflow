package com.finflow.backend.Market;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.springframework.core.ParameterizedTypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class CorrelationService {

    private static final Logger log =
            LoggerFactory.getLogger(CorrelationService.class);

    // your ten symbols as you track them
    private static final String[] SYMBOLS = {
            "BTCUSDT","ETHUSDT","XRPUSDT","SOLUSDT","AVAXUSDT",
            "LTCUSDT","BCHUSDT","ADAUSDT","DOGEUSDT","MATICUSDT"
    };

    private final RestTemplate rt = new RestTemplate();

    /** symbol → top-5 correlated symbols */
    private final Map<String, List<String>> recommendations = new HashMap<>();

    /** expose to controller */
    public Map<String, List<String>> getRecommendations() {
        return recommendations;
    }

    @PostConstruct
    void build() {
        log.info("Building correlation matrix via Binance klines…");

        // Binance returns an array of arrays for klines
        ParameterizedTypeReference<List<List<Object>>> typeRef =
                new ParameterizedTypeReference<>() {};

        // ① fetch closing prices for the last 30 days
        Map<String,double[]> series = new LinkedHashMap<>();
        for (String sym : SYMBOLS) {
            String url = String.format(
                    "https://api.binance.com/api/v3/klines?symbol=%s&interval=1d&limit=30",
                    sym
            );
            try {
                ResponseEntity<List<List<Object>>> resp =
                        rt.exchange(url, HttpMethod.GET, null, typeRef);

                List<List<Object>> data = resp.getBody();
                if (data == null) throw new IllegalStateException("No body");

                // extract close price (index 4) from each candle
                double[] closes = data.stream()
                        .mapToDouble(c -> Double.parseDouble((String)c.get(4)))
                        .toArray();

                series.put(sym, closes);
                log.debug("Fetched {} closes for {}", closes.length, sym);
            } catch (Exception ex) {
                log.warn("Binance klines failed for {}: {}", sym, ex.toString());
            }
        }

        // ② compute Pearson correlations pairwise
        org.apache.commons.math3.stat.correlation.PearsonsCorrelation pc =
                new org.apache.commons.math3.stat.correlation.PearsonsCorrelation();

        for (String s1 : SYMBOLS) {
            double[] a = series.get(s1);
            if (a == null || a.length == 0) continue;

            List<Map.Entry<String,Double>> list = new ArrayList<>();
            for (String s2 : SYMBOLS) {
                if (s1.equals(s2)) continue;
                double[] b = series.get(s2);
                if (b != null && b.length == a.length) {
                    double corr = pc.correlation(a, b);
                    list.add(Map.entry(s2, corr));
                }
            }

            // ③ sort descending and take top 5
            list.sort((x,y) -> Double.compare(y.getValue(), x.getValue()));
            List<String> top5 = new ArrayList<>();
            for (int i = 0; i < Math.min(5, list.size()); i++) {
                top5.add(list.get(i).getKey());
            }

            recommendations.put(s1, top5);
            log.info("Recs for {} → {}", s1, top5);
        }

        log.info("Correlation build complete.");
    }
}