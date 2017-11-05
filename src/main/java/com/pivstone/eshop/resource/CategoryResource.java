package com.pivstone.eshop.resource;

import com.pivstone.eshop.controller.CategoryController;
import com.pivstone.eshop.model.Category;
import org.springframework.hateoas.Resource;

import javax.xml.bind.annotation.XmlRootElement;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
@XmlRootElement(name = "Categories")
public class CategoryResource extends Resource<Category> {
    private final Category category;


    public CategoryResource(Category category) {
        super(category);
        this.category = category;
        this.add(linkTo(methodOn(CategoryController.class).show(category.getId())).withSelfRel());
        this.add(linkTo(methodOn(CategoryController.class).index(null, null)).withRel("list"));
        this.add(linkTo(methodOn(CategoryController.class).products(category.getId(), null, null)).withRel("products"));

    }
}
