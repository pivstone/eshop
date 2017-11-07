package com.pivstone.eshop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 * Exchange rates come from fixer.io
 */
@Component
@Slf4j
public class CurrencyUtils {
    private OkHttpClient client = new OkHttpClient();
    private Map<String, Double> rates;
    private String url = "http://api.fixer.io/latest";
    private ObjectMapper mapper = new ObjectMapper();

    public BigDecimal exchange(Currency target, BigDecimal money) {
        double rate = this.rates.get(target.getCurrencyCode());
        return money.divide(BigDecimal.valueOf(rate), BigDecimal.ROUND_DOWN);
    }

    @PostConstruct
    public void init() {
        update();
    }

    /**
     * for unit test inject
     *
     * @param url MockerWebServer URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Update the exchange rates
     */
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void update() {
        this.rates = fetchRates();
    }

    private Map<String, Double> fetchRates() {
        Map<String, Double> rates = new HashMap<>();
        Request request = new Request.Builder()
                .url(this.url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                String json = response.body().string();
                log.info(json);
                Fixer data = mapper.readValue(json, Fixer.class);
                rates = data.getRates();
            } else {
                log.error("Update exchange rate from fiex failed:{}", response.code());
            }
        } catch (IOException e) {
            log.error("exchange rates update failed, cause: {}", e);
        }
        return rates;
    }

    public Map<String, Double> getMap() {
        return this.rates;
    }

}
