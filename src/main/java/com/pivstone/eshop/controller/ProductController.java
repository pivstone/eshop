package com.pivstone.eshop.controller;

import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.model.Product;
import com.pivstone.eshop.resource.ProductResource;
import com.pivstone.eshop.resource.ProductResourceAssembler;
import com.pivstone.eshop.services.CategoryService;
import com.pivstone.eshop.services.ProductService;
import com.pivstone.eshop.utils.CurrencyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;


/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */

@RestController("product")
@RequestMapping("/products")
@ExposesResourceFor(ProductResource.class)
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private CurrencyUtils utils;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductResourceAssembler assembler;

    private static final Currency CURRENCY = Currency.getInstance("EUR");

    @GetMapping("/{id}")
    public ProductResource show(@PathVariable UUID id) {
        Product entity = service.findOne(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return assembler.toResource(entity);
    }

    @GetMapping("/")
    public PagedResources<ProductResource> index(@PageableDefault(sort = "name") Pageable pageable,
                                                 PagedResourcesAssembler<Product> assembler) {
        Page<Product> products = this.service.findAll(pageable);
        return assembler.toResource(products, this.assembler);
    }

    /**
     * Create new product
     *
     * @param product product
     * @return 201
     */
    @PostMapping("/")
    private ResponseEntity<Product> create(@RequestBody Product product) {
        exchange(product);
        setCategory(product);
        Product result = this.service.save(product);
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
    @DeleteMapping("/{id}")
    private ResponseEntity<Product> destroy(@PathVariable UUID id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update
     *
     * @param id      productId
     * @param product product
     * @return
     */
    @PutMapping("/{id}")
    private Product update(@PathVariable UUID id, @Valid @RequestBody Product product) {
        product.setId(id);
        exchange(product);
        setCategory(product);
        return this.service.save(product);
    }

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
     * @param product
     */
    private void setCategory(Product product) {
        for (UUID categoryId : product.getCategoriesIdList()) {
            Optional<Category> category = categoryService.findOne(categoryId);
            if (category.isPresent()) {
                product.getCategory().add(category.get());
            }
        }
    }
}
