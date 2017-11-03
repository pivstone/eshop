package com.pivstone.eshop.com.pivstoe.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

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
    @OneToMany
    private List<Category> categories;
    private Currency price;

}
