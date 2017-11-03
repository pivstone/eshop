package com.pivstone.eshop.controller;

import com.pivstone.eshop.domain.Category;
import com.pivstone.eshop.domain.Product;
import com.pivstone.eshop.repo.CategoryRepo;
import com.pivstone.eshop.repo.ProductRepo;
import com.pivstone.eshop.utils.CurrencyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Currency;
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

    @Autowired
    private CurrencyUtils utils;
    @Autowired
    private CategoryRepo categoryRepo;


    private static final Currency CURRENCY = Currency.getInstance("EUR");

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Product show(@PathVariable UUID id) {
        return repo.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    private Page<Product> index(@PageableDefault(value = 15, sort = {"name"}, direction = Sort.Direction.DESC)
                                Pageable pageable) {
        return this.repo.findAll(pageable);
    }

    /**
     * Create new product
     *
     * @param product product
     * @return 201
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    private ResponseEntity<Product> create(@RequestBody Product product) {
        product = exchange(product);
        for (UUID categoryId : product.getCategoriesIdList()) {
            Category category = categoryRepo.findOne(categoryId);
            product.getCategory().add(category);
        }
        Product result = this.repo.save(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    /**
     * Delete Product
     *
     * @param id product ID
     * @return 204
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<Product> destroy(@PathVariable UUID id) {
        this.repo.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update
     *
     * @param id      productId
     * @param product
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private Product update(@PathVariable UUID id, @RequestBody Product product) {
        product.setId(id);
        product = exchange(product);
        for (UUID categoryId : product.getCategoriesIdList()) {
            Category category = categoryRepo.findOne(categoryId);
            product.getCategory().add(category);
        }
        return this.repo.save(product);
    }

    /**
     * currency excahneg
     *
     * @param product
     * @return
     */
    private Product exchange(Product product) {
        if (!product.getCurrency().getCurrencyCode().equals(CURRENCY.getCurrencyCode())) {
            BigDecimal money = utils.exchange(product.getCurrency(), product.getPrice());
            product.setPrice(money);
            product.setCurrency(CURRENCY.getCurrencyCode());
        }
        return product;
    }
}
