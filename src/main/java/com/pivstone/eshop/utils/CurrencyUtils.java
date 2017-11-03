package com.pivstone.eshop.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Component
@Slf4j
public class CurrencyUtils {
    private OkHttpClient client = new OkHttpClient();
    private HashMap<String, Double> rates;
    private String url = "http://api.fixer.io/latest";
    private ScheduledExecutorService scheduler;

    public BigDecimal exchange(Currency target, BigDecimal money) {
        double rate = this.rates.get(target.getCurrencyCode());
        return money.divide(BigDecimal.valueOf(rate), BigDecimal.ROUND_DOWN);
    }

    @PostConstruct
    public void init() {
        scheduler = Executors.newScheduledThreadPool(1);
        final Runnable runnable = () -> {
            HashMap<String, Double> rates = fetchRates();
            this.rates = rates;
        };
        //schedule update the rates
        scheduler.scheduleAtFixedRate(runnable, 0, 120, TimeUnit.SECONDS);

    }

    private HashMap<String, Double> fetchRates() {
        HashMap<String, Double> rates = new HashMap<>();
        Request request = new Request.Builder()
                .url(url)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            log.info(json);
            Map data = mapper.readValue(json, Map.class);
            rates = (HashMap<String, Double>) data.get("rates");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rates;
    }

    public HashMap<String, Double> getMap() {
        return this.rates;
    }

}
