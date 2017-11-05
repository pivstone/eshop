package com.pivstone.eshop.controller;

import com.pivstone.eshop.jpa.CategoryRepo;
import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.model.Product;
import com.pivstone.eshop.resource.ProductResource;
import com.pivstone.eshop.utils.CurrencyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;


/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */

@RestController("product")
@RequestMapping(path = "/products", produces = "application/json; charset=UTF-8")
@ExposesResourceFor(ProductResource.class)
public class ProductController extends AbstractController<Product> {


    @Autowired
    private CurrencyUtils utils;

    @Autowired
    private CategoryRepo categoryRepo;
    private static final Currency CURRENCY = Currency.getInstance("EUR");


    /**
     * currency exchnge currency
     *
     * @param product
     */
    private void exchange(Product product) {
        if (!product.getCurrency().getCurrencyCode().equals(CURRENCY.getCurrencyCode())) {
            BigDecimal money = utils.exchange(product.getCurrency(), product.getPrice());
            product.setPrice(money);
            product.setCurrency(CURRENCY.getCurrencyCode());
        }
    }

    /**
     * set category from categories id list
     *
     * @param product
     */
    private void setCategory(Product product) {
        for (UUID categoryId : product.getCategoriesIdList()) {
            Optional<Category> category = categoryRepo.findById(categoryId);
            if (category.isPresent()) {
                product.getCategory().add(category.get());
            }
        }
    }

    @Override
    protected void beforeCreate(Product product) {
        exchange(product);
        setCategory(product);
    }

    @Override
    protected void beforeUpdate(Product product) {
        exchange(product);
        setCategory(product);
    }
}
