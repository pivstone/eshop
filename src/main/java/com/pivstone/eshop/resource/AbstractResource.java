package com.pivstone.eshop.resource;

import com.pivstone.eshop.controller.AbstractController;
import com.pivstone.eshop.controller.ProductController;
import com.pivstone.eshop.model.AbstractModel;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
public abstract class AbstractResource<E extends AbstractModel> extends Resource<E> {
    private final E entity;


    public AbstractResource(E entity) {
        super(entity);
        this.entity = entity;
        this.add(linkTo(methodOn(AbstractController.class).show(entity.getId())).withSelfRel());
        this.add(linkTo(methodOn(AbstractController.class).index(null, null)).withRel("list"));

    }
}
