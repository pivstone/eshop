package com.pivstone.eshop.utils;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
@Data
public class Fixer {
    private String base;
    private Date date;
    private Map<String,Double> rates;

}
