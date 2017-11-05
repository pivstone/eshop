package com.pivstone.eshop.resource;

import com.pivstone.eshop.controller.ProductController;
import com.pivstone.eshop.model.Product;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
public class ProductResource extends Resource<Product> {

    public ProductResource(Product entity) {
        super(entity);
        this.add(linkTo(methodOn(ProductController.class).show(entity.getId())).withSelfRel());
        this.add(linkTo(methodOn(ProductController.class).index(null, null)).withRel("list"));
    }
}
