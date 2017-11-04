package com.pivstone.eshop.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    @ManyToMany
    private Set<Category> category = new HashSet<>();
    private BigDecimal price;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<UUID> categoriesIdList;
    @JsonInclude
    @Transient
    private Currency currency = Currency.getInstance("EUR");

    public void setCurrency(String locate) {
        this.currency = Currency.getInstance(locate);
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Set<UUID> getCategoriesIdList() {
        return getCategory().stream().map(Category::getId).collect(Collectors.toSet());
    }

}
