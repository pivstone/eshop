package com.pivstone.eshop.utils;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Currency;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyUtilsTest {

    @Autowired
    private CurrencyUtils utils;

    private String fixer_content = "";

    @Before
    public void setUp() throws IOException {
        Path path = Paths.get("src/test/resources/fixtures/vcr/fixer.json");
        fixer_content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }


    @Test
    public void tesFetchRates() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(fixer_content));

        // Start the server.
        server.start();
        utils.setUrl(server.url("/").toString());
        utils.update();
        Map map = utils.getMap();
        assertEquals(map.size(), 31);
        server.shutdown();
    }

    @Test
    public void testExchange() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(fixer_content));
        // Start the server.
        server.start();
        utils.setUrl(server.url("/").toString());
        utils.update();
        BigDecimal cny = utils.exchange(Currency.getInstance("CNY"), BigDecimal.valueOf(12.31d));
        assertEquals(BigDecimal.valueOf(1.59d), cny);
        server.shutdown();
    }
}