package com.pivstone.eshop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
    protected RestRepo<E> repository;

    @Autowired
    protected ObjectMapper objectMapper;

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
        return ResponseEntity.created(location(entity)).body(result);
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
        E instance = getInstance(id);
        entity.setId(id);
        entity.setId(instance.getId());
        entity.setCreatedAt(instance.getCreatedAt());
        beforeUpdate(entity);
        return assembler.toResource(this.repository.save(entity));
    }

    @PatchMapping("/{id}")
    public Resource<E> patch(@PathVariable UUID id, HttpServletRequest request) throws IOException {
        E entity = getInstance(id);
        E updateEntity = objectMapper.readerForUpdating(entity).readValue(request.getReader());
        updateEntity.setId(entity.getId());
        updateEntity.setCreatedAt(entity.getCreatedAt());
        return assembler.toResource(this.repository.save(updateEntity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity head(@PathVariable UUID id) {
        if (this.repository.existsById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    protected URI location(E entity) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(entity.getId()).toUri();
    }

    protected void beforeUpdate(E entity) {
    }


    protected void beforeCreate(E entity) {
    }


    protected void beforeDestroy(E entity) {
    }

    protected void beforePatch(E entity) {
    }


    protected E getInstance(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("resource not found"));
    }
}
