package com.pivstone.eshop.domain;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
@Data
public class ProductResource extends ResourceSupport {
    private final Product product;


    public ProductResource(Product product) {
        this.product = product;
        this.add(new LinK());
    }
}
