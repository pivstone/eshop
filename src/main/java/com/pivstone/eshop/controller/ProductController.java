package com.pivstone.eshop.controller;

import com.pivstone.eshop.domain.Product;
import com.pivstone.eshop.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */

@RestController("product")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepo repo;

    @RequestMapping(method = RequestMethod.GET)
    private Product show(@PathVariable UUID productId) {
        return repo.findOne(productId);
    }
}
