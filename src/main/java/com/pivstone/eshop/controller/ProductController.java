package com.pivstone.eshop.controller;

import com.pivstone.eshop.jpa.CategoryRepo;
import com.pivstone.eshop.jpa.ProductRepo;
import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.model.Product;
import com.pivstone.eshop.resource.ProductResource;
import com.pivstone.eshop.utils.CurrencyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
     * currency exchange currency
     *
     * @param product the product need to exchange
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
     * @param product the product
     */
    private void setCategory(Product product) {
        for (UUID categoryId : product.getCategoriesIdList()) {
            Optional<Category> category = categoryRepo.findById(categoryId);
            if (category.isPresent()) {
                product.getCategory().add(category.get());
            }
        }
    }

    /**
     * check a product contains the category
     *
     * @param productId  Product ID
     * @param categoryId Category Id
     * @return If the product contains the category will return 204 else return 404
     */
    @RequestMapping(value = "/{productId}/categories/{categoryId}", method = RequestMethod.HEAD)
    public ResponseEntity<Product> checkCategory(@PathVariable UUID productId,
                                                 @PathVariable UUID categoryId) {
        if (((ProductRepo) this.repository).existsByIdAndCategory_Id(productId, categoryId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a category into the product
     *
     * @param productId  Product ID
     * @param categoryId Category ID
     * @return If product doesn't contain the category
     * will add the category into the product's categories and return 201
     * else return 204
     */
    @PutMapping("/{productId}/categories/{categoryId}")
    public ResponseEntity<Product> addCategory(@PathVariable UUID productId,
                                               @PathVariable UUID categoryId) {
        Product product = getInstance(productId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("category not found"));
        if (product.getCategory().contains(category)) {
            return ResponseEntity.noContent().build();
        } else {
            product.getCategory().add(category);
            this.repository.save(product);
            return ResponseEntity.created(location(product)).body(product);
        }

    }

    /**
     * Remove a category from a product's category list
     * @param productId Product ID
     * @param categoryId Category ID
     * @return If category not in category list will return 404
     * else remove the category from the list and return 204
     */
    @DeleteMapping("/{productId}/categories/{categoryId}")
    public ResponseEntity<Product> removeCategory(@PathVariable UUID productId,
                                                  @PathVariable UUID categoryId) {
        Product product = getInstance(productId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("category not found"));
        if (product.getCategory().contains(category)) {
            product.getCategory().remove(category);
            this.repository.save(product);

        }
        return ResponseEntity.noContent().build();

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

    @Override
    protected void beforePatch(Product product) {
        exchange(product);
    }
}
