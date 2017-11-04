package com.pivstone.eshop.resource;

import com.pivstone.eshop.controller.ProductController;
import com.pivstone.eshop.model.Product;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
@Component
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, ProductResource> {
    public ProductResourceAssembler() {
        super(ProductController.class, ProductResource.class);
    }

    @Override
    public ProductResource toResource(Product entity) {
        return new ProductResource(entity);
    }

    @Override
    public List<ProductResource> toResources(Iterable<? extends Product> products) {
        List<ProductResource> resources = new ArrayList<>();
        for (Product product : products) {
            resources.add(new ProductResource(product));
        }
        return resources;
    }


}
