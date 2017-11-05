package com.pivstone.eshop.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Data
@Entity
public class Product extends AbstractTimestampModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotBlank(message = "name shouldn't be blank")
    private String name;

    @ManyToMany
    private Set<Category> category = new HashSet<>();

    @NotNull
    @DecimalMin(value = "0.01", message = "should be positive")
    private BigDecimal price;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<UUID> categoriesIdList = new HashSet<>();

    @JsonInclude
    @Transient
    private Currency currency = Currency.getInstance("EUR");


    public void setCurrency(String locate) {
        this.currency = Currency.getInstance(locate);
    }

    public Currency getCurrency() {
        return this.currency;
    }

}
