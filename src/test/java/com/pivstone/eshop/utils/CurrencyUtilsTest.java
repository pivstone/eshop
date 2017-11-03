package com.pivstone.eshop.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Currency;

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

    @Test
    public void testExchange() throws Exception {
        BigDecimal money = new BigDecimal(12);
        utils.exchange(Currency.getInstance("CNY"), money);
        assertEquals(money, new BigDecimal(12));
        assertEquals(utils.getMap().size(), 31);
    }

    @Test
    public void testGetMap() throws Exception {
        assertEquals(utils.getMap().size(), 31);
    }
}