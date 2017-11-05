package com.pivstone.eshop.controller;


import com.pivstone.eshop.jpa.RestRepo;
import com.pivstone.eshop.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;


/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
public abstract class AbstractController<E extends AbstractModel> {

    @Autowired
    private ResourceAssemblerSupport<E, ? extends Resource<E>> assembler;

    @Autowired
    private RestRepo<E> repository;

    @Autowired
    private PagedResourcesAssembler<E> pagedAssembler;

    @GetMapping("/{id}")
    public Resource<E> show(@PathVariable UUID id) {
        E entity = getInstance(id);
        return this.assembler.toResource(entity);
    }

    @GetMapping("/")
    public PagedResources<? extends Resource<E>> index(@PageableDefault(sort = "id") Pageable pageable) {
        Page<E> entities = this.repository.findAll(pageable);
        return pagedAssembler.toResource(entities, this.assembler);
    }


    @PostMapping("/")
    public ResponseEntity<E> create(@RequestBody E entity) {
        beforeCreate(entity);
        E result = this.repository.save(entity);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<E> destroy(@PathVariable UUID id) {
        E entity = getInstance(id);
        beforeDestroy(entity);
        this.repository.delete(entity.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Resource<E> update(@PathVariable UUID id, @Valid @RequestBody E entity) {
        entity.setId(id);
        entity.setId(entity.getId());
        entity.setCreatedAt(entity.getCreatedAt());
        beforeUpdate(entity);
        return assembler.toResource(this.repository.save(entity));
    }

    protected void beforeUpdate(E entity) {
    }


    protected void beforeCreate(E entity) {
    }


    protected void beforeDestroy(E entity) {
    }


    protected E getInstance(UUID id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
