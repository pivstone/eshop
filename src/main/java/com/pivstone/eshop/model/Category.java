package com.pivstone.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Data
@Entity
public class Category extends AbstractModel {

    private String name;
    @ManyToMany
    @JsonIgnore
    private Set<Product> product = new HashSet<>();

}
